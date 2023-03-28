package Capstone.Bioproject.web.Mypage;
import Capstone.Bioproject.web.Mypage.dto.DiseaseResponseDto;
import Capstone.Bioproject.web.Mypage.dto.MypageResponseDto;
import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.Mypage.dto.MyInfoUpdateRequestDto;
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
    //내 정보 보기
    @GetMapping("/mypage")
    public User sendUserInfo(HttpServletRequest request){
        User user=mypageService.getUserInfo(request);
        return user;
    }

    //최근 기록 보기
    @GetMapping("/mypage/contents")
    public MypageResponseDto sendContents(HttpServletRequest request){
        return mypageService.getMyContents(request);
    }

    @GetMapping("/mypage/{id}")
    public DiseaseResponseDto sendContentDetail(@PathVariable Long id){
        return mypageService.getContent(id);
    }

    //정보 수정
    @PostMapping("/mypage/update")
    public ResponseEntity<Map<String, Boolean>> updateInfo(HttpServletRequest request, @RequestBody MyInfoUpdateRequestDto updateDto){
        return mypageService.updateInfo(request, updateDto);
    }

    //정보 삭제
    @DeleteMapping("/mypage/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteContent(HttpServletRequest request, @PathVariable Long id){
        return mypageService.deleteContent(request,id);
    }

}
