package Capstone.Bioproject.web.Mypage;
import Capstone.Bioproject.web.domain.dtos.MyInfoUpdateDto;
import Capstone.Bioproject.web.domain.dtos.MypageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MypageController {
    private final MypageService mypageService;
    @GetMapping("/mypage")
    public MypageResponseDto sendUserInfo(HttpServletRequest request){
        MypageResponseDto userInfo=mypageService.getMyPage(request);
        return userInfo;
    }

    @PostMapping("/mypage/update")
    public ResponseEntity<Map<String, Boolean>> updateInfo(HttpServletRequest request, @RequestBody MyInfoUpdateDto updateDto){
        return mypageService.updateInfo(updateDto);
    }

}
