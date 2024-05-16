package com.self.member.service.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.member.domain.member.Member;
import com.self.member.repository.member.MemberRepository;
import com.side.foodpie.domain.member.Oauth2Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @프로젝트명	: foodpie 프로젝트
 * @클래스명		: MemberServiceImpl.java
 * @패키지 정보	: com.side.foodpie.service.oauth2
 * @클래스 설명	: OAuth2 처리
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
 * 4.
 * ------------------------------------------------------
 * ------------------------------------------------------
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    /**
     * @param : request
     * @메소드명 : loadUser
     * @설명     : 소셜 로그인 부분
     * @작성자 : 신범식
     * @작성일 : 2024. 05. 06. 오후 02:36:48
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        
        try { // 단순히 무엇을 가져왔는지 체크하기위해 존재  나중에 필히 삭제해야됨
            log.info(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception exception){
            exception.printStackTrace();
        }

        Member member = null;
        String email = "";

        if (oauthClientName.equals("kakao")){
            Map<String,String> properties = (Map<String,String>)oAuth2User.getAttributes().get("properties");

            Map<String,String> kakaoAccount = (Map<String,String>)oAuth2User.getAttributes().get("kakao_account");
            email = kakaoAccount.get("email");
            String name = kakaoAccount.get("name");
            String phoneNumber = kakaoAccount.get("phone_number").replace("+82 ","0").replaceAll("-" , "");
            String nickname = properties.get("nickname");


            member = Member.createKakao(email,name, phoneNumber ,nickname );
            log.info("member >>>"  + member.toString());
        }
        if (oauthClientName.equals("naver")){
            Map<String,String> responseMap = (Map<String,String>)oAuth2User.getAttributes().get("response");
            email = responseMap.get("email");
            String name = responseMap.get("name");
            String nickname = responseMap.get("nickname");
            String phoneNumber = responseMap.get("mobile").replaceAll("-" , "");

            member = Member.createNaver(email , name, nickname, phoneNumber);
            log.info("member >>>"  + member.toString());
        }

        Member checkMember = memberRepository.findByEmail(email);

        if(checkMember == null) {
            memberRepository.saveMember(member.getName(), member.getEmail(), member.getPassword(),
                    member.getNickname(), member.getPhoneNumber(), member.getSignType());

        }
        return new Oauth2Member(member.getEmail());
    }
}
