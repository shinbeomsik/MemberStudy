package com.self.member.dto.response;

import com.self.member.common.ResponseCode;
import com.self.member.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: ResponseDto.java
 * @패키지 정보	: com.side.foodpie.dto.response
 * @클래스 설명	: ResponseDto 을 vue.js 으로 보냄
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
@AllArgsConstructor
@Getter
public class ResponseDto {

    private String code;
    private String message;

    public ResponseDto(){
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS;
    }

    /**
     * @메소드명  : databaseError
     * @설명	   :  데이터베이스 에러
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 06. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseDto>
     */
    public static ResponseEntity<ResponseDto> databaseError(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR , ResponseMessage.DATABASE_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    /**
     * @메소드명  : validationFail
     * @설명	   :  유효성 실패
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 06. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseDto>
     */
    public static ResponseEntity<ResponseDto> validationFail(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAIL , ResponseMessage.VALIDATION_FAIL);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }


    /**
     * @메소드명  : success
     * @설명	   :  성공
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 06. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseDto>
     */
    public static ResponseEntity<ResponseDto> success () {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SUCCESS , ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    /**
     * @메소드명  : duplicateEmail
     * @설명	   :  이메일 중복
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 06. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseDto>
     */
    public static ResponseEntity<ResponseDto> duplicateEmail () {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_EMAIL , ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    /**
     * @메소드명  : duplicateNickname
     * @설명	   :  닉네임 중복
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 06. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseDto>
     */
    public static ResponseEntity<ResponseDto> duplicateNickname () {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME , ResponseMessage.DUPLICATE_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    /**
     * @메소드명  : certificationFail
     * @설명	   :  인증 실패
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 06. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseDto>
     */
    public static ResponseEntity<ResponseDto> certificationFail () {
        ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL , ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
