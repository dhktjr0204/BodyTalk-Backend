package Capstone.Bioproject.web.Login;

import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.config.jwt.JwtHeaderUtil;
import Capstone.Bioproject.web.config.oauth.util.CookieUtils;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;
    private final MypageService mypageService;

    @GetMapping("auth/logout")
    public String logout(HttpServletRequest request){
        User user = mypageService.getUserInfo(request);
        String access_Token = getAccessToken(request);
        if (user.getProvider().equals("kakao")) {
            loginService.kakaoLogout(access_Token);
        }
        return "로그아웃 완료";
    }

    @PutMapping("/auth/delete")
    public String delete(HttpServletRequest request) {
        User user = mypageService.getUserInfo(request);
        String access_Token = getAccessToken(request);
        System.out.println("확인용"+access_Token);
        if (user.getProvider().equals("kakao")) {
            loginService.kakaoDelete(access_Token);
            loginService.delete(user);
        }
        else if(user.getProvider().equals("naver")){
            loginService.naverDelete(access_Token);
        }
        else{

        }
        return "탈퇴 완료";
    }

    public String getAccessToken(HttpServletRequest request){
        HttpSession session=request.getSession();
        String access_Token=(String)session.getAttribute("access_Token");
        session.removeAttribute("access_Token");
        return access_Token;
    }

}
