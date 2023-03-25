package Capstone.Bioproject.web.diary;

import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.diary.dto.ChartRequestDto;
import Capstone.Bioproject.web.diary.dto.DiaryRequestDto;
import Capstone.Bioproject.web.diary.dto.TypeResponseDto;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final UserRepository userRepository;
    private final MypageService mypageService;
    private final DiaryService diaryService;

    @GetMapping("/diary")
    public ResponseEntity<Map<String, Boolean>> sendUserInfo(HttpServletRequest request, @RequestBody DiaryRequestDto diaryRequestDto){
        //사용자 정보 가져오기
        User user=mypageService.getUserInfo(request);
        return diaryService.save(user, diaryRequestDto);
    }

    @GetMapping("/diary/chart")
    public TypeResponseDto sendGraph(HttpServletRequest request, @RequestBody ChartRequestDto chartRequestDto){
        User user=mypageService.getUserInfo(request);
        return diaryService.sendGraph(user,chartRequestDto);
    }
}
