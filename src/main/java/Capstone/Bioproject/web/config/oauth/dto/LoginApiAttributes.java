package Capstone.Bioproject.web.config.oauth.dto;

import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class LoginApiAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static LoginApiAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static LoginApiAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return LoginApiAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static LoginApiAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return LoginApiAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static LoginApiAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) response.get("profile");

        return LoginApiAttributes.builder()
                .name((String) account.get("nickname"))
                .email((String) response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
//User 엔티티 생성 OAuthAttribute에서 엔티티를 생성하는 시점은 처음 가입할 때
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
