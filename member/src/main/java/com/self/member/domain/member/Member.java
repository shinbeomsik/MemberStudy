package com.self.member.domain.member;

import com.self.member.dto.member.MemberDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Date;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: Member.java
 * @패키지 정보	: com.side.foodpie.domain.member
 * @클래스 설명	: member 데이터베이스에 전달
 * @작성자		: 신범식
 * @작성일		: 2024. 04. 21. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 04. 21. :            : 최초 작성
 * 2. 신범식 : 2024. 04. 22. :            : role데이터 타입 String 변경
 * 3. 신범식 : 2024. 04. 23. :            : String 데이터 타입 phoneNumber 추가
 * 4. 신범식 : 2024. 05. 06  :            : 회원가입 타입 추가 , createKakao 추가 , createNaver 추가
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@NoArgsConstructor
@Getter
@Table(name = "member")
@Entity
@Data
public class Member {

    private String name; // 이름

    @Id
    private String email; // 이메일

    private String password; // 비밀번호

    private String nickname; // 닉네임

    private String phoneNumber; // 휴대폰 번호

    private Date createDate; // 생성일자

    private Date updateDate; // 변경 일자

    private String useYn; // default Y -> 사용중   N-> 탈퇴


    private String role;  // 관리자 ADM , 사용자 USER

    private String signType; // 회원 가입할때 타입  웹페이지 WEB , 카카오 KAKAO , 네이버 NAVER

    @Builder
    public Member(String name, String email, String password, String nickname,
                  String phoneNumber, Date createDate, Date updateDate, String useYn, String role , String signType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.useYn = useYn;
        this.role = role;
        this.signType = signType;
    }

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .phoneNumber(memberDto.getPhoneNumber())
                .password(passwordEncoder.encode(memberDto.getPassword()))      // 암호화 처리
                .nickname(memberDto.getNickname())
                .signType("WEB")
                .build();

        return member;
    }

    public static Member createKakao(String email, String name, String phoneNumber , String nickname) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .signType("KAKAO")
                .build();

        return member;
    }

    public static Member createNaver(String email, String name, String nickname , String phoneNumber) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .signType("NAVER")
                .build();

        return member;
    }
}
