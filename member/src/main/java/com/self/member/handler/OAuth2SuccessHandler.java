package com.self.member.handler;

import com.self.member.provider.JwtProvider;
import com.side.foodpie.domain.member.Oauth2Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: OAuth2SuccessHandler.java
 * @패키지 정보	: com.side.foodpie.handler
 * @클래스 설명	: 소셜 로그인을 성공하면 JWT 토큰 생성
 * @작성자		: 신범식
 * @작성일		: 2024. 05. 06. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 05. 06. :            : 최초 작성
 * 2.
 * 3.

 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    /**
     * @param : HttpServletRequest , HttpServletResponse , Authentication
     * @메소드명 : onAuthenticationSuccess
     * @설명     : 소셜 로그인이 성공하였을때 JWT 토큰 발급
     * @작성자 : 신범식
     * @작성일 : 2024. 05. 06. 오후 02:36:48
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Oauth2Member oauth2Member = (Oauth2Member)authentication.getPrincipal();

        String email = oauth2Member.getName();
        String token = jwtProvider.create(email);

        response.sendRedirect("http://localhost:3000/auth/oauth-repsonse/" + token);
    }
}
