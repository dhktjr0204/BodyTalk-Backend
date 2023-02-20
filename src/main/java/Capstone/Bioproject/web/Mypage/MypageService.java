package Capstone.Bioproject.web.Mypage;

import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.domain.dtos.MyInfoUpdateRequestDto;
import Capstone.Bioproject.web.repository.ContentRepository;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    @Transactional
    public List<Content> getMyPage(HttpServletRequest request) {
        User user=authService.getMemberInfo(request);
        List<Content> contents = contentRepository.findByUser(user);

        return contents;
    }

    @Transactional
    public ResponseEntity<Map<String, Boolean>> updateInfo(MyInfoUpdateRequestDto updateDto){
        User user=userRepository.findById(updateDto.getId())
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id" + updateDto.getId()));

        user.update(updateDto.getName(),updateDto.getAge(),updateDto.getSex());
        Map<String, Boolean> response = new HashMap<>();

        response.put("update",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}

