package Capstone.Bioproject.web.Login;

import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;
    @Value("${naver.client_id}")
    private String naver_Id;
    @Value(("${naver.client-secret}"))
    private String naver_Secret;
    public void googleDelete(String access_Token){
        String apiUrl="https://accounts.google.com/o/oauth2/revoke?token="+access_Token;

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
        System.out.println("확인용");
        System.out.println(access_Token);
        requestToServer("https://kapi.kakao.com/v1/user/unlink","POST","kakao",access_Token);
    }
    private void requestToServer(String apiURL,String method, String headStr, String access_Token){
        String reqURL =apiURL;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if(headStr!=null) {
                conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            }
            System.out.println("보내기까지 성공");
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
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
}
