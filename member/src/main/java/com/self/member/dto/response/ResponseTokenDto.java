package com.self.member.dto.response;

import com.self.member.common.ResponseCode;
import com.self.member.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: ResponseTokenDto.java
 * @패키지 정보	: com.side.foodpie.dto.response
 * @클래스 설명	: ResponseTokenDto 을 vue.js 으로 보냄
 * @작성자		: 신범식
 * @작성일		: 2024. 05. 09. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 05. 09. :            : 최초 작성
 * 2.
 * 3.

 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@Getter
public class ResponseTokenDto extends ResponseDto{

    private String token;

     private ResponseTokenDto (String token) {
        super();
        this.token = token;
    }


    /**
     * @메소드명  : success
     * @설명	   :  성공하면 토큰을 프론트에 보냄
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 09. 오전 10:30:57
     * @param  : String
     * @return : ResponseEntity<ResponseTokenDto>
     */
    public static ResponseEntity<ResponseTokenDto> success (String token){
        ResponseTokenDto responseBody = new ResponseTokenDto(token);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    /**
     * @메소드명  : signInFail
     * @설명	   :  로그인 실패
     * @작성자   : 신범식
     * @작성일   : 2024. 05. 09. 오전 10:30:57
     * @param  :
     * @return : ResponseEntity<ResponseTokenDto>
     */
    public static ResponseEntity<ResponseDto> signInFail () {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL , ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody) ;
    }
}
