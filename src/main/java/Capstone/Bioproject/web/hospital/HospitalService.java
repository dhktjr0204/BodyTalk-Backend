package Capstone.Bioproject.web.hospital;
import Capstone.Bioproject.web.domain.Hospital;
import Capstone.Bioproject.web.hospital.dto.HospitalInfoDto;
import Capstone.Bioproject.web.hospital.dto.LocalDto;
import Capstone.Bioproject.web.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HospitalService {
    @Value("${naver.map.client_id}")
    private String naver_Id;
    @Value("${naver.map.client_secret}")
    private String naver_key;

    private final HospitalRepository hospitalRepository;

    //최근 진료 기록 보기
    @Transactional
    public List<Hospital> getMyContents(HospitalInfoDto hospitalInfoDto) {
        if(hospitalInfoDto.getType().equals("치과")){
            return hospitalRepository.findByDentalDistance(hospitalInfoDto.getType(), hospitalInfoDto.getLon(), hospitalInfoDto.getLat());
        }else {
            return hospitalRepository.findByHospitalDistance(hospitalInfoDto.getType(), hospitalInfoDto.getLon(), hospitalInfoDto.getLat());
        }
    }

    public LocalDto getMyCoordi(String local){
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+local;
        HttpHeaders headers = new HttpHeaders();
        //JSON형식의 요청
        headers.setContentType(MediaType.APPLICATION_JSON);
        //인증키 설정
        headers.set("X-NCP-APIGW-API-KEY-ID", naver_Id);
        headers.set("X-NCP-APIGW-API-KEY", naver_key);
        //JSON형식의 응답
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //요청 본문과 헤더를 포함하는 HTTP생성
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        //HTTP요청 실행
        RestTemplate restTemplate = new RestTemplate();
        //응답은 ResponseEntity로 받는다
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        try {
            JSONObject object = new JSONObject(response.getBody());
            JSONArray addresses = object.getJSONArray("addresses");
            JSONObject address = addresses.getJSONObject(0);
            Double x = address.getDouble("x");
            Double y= address.getDouble("y");
            LocalDto localDto=LocalDto.builder().lon(x).lat(y).build();
            return localDto;
        } catch (JSONException e) {
            // 예외 처리
            LocalDto localDto=LocalDto.builder().lon(0D).lat(0D).build();
            return localDto;
        }
    }
}
