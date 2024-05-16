package com.self.member.handler;

import com.self.member.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: ValidationExceptionHandler.java
 * @패키지 정보	: com.side.foodpie.handler
 * @클래스 설명	: 유효성 실패했을때
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
@RestControllerAdvice
public class ValidationExceptionHandler {

    /**
     * @param : Exception
     * @메소드명 : validationExceptionHandler
     * @설명     : 유효성 실패
     * @작성자 : 신범식
     * @작성일 : 2024. 05. 06. 오후 02:36:48
     */
    @ExceptionHandler({MethodArgumentNotValidException.class , HttpMessageConversionException.class})
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception){
        return ResponseDto.validationFail();
    }
}
