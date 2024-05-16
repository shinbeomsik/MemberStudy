package com.side.foodpie.domain.member;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;


/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: Oauth2Member.java
 * @패키지 정보	: com.side.foodpie.domain.Oauth2Member
 * @클래스 설명	: Oauth2Member 생성
 * @작성자		: 신범식
 * @작성일		: 2024. 05. 06. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 05. 06 :            : 최초 작성
 * 2.
 * 3.
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2Member implements OAuth2User {

    private  String email;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return  this.email;
    }
}
