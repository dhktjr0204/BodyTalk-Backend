package Capstone.Bioproject.web.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//cors설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 1시간
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(SiteProperties.SITE_URL);
    }

}