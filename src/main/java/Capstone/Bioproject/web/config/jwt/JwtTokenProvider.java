package Capstone.Bioproject.web.config.jwt;

import Capstone.Bioproject.web.config.oauth.dto.TokenResponseDto;
import Capstone.Bioproject.web.config.oauth.util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final String BEARER_TYPE = "Bearer";
    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14; // 2 week

    private final Key key;
    private final RedisUtil redisUtil;

    public JwtTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey, RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }

    public TokenResponseDto generateToken(Authentication authentication,String email, String provider) {
        Date tokenAccessExpiresIn = getTokenExpiresIn(ACCESS_TOKEN_EXPIRE_TIME);
        Date tokenRefreshExpiresIn = getTokenExpiresIn(REFRESH_TOKEN_EXPIRE_TIME);
        String authorities = getAuthorities(authentication);
        String userInfo = email + "," + provider;
        //Access Token 생성
        String accessToken= Jwts.builder()
                .setSubject(userInfo)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(tokenAccessExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(tokenRefreshExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponseDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    public TokenResponseDto reGenerateToken(Authentication authentication,String email,String provider, String refreshToken) {
        Date tokenAccessExpiresIn = getTokenExpiresIn(ACCESS_TOKEN_EXPIRE_TIME);
        String authorities = getAuthorities(authentication);
        String userInfo = email + "," + provider;
        //Access Token 생성
        String accessToken= Jwts.builder()
                .setSubject(userInfo)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(tokenAccessExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponseDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        //권한 부여
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //userDetails 객체 만들어서 Authentication리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("Expired JWT Token", expiredJwtException);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (SignatureException e){
            log.info("JWT signature does not match",e);
        }
        catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //지정 시간+ 현재 시간으로 만료 시간 정해줌
    private Date getTokenExpiresIn(long tokenExpireTime) {
        long now = (new Date()).getTime();
        return new Date(now + tokenExpireTime);
    }

    //accessToken에 저장된 만료시간 가져옴
    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}