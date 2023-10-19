package Capstone.Bioproject.web.Login;

import Capstone.Bioproject.web.Login.dto.UserRequestDto;
import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.config.jwt.JwtHeaderUtil;
import Capstone.Bioproject.web.config.oauth.util.CookieUtils;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;
    private final AuthService authService;

    @PostMapping("api/logout")
    public ResponseEntity<?> logout(@RequestBody UserRequestDto userRequestDto){
        return loginService.logout(userRequestDto);
    }

    @PutMapping("/api/delete")
    public String delete(HttpServletRequest request) {
        User user = authService.getMemberInfo(request);
        String access_Token = getAccessToken(request);
        if (user.getProvider().equals("kakao")) {
            loginService.kakaoDelete(access_Token);
        }
        else if(user.getProvider().equals("naver")){
            loginService.naverDelete(access_Token);
        }
        else{
            loginService.googleDelete(access_Token);
        }
        loginService.delete(user);
        return "탈퇴 완료";
    }

    @PostMapping("/api/token")
    public ResponseEntity<?> reissue(HttpServletRequest request, @RequestBody UserRequestDto userRequestDto){
        User user = authService.getMemberInfo(request);
        return loginService.reissue(userRequestDto,user);
    }

    public String getAccessToken(HttpServletRequest request){
        HttpSession session=request.getSession();
        String access_Token=(String)session.getAttribute("access_Token");
        session.removeAttribute("access_Token");
        return access_Token;
    }

}
