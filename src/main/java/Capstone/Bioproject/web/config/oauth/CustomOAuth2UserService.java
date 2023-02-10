package Capstone.Bioproject.web.config.oauth;

import Capstone.Bioproject.web.config.oauth.dto.LoginApiAttributes;
import Capstone.Bioproject.web.config.oauth.dto.SessionUser;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //요청하는 로그인 api 타입
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //oauth2로그인 진행 시 키가 되는 필드 값
        //구글은 sub 네이버 카카오는 사용하지 않음
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        //OAuth2UserService에서 빼온 attribute를 담을 클래스
        LoginApiAttributes attributes = LoginApiAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes,registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(LoginApiAttributes attributes, String APIid) {
        User user = userRepository.findByEmailAndProvider(attributes.getEmail(),APIid)
                .map(entity -> entity.update(attributes.getName()))//이름이 바뀌면 업뎃
                .orElse(attributes.toEntity(APIid));

        return userRepository.save(user);
    }
}
