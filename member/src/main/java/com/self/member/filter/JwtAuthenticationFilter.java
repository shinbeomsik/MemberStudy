package com.self.member.filter;

import com.self.member.domain.member.Member;
import com.self.member.provider.JwtProvider;
import com.self.member.repository.member.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: JwtAuthenticationFilter.java
 * @패키지 정보	: com.side.foodpie.filter
 * @클래스 설명	: JWT 필터
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
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = parseBearerToken(request);

            if( token == null){
                filterChain.doFilter(request,response);
                return;
            }

            String email = jwtProvider.validate(token);
            if(email == null){
                filterChain.doFilter(request,response);
                return;
            }

            Member member = memberRepository.findByEmail(email);
            String role = member.getRole();

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            AbstractAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail() , /*이부분에 패스워드 넣어도됨*/null ,authorities );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception exception){
            exception.printStackTrace();
        }

        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization){
            return null;
        }

        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer){
            return null;
        }

        String token = authorization.substring(7);

        return token;

    }
}
