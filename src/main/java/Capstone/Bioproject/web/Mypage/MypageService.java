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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    //최근 진료 기록 보기
    @Transactional
    public List<Content> getMyContents(HttpServletRequest request) {
        User user=authService.getMemberInfo(request);
        List<Content> contents = contentRepository.findByUser(user);

        return contents;
    }

    //마이페이지
    @Transactional
    public User getUserInfo(HttpServletRequest request){
        User user=authService.getMemberInfo(request);

        return user;
    }

    //업데이트
    @Transactional
    public ResponseEntity<Map<String, Boolean>> updateInfo(HttpServletRequest request,MyInfoUpdateRequestDto updateDto){
        User user=authService.getMemberInfo(request);
        user.update(updateDto.getName(),updateDto.getAge(),updateDto.getSex(),0);
        return makeResponse("update");
    }

    //삭제
    @Transactional
    public ResponseEntity<Map<String, Boolean>> deleteContent(HttpServletRequest request, Long id){
        Content content= contentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 진료 기록이 없습니다.id:"+id));
        contentRepository.delete(content);
        return makeResponse("delete");
    }

    //응답 메세지 보내기
    public ResponseEntity<Map<String, Boolean>> makeResponse(String state){
        Map<String, Boolean> response = new HashMap<>();
        response.put(state, Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

