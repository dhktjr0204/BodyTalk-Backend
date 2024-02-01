package Capstone.Bioproject.web.Login;

import Capstone.Bioproject.web.Login.dto.UserRequestDto;
import Capstone.Bioproject.web.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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
        if (user.getProvider().equals("kakao")) {
            loginService.kakaoDelete(user.getToken());
        }
        else if(user.getProvider().equals("naver")){
            loginService.naverDelete(user.getToken());
        }
        else{
            loginService.googleDelete(user.getToken());
        }
        loginService.delete(user);
        return "탈퇴 완료";
    }

    @PostMapping("/api/token")
    public ResponseEntity<?> reissue(HttpServletRequest request, @RequestBody UserRequestDto userRequestDto){
        return loginService.reissue(userRequestDto);
    }
}
