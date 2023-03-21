package Capstone.Bioproject.web.diary;

import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final MypageService mypageService;

    @GetMapping("/diary")
    public User sendUserInfo(HttpServletRequest request){
        User user=mypageService.getUserInfo(request);

        return user;
    }
}
