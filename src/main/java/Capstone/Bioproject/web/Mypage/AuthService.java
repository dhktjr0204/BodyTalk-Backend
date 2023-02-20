package Capstone.Bioproject.web.Mypage;

import Capstone.Bioproject.web.config.jwt.JwtHeaderUtil;
import Capstone.Bioproject.web.config.jwt.JwtTokenProvider;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public User getMemberInfo(HttpServletRequest request){
        //request로부터 token 값 뽑기
        String token=JwtHeaderUtil.getAccessToken(request);

        if (token==null){
            return null;
        }

        //token 복호화
        String tokenInfo=jwtTokenProvider.parseClaims(token).getSubject();
        //email,provider로 된 형태 나누기
        String userInfo[]=tokenInfo.split(",");
        User user = userRepository.findByEmailAndProvider(userInfo[0],userInfo[1])
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다.: " + userInfo[0]));
        return user;
    }
}
