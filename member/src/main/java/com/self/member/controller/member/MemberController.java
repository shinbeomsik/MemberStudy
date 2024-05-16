package com.self.member.controller.member;


import com.self.member.dto.member.MemberDto;
import com.self.member.dto.response.ResponseDto;
import com.self.member.dto.response.ResponseTokenDto;
import com.self.member.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: MemberController.java
 * @패키지 정보	: com.side.foodpie.controller
 * @클래스 설명	: Member 처리
 * @작성자		: 신범식
 * @작성일		: 2024. 04. 21. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 04. 21. :            : 최초 작성
 * 2. 신범식 : 2024. 04. 22. :            : login, handleValidationExceptions 작성
 * 3. 신범식 : 2024. 04. 23  :            : updateNickname, updatePassword, updatePhoneNumber, mapping에  /member 작성
 * 4. 신범식 : 2024. 05. 09  :            : saveMember, login 메서드 내용 변경 , handleValidationExceptions 삭제
 * 5. 신범식 : 2024. 05. 10  :            : updateNickname, updatePassword, updatePhoneNumber 변경
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@Controller
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    /**
     * @메소드명  : saveMember
     * @설명	   : 회원가입
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 21. 오전 10:30:57
     * @param  : MemberFormDto
     * @return : ResponseEntity<? super MemberDto>
     */
    @PostMapping("/member/join")
    @ResponseBody
    public ResponseEntity<? super MemberDto> saveMember(@RequestBody @Valid MemberDto memberDto){
            ResponseEntity<? super MemberDto> response = memberService.saveMember(memberDto);
            return response;
    }

    /**
     * @메소드명  : login
     * @설명	   : 로그인
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 22. 오전 10:30:57
     * @param  : Map<String, String>
     * @return : ResponseEntity<? super ResponseTokenDto>
     */
    @PostMapping("/member/login")
    public ResponseEntity<? super ResponseTokenDto> login(@RequestBody Map<String, String> paramMap) {
            String email = paramMap.get("email");
            String password = paramMap.get("password");
            ResponseEntity<? super ResponseTokenDto> response = memberService.login(email,password, passwordEncoder);

            return response;
    }

    /**
     * @메소드명  : updateNickname
     * @설명	   : 이름 변경
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 23. 오전 10:30:57
     * @param  : Map<String ,String>
     * @return : esponseEntity<ResponseDto>
     */
    @PutMapping("/member/updnickname")
    @ResponseBody
    public ResponseEntity<ResponseDto> updateNickname(@RequestBody Map<String, String> paramMap){
        String nickname = paramMap.get("nickname");
        ResponseEntity<ResponseDto> response = memberService.updNickname(nickname);
        return response;
    }

    /**
     * @메소드명  : updatePassword
     * @설명	   :  비밀번호 변경
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 23. 오전 10:30:57
     * @param  : Map<String ,String>
     * @return : ResponseEntity<ResponseDto>
     */
    @PutMapping("/member/updpassword")
    @ResponseBody
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody Map<String, String> paramMap){
        String password = paramMap.get("password");
        String newpassword = paramMap.get("newpassword");
        ResponseEntity<ResponseDto> response = memberService.updPassword(password , newpassword , passwordEncoder);
        return response;
    }

    /**
     * @메소드명  : updatePhoneNumber
     * @설명	   :  핸드폰 번호 변경
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 23. 오전 10:30:57
     * @param  : Map<String ,String>
     * @return : esponseEntity<ResponseDto>
     */
    @PutMapping("/member/updphonenumber")
    @ResponseBody
    public ResponseEntity<ResponseDto> updatePhoneNumber(@RequestBody  Map<String, String> paramMap){
        String phoneNumber = paramMap.get("phoneNumber");
        ResponseEntity<ResponseDto> response = memberService.updPhoneNumber(phoneNumber);
        return response;
    }

}
