package Capstone.Bioproject.web.config.oauth;

import Capstone.Bioproject.web.config.jwt.JwtTokenProvider;
import Capstone.Bioproject.web.config.oauth.dto.TokenResponseDto;
import Capstone.Bioproject.web.config.oauth.util.CookieUtils;
import Capstone.Bioproject.web.config.oauth.util.RedisUtil;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import static Capstone.Bioproject.web.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static Capstone.Bioproject.web.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());;
        //login한 사용자 목록
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        TokenResponseDto token=null;
        int isNew=999;
        String userInfo=null;
        if (oAuth2User.getAttribute("sub")!=null){//구글일 때
            String email=oAuth2User.getAttribute("email");
            token = tokenProvider.generateToken(authentication,email,"google");
            //새로운 회원인지 아닌지 확인용
            isNew=getisNew(oAuth2User.getAttribute("email"),"google");
            userInfo = email + ",google";
            redisUtil.set(userInfo,token.getRefreshToken(), Duration.ofMillis(token.getRefreshTokenExpirationTime()));
        }
        else {//카카오톡일 때
            if(oAuth2User.getAttribute("email")==null) {
                Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
                String email = (String) kakao_account.get("email");
                token = tokenProvider.generateToken(authentication, email, "kakao");
                isNew=getisNew(email,"kakao");
                userInfo = email + ",kakao";
                redisUtil.set(userInfo,token.getRefreshToken(), Duration.ofMillis(token.getRefreshTokenExpirationTime()));
            }
            else{//네이버일 때
                token = tokenProvider.generateToken(authentication,oAuth2User.getAttribute("email"),"naver");
                String email=oAuth2User.getAttribute("email");
                isNew=getisNew(oAuth2User.getAttribute("email"),"naver");
                userInfo=email+",naver";
                redisUtil.set(userInfo,token.getRefreshToken(), Duration.ofMillis(token.getRefreshTokenExpirationTime()));
            }
        }
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken",token.getRefreshToken())
                .queryParam("isNew", isNew)
                .build().toUriString();
    }

    private int getisNew(String email, String provider){
        User user=userRepository.findByEmailAndProvider(email,provider)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. email"+email));
        int isNew=user.getIsnew();
        return isNew;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}