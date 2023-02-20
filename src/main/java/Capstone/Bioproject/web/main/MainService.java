package Capstone.Bioproject.web.main;

import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.domain.dtos.MainResponseDto;
import Capstone.Bioproject.web.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {
    private final ContentRepository contentRepository;
    @Transactional
    public void save(User user, String content,MainResponseDto mainResponseDto){
        Content contents=
                Content.builder()
                        .user(user)
                        .disease(mainResponseDto.getDisease())
                        .diseaseinfo(mainResponseDto.getDiseaseInfo())
                        .content(content)
                        .build();
        contentRepository.save(contents);
    }

    public MainResponseDto getDisease(String content){
        //여기 머신러닝 써야할 부분
        String disease="두통";
        String diseaseInfo="머리가 아픈 것";
        List<String> hospitals=new ArrayList<>();
        hospitals.add("서울대 병원");
        hospitals.add("삼성 병원");
        //-----------------

        return new MainResponseDto(disease,diseaseInfo,hospitals);
    }


}
