package Capstone.Bioproject.web.config;


import Capstone.Bioproject.web.config.jwt.JwtAuthenticationFilter;
import Capstone.Bioproject.web.config.jwt.JwtExceptionFilter;
import Capstone.Bioproject.web.config.jwt.JwtTokenProvider;
import Capstone.Bioproject.web.config.oauth.CustomOAuth2UserService;
import Capstone.Bioproject.web.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import Capstone.Bioproject.web.config.oauth.OAuth2AuthenticationFailureHandler;
import Capstone.Bioproject.web.config.oauth.OAuth2AuthenticationSuccessHandler;
import Capstone.Bioproject.web.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig { //WebSecurityConfigurerAdapter was deprecated
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtExceptionFilter jwtExceptionFilter;
    //필터 거치면 안될 것들
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().antMatchers("/images/**", "/js/**", "/css/**", "/main/**","/medi/**");
        };
    }

    //요청이 들어오면 repository로 가서 authorization request를 저장한다.
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .authorizeRequests()
                .antMatchers ( "/main/**","/login/**", "/oauth2/**","/medi/**").permitAll()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/**")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)

                //로그아웃 추가기능 안되면 뺄 것
                .and().logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/main").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,JwtAuthenticationFilter.class);
        return http.build();
    }
}