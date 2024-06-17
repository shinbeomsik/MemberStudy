package com.self.member.domain.refreshtoken;

import com.self.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: Oauth2Member.java
 * @패키지 정보	: com.side.foodpie.domain.RefreshToken
 * @클래스 설명	: Oauth2Member 생성
 * @작성자		: 신범식
 * @작성일		: 2024. 05. 06. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 06. 17 :            : 최초 작성
 * 2.
 * 3.
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String refreshToken;

    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "member_email")
    private Member email;
}
