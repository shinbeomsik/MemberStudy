package com.self.member.common;

public interface ResponseMessage {

    String SUCCESS = "Success.";  // 성공

    String VALIDATION_FAIL = "Validation failed."; // 유효성 실패
    String DUPLICATE_EMAIL = "Duplicate Email."; // 이메일 중복
    String DUPLICATE_NICKNAME = "Duplicate Nickname."; // 닉네임 중복

    String SIGN_IN_FAIL = "Login information mismatch."; // 로그인 실패

    String CERTIFICATION_FAIL = "Certification failed."; // 인증 실패

    String DATABASE_ERROR = "Datebase error."; // 데이터베이스 오류
}
