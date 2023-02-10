package Capstone.Bioproject.web.Mypage;

import Capstone.Bioproject.web.config.jwt.JwtAuthenticationFilter;
import Capstone.Bioproject.web.config.jwt.JwtTokenProvider;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.domain.dtos.MyInfoUpdateDto;
import Capstone.Bioproject.web.domain.dtos.MypageResponseDto;
import Capstone.Bioproject.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MypageResponseDto getMyPage(HttpServletRequest request) {
        String token=jwtAuthenticationFilter.resolveToken(request);
        String tokenInfo=jwtTokenProvider.parseClaims(token).getSubject();
        String userInfo[]=tokenInfo.split(",");
        User user = userRepository.findByEmailAndProvider(userInfo[0],userInfo[1])
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다.: " + userInfo[0]));

        return new MypageResponseDto(user);
    }

    @Transactional
    public ResponseEntity<Map<String, Boolean>> updateInfo(MyInfoUpdateDto updateDto){
        User user=userRepository.findById(updateDto.getId())
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id" + updateDto.getId()));
        user.update(updateDto.getName(),updateDto.getAge(),updateDto.getSex());

        Map<String, Boolean> response = new HashMap<>();

        response.put("update",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}

