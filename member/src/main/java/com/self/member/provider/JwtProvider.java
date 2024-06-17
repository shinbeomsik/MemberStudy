package com.self.member.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: JwtProvider.java
 * @패키지 정보	: com.side.foodpie.provider
 * @클래스 설명	: JWT 토큰 생성 , JWT 토큰의 유효성 검사
 * @작성자		: 신범식
 * @작성일		: 2024. 05. 06. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 05. 06. :            : 최초 작성
 * 2. 신범식 : 2024. 06. 17  :            : createRefreshToken 추가작성
 * 3.

 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@Component
@Slf4j
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    /**
     * @param : String
     * @메소드명 : create
     * @설명     : JWT 토큰 생성
     * @작성자 : 신범식
     * @작성일 : 2024. 05. 06. 오후 02:36:48
     */
    public String create(String email) {

        Date expiredDate = Date.from(Instant.now().plus(1,ChronoUnit.MINUTES));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();

        return jwt;
    }
    /**
     * @param : String
     * @메소드명 : createRefreshToken
     * @설명     : JWT 리프레시토큰 생성
     * @작성자 : 신범식
     * @작성일 : 2024. 06. 17. 오후 02:36:48
     */
    public String createRefreshToken(String email) {

        Date expiredDate = Date.from(Instant.now().plus(7,ChronoUnit.DAYS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();

        return jwt;
    }

    /**
     * @param : String
     * @메소드명 : validate
     * @설명     : JWT 토큰 유효성 검사
     * @작성자 : 신범식
     * @작성일 : 2024. 05. 06. 오후 02:36:48
     */
    public String validate(String jwt) {
        String subject = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try{
             subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                     .getSubject();
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }

        return subject;
    }
}
