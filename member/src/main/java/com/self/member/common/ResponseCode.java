package com.self.member.common;

public interface ResponseCode {

    String SUCCESS = "SU";  //성공

    String VALIDATION_FAIL = "VF"; // 유효성 실패
    String DUPLICATE_EMAIL = "DE"; // 이메일 중복
    String DUPLICATE_NICKNAME = "DN"; // 닉네임 중복

    String SIGN_IN_FAIL = "SF"; // 로그인 실패

    String CERTIFICATION_FAIL = "CF"; // 인증 실패

    String DATABASE_ERROR = "DBE"; // 데이터베이스 오류
}
