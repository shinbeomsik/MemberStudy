package com.self.member.service.member;

import com.self.member.domain.member.Member;
import com.self.member.domain.refreshtoken.RefreshToken;
import com.self.member.dto.member.MemberDto;
import com.self.member.dto.response.ResponseDto;
import com.self.member.dto.response.ResponseTokenDto;
import com.self.member.provider.JwtProvider;
import com.self.member.repository.member.MemberRepository;
import com.self.member.repository.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: MemberServiceImpl.java
 * @패키지 정보	: com.side.foodpie.service.member
 * @클래스 설명	: member 처리
 * @작성자		: 신범식
 * @작성일		: 2024. 04. 21. 오전 10:30:57
 * @변경이력		:
 * ------------------------------------------------------
 * ------------------------------------------------------
 *  이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * ------------------------------------------------------
 * 1. 신범식 : 2024. 04. 21. :            : 최초 작성
 * 2. 신범식 : 2024. 04. 22  :            : login, loadUserByUsername 작성
 * 3. 신범식 : 2024. 04. 23  :            : updNickname, updPassword, updPhoneNumber 작성
 * 4. 신범식 : 2024. 05. 09  :            : saveMember , login 메서드 내용
 * 5. 신범식 : 2024. 05. 10  :            : updNickname, updPassword, updPhoneNumber 변경
 * 6. 신범식 : 2024. 06. 17  :            : refreshtoken 작성
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;
    /**
     * @메소드명  : saveMember
     * @설명	   : 회원가입
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 22. 오후 02:36:48
     * @param  : MemberDto
     * @return : ResponseEntity<ResponseDto>
     */
    @Override
    public ResponseEntity<ResponseDto> saveMember(MemberDto memberDto) {
         try {
             Member findMember = memberRepository.findByEmail(memberDto.getEmail());
             if (findMember != null) {
                 log.info("이미 가입된 회원입니다");
                 return ResponseDto.duplicateEmail();
             }
             int count = memberRepository.checkNickname(memberDto.getNickname());
             if (count != 0) {
                 log.info("이미 가입된 닉네임입니다");
                 return ResponseDto.duplicateNickname();
             }

             Member member = Member.createMember(memberDto , passwordEncoder);

             memberRepository.saveMember(member.getName() , member.getEmail() , member.getPassword() ,
                     member.getNickname() , member.getPhoneNumber() , member.getSignType());

         }catch (Exception e){
             e.printStackTrace();
             return MemberDto.databaseError();
         }
        return ResponseDto.success();
    }


    /**
     * @메소드명  : login
     * @설명	   : 로그인
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 22. 오후 02:36:48
     * @param  : String,String,PasswordEncoder
     * @return : ResponseEntity<? super ResponseTokenDto>
     */
    @Override
    public ResponseEntity<? super ResponseTokenDto> login(String email, String password , PasswordEncoder passwordEncoder) {

        String token = null;
        String refreshToken = null;

        try{
            if(email == null ||email.isEmpty()){
                return ResponseDto.validationFail();
            }

            if(password == null ||password.isEmpty()){
                return ResponseDto.validationFail();
            }

            Member findMember = memberRepository.findByEmail(email);

            if(findMember == null){
                return ResponseTokenDto.signInFail();
            }
            if (!passwordEncoder.matches(password, findMember.getPassword())) {
                return  ResponseTokenDto.signInFail();
            }

            token = jwtProvider.create(findMember.getEmail());
            refreshToken = jwtProvider.createRefreshToken(findMember.getEmail());

            refreshTokenRepository.saveToken(refreshToken , Instant.now().plus(7, ChronoUnit.DAYS),findMember.getEmail());

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseTokenDto.success(token , refreshToken);
    }

    /**
     * @메소드명  : refreshtoken
     * @설명	   : 리프레시 토큰 발행
     * @작성자   : 신범식
     * @작성일   : 2024. 06. 17. 오후 02:36:48
     * @param  : String
     * @return : ResponseEntity<? super ResponseTokenDto>
     */
    @Override
    public ResponseEntity<? super ResponseTokenDto> refreshtoken(String refreshToken) {
        String token = null;
       RefreshToken refreshToken1 = refreshTokenRepository.findByRefreshToken(refreshToken);

        Member member = refreshToken1.getEmail();
        log.info(">>>>" + member );
        
        try {
            if (refreshToken1.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
                refreshTokenRepository.delete(refreshToken1);
            }else {
                token = jwtProvider.create(member.getEmail());
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseTokenDto.success(token , refreshToken);
    }

    /**
     * @메소드명  : loadUserByUsername
     * @설명	   : Spring Security의 UserDetails 객체 생성 (로그인한 사용자 정보를 Security에 제공)
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 22. 오후 02:36:48
     * @param  : String
     * @return : UserDetails
     */
    public UserDetails loadUserByUsername(String email)  throws UsernameNotFoundException{
        Member member = memberRepository.findByEmail(email);

        UserDetails userDetails =  User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();

        return userDetails;
    }
    /**
     * @메소드명  : updNickname
     * @설명	   : 닉네임변경
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 23. 오후 02:36:48
     * @param  : String
     * @return : ResponseEntity<ResponseDto>
     */
    public ResponseEntity<ResponseDto> updNickname(String nickname) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try{
            if(nickname == null ||nickname.isEmpty()){
                return ResponseDto.validationFail();
            }

            memberRepository.updNickname(email , nickname);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    /**
     * @메소드명  : updPassword
     * @설명	   :  비밀번호 변경
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 23. 오후 02:36:48
     * @param  : String , String , PasswordEncoder
     * @return : ResponseEntity<ResponseDto>
     */
    public ResponseEntity<ResponseDto> updPassword(String password , String newpassword , PasswordEncoder passwordEncoder) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member findMember = memberRepository.findByEmail(email);

        try{

            if (!passwordEncoder.matches(password, findMember.getPassword())) {
                return ResponseDto.validationFail();
            }

            newpassword = passwordEncoder.encode(newpassword);


            memberRepository.updPassword(email , newpassword);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    /**
     * @메소드명  : updPhoneNumber
     * @설명	   :  휴대폰 번호 변경
     * @작성자   : 신범식
     * @작성일   : 2024. 04. 23. 오후 02:36:48
     * @param  : String
     * @return : ResponseEntity<ResponseDto>
     */
    public ResponseEntity<ResponseDto> updPhoneNumber(String PhoneNumber) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try{
            if(PhoneNumber == null ||PhoneNumber.isEmpty()){
                return ResponseDto.validationFail();
            }

            memberRepository.updPhoneNumber(email , PhoneNumber);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

}
