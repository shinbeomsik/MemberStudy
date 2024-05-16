package com.self.member.service.member;

import com.self.member.dto.member.MemberDto;
import com.self.member.dto.response.ResponseDto;
import com.self.member.dto.response.ResponseTokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface MemberService  {
    ResponseEntity<ResponseDto> saveMember(MemberDto memberDto);

    ResponseEntity<? super ResponseTokenDto> login(String email, String password , PasswordEncoder passwordEncoder);

    UserDetails loadUserByUsername(String email);

    ResponseEntity<ResponseDto> updNickname(String name);

    ResponseEntity<ResponseDto> updPassword(String password , String newpassword ,PasswordEncoder passwordEncoder );

    ResponseEntity<ResponseDto> updPhoneNumber(String phoneNumber);

}
