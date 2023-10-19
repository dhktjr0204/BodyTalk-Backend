package Capstone.Bioproject.web.Login;

import Capstone.Bioproject.web.Login.dto.UserRequestDto;
import Capstone.Bioproject.web.config.jwt.JwtTokenProvider;
import Capstone.Bioproject.web.config.oauth.dto.TokenResponseDto;
import Capstone.Bioproject.web.config.oauth.util.RedisUtil;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import Capstone.Bioproject.web.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    @Value("${naver.client_id}")
    private String naver_Id;
    @Value(("${naver.client-secret}"))
    private String naver_Secret;

    public ResponseEntity<?> logout(UserRequestDto logout) {
        // Access Token 에서 User email 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if (redisUtil.get(authentication.getName()) != null) {
            // Refresh Token 삭제
            redisUtil.delete(authentication.getName());
        }

        // 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisUtil.set(logout.getAccessToken(), "logout", Duration.ofMillis(expiration));

        return Response.ok("로그아웃 되었습니다.");
    }

    public void googleDelete(String access_Token){
        String apiUrl="https://accounts.google.com/o/oauth2/revoke?token={token}";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("token", access_Token);

        String response = restTemplate.postForObject(builder.toUriString(), null, String.class);
        System.out.println(response);
    }

    public void naverDelete(String access_Token){
        String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+naver_Id+
                "&client_secret="+naver_Secret+"&access_token="+access_Token+"&service_provider=NAVER";
        requestToServer(apiUrl,"GET",null,access_Token);
    }
    public void kakaoLogout(String access_Token){
        requestToServer("https://kapi.kakao.com/v1/user/logout","POST","kakao",access_Token);
    }
    //카카오 탈퇴
    public void kakaoDelete(String access_Token) {
        requestToServer("https://kapi.kakao.com/v1/user/unlink","POST","kakao",access_Token);
    }
    private void requestToServer(String apiURL,String method, String headStr, String access_Token){
        String reqURL =apiURL;
        try {
            //지정된 url문자열에서 URL객체 만듬
            URL url = new URL(reqURL);
            //url연결을 열고 httpConnections 객체를 만든다.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //HTTP 요청메서드 설정
            conn.setRequestMethod(method);
            //만약 headStr이 포함되면 Authorization헤더를 추가
            if(headStr!=null) {
                conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            }
            //서버에서의 응답코드 얻는다.
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //서버 응답 스트림을 읽기위한 bufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    public ResponseEntity<?> reissue(UserRequestDto reissue){
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())){
            return Response.badRequest("Refresh Token 정보가 유효하지 않습니다.");
        }
        //Access Token에서 User email가져온다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());
        String userInfo[]=authentication.getName().split(",");;
        //Redis에서 user email을 기반으로 저장된 Refresh Token 값을 가져온다.
        String refreshToken = redisUtil.get(authentication.getName());
        if (ObjectUtils.isEmpty(refreshToken)){
            return Response.badRequest("잘못된 요청입니다.");
        }if(!refreshToken.equals(reissue.getRefreshToken())){
            return Response.badRequest("Refresh Token 정보가 일치하지 않습니다.");
        }
        //새로운 Access토큰 생성
        TokenResponseDto tokenInfo = jwtTokenProvider.reGenerateToken(authentication,userInfo[0],userInfo[1],refreshToken);

        //RefreshToken Redis 업데이트
        redisUtil.set(authentication.getName(),tokenInfo.getRefreshToken(), Duration.ofMillis(tokenInfo.getRefreshTokenExpirationTime()));
        return Response.makeResponse(HttpStatus.OK, "토큰 재발급을 성공하였습니다.", 0, tokenInfo);
    }
}
