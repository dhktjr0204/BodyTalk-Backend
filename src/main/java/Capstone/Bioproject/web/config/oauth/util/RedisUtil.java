package Capstone.Bioproject.web.config.oauth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
//redis에 저장,조회,삭제하는 메서드를 구현하는 클래스
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;
    //저장
    public void set(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }
    //만료시간과 함께 저장
    public void set(String key, String value, Duration duration){
        redisTemplate.opsForValue().set(key,value,duration);
    }
    //key를 기반으로 데이터 조회
    public String get(String key){
        String getKey = redisTemplate.opsForValue().get(key);
        return getKey;
    }

    //key를 기반으로 데이터 삭제
    public void delete(String key){redisTemplate.delete(key);}


}
