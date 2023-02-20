package Capstone.Bioproject.web.main;

import Capstone.Bioproject.web.Mypage.AuthService;
import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.domain.dtos.MainResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final AuthService authService;
    private final MainService mainService;

    @PostMapping("/main")
    public MainResponseDto save(HttpServletRequest request, @RequestPart(value = "content") String content){
        //로그인된 사용자인지 확인
        User user=authService.getMemberInfo(request);
        //추측 병명 및 가까운 병원 알려주기
        MainResponseDto mainResponseDto=mainService.getDisease(content);

        if (user == null){//비로그인 사용자라면
            //바로 결과 알려주기
            return mainResponseDto;
        }else {//로그인된 사용자라면
            //db에 저장
            mainService.save(user,content,mainResponseDto);
            return mainResponseDto;
        }
    }

}
