package com.self.member.dto.member;


import com.self.member.dto.response.ResponseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: MemberDto.java
 * @패키지 정보	: com.side.foodpie.dto.member
 * @클래스 설명	: MemberDto 을 vue.js 에서 받아옴
 * @작성자		: 신범식
 * @작성일		: 2024. 04. 21. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 03. 25. :            : 최초 작성
 * 2. 신범식 : 2024. 04. 23. :            : String 데이터 타입 phoneNumber 추가
 * 3.
 * 4.
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@NoArgsConstructor
@Getter
public class MemberDto extends ResponseDto {

    @NotBlank(message = "이름은 필수 입력 값이다.")
    private String name; // 이름

    @NotBlank(message = "이메일은 필수 입력 값이다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email; // 이메일

    @NotBlank(message = "비밀번호는 필수 입력 값이다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password; // 비밀번호

    @NotBlank(message = "닉네임은 필수 입력 값이다.")
    private String nickname; // 닉네임

    private String gender; // 성별

    @Pattern(regexp="(^\\d{10,11}$)",
            message = "휴대폰 번호는 -를 제외하고 입력해주세요")
    private String phoneNumber; // 휴대폰 번호

    @Builder
    public MemberDto(String name, String email, String password, String nickname, String gender , String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

}
