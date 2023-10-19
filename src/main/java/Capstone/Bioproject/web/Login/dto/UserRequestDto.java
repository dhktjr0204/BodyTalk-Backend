package Capstone.Bioproject.web.Login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String accessToken;
    private String refreshToken;
}
