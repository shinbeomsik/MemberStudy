package com.self.member.repository.member;


import com.self.member.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO member " +
                   "(name, email, password,nickname,phone_number, create_date, update_date, use_yn ,role , sign_type) " +
                   "VALUES " +
                   "(:name, :email, :password, :nickname, :phoneNumber, NOW(), NOW(), 'Y' , 'USER' , :signType)", nativeQuery = true)
    void saveMember(@Param("name") String name, @Param("email") String email,
                    @Param("password") String password, @Param("nickname") String nickname,
                    @Param("phoneNumber") String phoneNumber, @Param("signType") String signType); // 회원 가입

    Member findByEmail(String mbrEmail);       // 중복 회원 검사

    @Query("SELECT COUNT(m) FROM Member m WHERE m.nickname = :nickname and m.useYn = 'Y'")
    int checkNickname(@Param("nickname") String nickname);  // 중복 닉네임 검사


    @Transactional
    @Modifying
    @Query(value = "UPDATE member m set m.nickname = :nickname , m.update_date = NOW() "+
            "WHERE m.email = :email and m.use_yn = 'Y'", nativeQuery = true)
    void updNickname(@Param("email") String email , @Param("nickname") String nickname); // 닉네임 변경

    @Transactional
    @Modifying
    @Query(value = "UPDATE member m set m.password = :newpassword , m.update_date = NOW() "+
            "WHERE m.email = :email and m.use_yn = 'Y'", nativeQuery = true)
    void updPassword(@Param("email") String email , @Param("newpassword") String newpassword); // 비밀번호 변경

    @Transactional
    @Modifying
    @Query(value = "UPDATE member m set m.phone_number = :phoneNumber , m.update_date = NOW() "+
            "WHERE m.email = :email and m.use_yn = 'Y'", nativeQuery = true)
    void updPhoneNumber(@Param("email") String email , @Param("phoneNumber") String phoneNumber); // 휴대폰 변경

}
