package Capstone.Bioproject.web.config.jwt;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(String bearerToken) {
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX) && bearerToken.length() > 6){
            return bearerToken.substring(7);
        }

        return null;
    }
}