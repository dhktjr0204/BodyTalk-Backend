package Capstone.Bioproject.web.django;

import Capstone.Bioproject.web.domain.Content;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.domain.dtos.DjangoResponseDto;
import Capstone.Bioproject.web.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DjangoService {
    private final ContentRepository contentRepository;
    @Transactional
    public void save(User user, String content, DjangoResponseDto mainResponseDto){
        Content contents=
                Content.builder()
                        .user(user)
                        .disease(mainResponseDto.getDisease())
                        .diseaseinfo(mainResponseDto.getDiseaseInfo())
                        .content(content)
                        .build();
        contentRepository.save(contents);
    }

    public DjangoResponseDto getDisease(String content){

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/medi";

        //header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 데이터 셋팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("content",content);

        // header와 요청데이터 결합하여 요청 생성
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        System.out.println("==================== Django call (request) : " +  content + " ======================");
        ResponseEntity<String> response = restTemplate.postForEntity(url,request, String.class);
        String result = response.getBody(); // 응답 받은 fno 출력
        System.out.println("==================== Django end ==========================");

        //기호 제거
        String disease=result.substring(2,result.length()-2);

        // 응답 확인
        System.out.print("reponse : "+disease);
        String diseaseInfo="머리가 아픈 것";
        List<String> hospitals=new ArrayList<>();
        hospitals.add("서울대 병원");
        hospitals.add("삼성 병원");

        return new DjangoResponseDto(disease, diseaseInfo, hospitals);
        /*
        //여기 머신러닝 써야할 부분
        String disease="두통";
        String diseaseInfo="머리가 아픈 것";
        List<String> hospitals=new ArrayList<>();
        hospitals.add("서울대 병원");
        hospitals.add("삼성 병원");
        //-----------------

        return new DjangoResponseDto(disease,diseaseInfo,hospitals);*/
    }


}
