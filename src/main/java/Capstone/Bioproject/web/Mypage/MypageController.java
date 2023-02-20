package Capstone.Bioproject.web.Mypage;
import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.dtos.MyInfoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/mypage")
    public List<Content> sendUserInfo(HttpServletRequest request){
        List<Content> userInfo=mypageService.getMyPage(request);
        return userInfo;
    }

    @PostMapping("/mypage/update")
    public ResponseEntity<Map<String, Boolean>> updateInfo(HttpServletRequest request, @RequestBody MyInfoUpdateRequestDto updateDto){
        return mypageService.updateInfo(updateDto);
    }

}
