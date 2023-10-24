package Capstone.Bioproject.web.Mypage;

import Capstone.Bioproject.web.Login.AuthService;
import Capstone.Bioproject.web.Mypage.dto.ContentResponseDto;
import Capstone.Bioproject.web.Mypage.dto.DiseaseResponseDto;
import Capstone.Bioproject.web.Mypage.dto.MypageResponseDto;
import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.Disease;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.Mypage.dto.MyInfoUpdateRequestDto;
import Capstone.Bioproject.web.repository.ContentRepository;
import Capstone.Bioproject.web.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final AuthService authService;
    private final ContentRepository contentRepository;
    private final DiseaseRepository diseaseRepository;

    //최근 진료 기록 보기
    @Transactional
    public MypageResponseDto getMyContents(HttpServletRequest request) {
        User user=authService.getMemberInfo(request);
        List<Content> contents = contentRepository.findByUser(user);
        List<ContentResponseDto> contentResponseDto= contents.stream()
                .map(content -> {
                    return ContentResponseDto.builder()
                            .id(content.getId()).content(content.getContent())
                            .disease(diseaseRepository.getById(content.getDisease()).getName())
                            .date(content.getDate()).build();
                })
                .collect(Collectors.toList());
        MypageResponseDto result = MypageResponseDto.builder().id(user.getId())
                .name(user.getName()).content(contentResponseDto).build();
        return result;
    }

    @Transactional
    public DiseaseResponseDto getContent(Long id){
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록이 없습니다.: " + id));
        Disease disease_Info=diseaseRepository.getById(content.getDisease());
        DiseaseResponseDto result = DiseaseResponseDto.builder().content(content.getContent())
                .disease(disease_Info.getName())
                .info(disease_Info.getInfo()).cause(disease_Info.getCause())
                .type(disease_Info.getType()).build();
        return result;
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

    public ResponseEntity<Map<String, Boolean>> makeResponse(String state){
        Map<String, Boolean> response = new HashMap<>();
        response.put(state, Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

