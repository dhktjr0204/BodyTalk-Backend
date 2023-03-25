package Capstone.Bioproject.web.Login;

import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.config.jwt.JwtHeaderUtil;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class LoginController {
    private final LoginService loginService;
    private final UserRepository userRepository;
    private final MypageService mypageService;

    @PutMapping("/auth/delete")
    public String delete(HttpServletRequest request) {
        User user = mypageService.getUserInfo(request);
        String token= JwtHeaderUtil.getAccessToken(request);
        if (user.getProvider().equals("kakao")) {
            loginService.kakaoDelete(token);
        }
        else if(user.getProvider().equals("naver")){

        }
        else{

        }

        userRepository.delete(user);

        return "탈퇴 완료";
    }
}
