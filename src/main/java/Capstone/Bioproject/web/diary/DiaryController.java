package Capstone.Bioproject.web.diary;

import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.diary.dto.DiaryRequestDto;
import Capstone.Bioproject.web.diary.dto.DiaryResponseDto;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final UserRepository userRepository;
    private final MypageService mypageService;
    private final DiaryService diaryService;

    @GetMapping("/diary/{id}")
    public ResponseEntity<Map<String, Boolean>> sendUserInfo(HttpServletRequest request, @PathVariable Long id
            , @RequestBody DiaryRequestDto diaryRequestDto){
        //사용자 정보 가져오기
        //User user=mypageService.getUserInfo(request);
        User user=userRepository.findById(id).orElseThrow();
        return diaryService.save(user, diaryRequestDto);
    }
}
