package Capstone.Bioproject.web.config.oauth.config;

import Capstone.Bioproject.web.config.oauth.util.RedisUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisCrudTest {
    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    @Autowired
    private RedisUtil redisUtil;

    @BeforeEach
    void shutDown(){
        redisUtil.set(KEY,VALUE,DURATION);
    }

    @AfterEach
    void tearDown(){
        redisUtil.delete(KEY);
    }

    @Test
    @DisplayName("데이터 조회")
    void saveAndFindTest() throws Exception{
        String findValue=redisUtil.get(KEY);
        assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("데이터 수정")
    void updateTest() throws Exception{
        //given
        String updateValue="updateValue";
        redisUtil.set(KEY,updateValue,DURATION);
        //when
        String findValue=redisUtil.get(KEY);
        //then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }

    @Test
    @DisplayName("데이터 삭제")
    void deleteTest(){
        //when
        redisUtil.delete(KEY);
        String findValue=redisUtil.get(KEY);
        //then
        assertThat(findValue).isEqualTo(null);
    }

    @Test
    @DisplayName("만료시간이 지나면 삭제")
    void expiredTest() throws Exception {
        // when
        String findValue = redisUtil.get(KEY);
        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
                () -> {
                    String expiredValue = redisUtil.get(KEY);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("false");
                }
        );
    }


}