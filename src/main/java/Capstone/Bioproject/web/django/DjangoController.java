package Capstone.Bioproject.web.django;

import Capstone.Bioproject.web.Login.AuthService;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.django.dto.DjangoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class DjangoController {
    private final AuthService authService;
    private final DjangoService mainService;

    @PostMapping("/api/medi")
    public DjangoResponseDto save(HttpServletRequest request, @RequestPart(value = "content") String content){
        //로그인된 사용자인지 확인
        User user=authService.getMemberInfo(request);
        String sex="female";
        if (user!=null){
            sex=user.getSex();
        }
        //추측 병명 및 가까운 병원 알려주기
        DjangoResponseDto djangoResponseDto=mainService.getDisease(content,sex);
        if (user == null){//비로그인 사용자라면
            //바로 결과 알려주기
            return djangoResponseDto;
        }else {//로그인된 사용자라면
            //db에 저장
            mainService.save(user,content,djangoResponseDto);
            return djangoResponseDto;
        }
    }

}
