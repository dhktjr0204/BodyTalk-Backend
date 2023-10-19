package Capstone.Bioproject.web.config.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponseDto {
    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Long refreshTokenExpirationTime;
}
