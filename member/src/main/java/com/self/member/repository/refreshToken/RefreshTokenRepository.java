package com.self.member.repository.refreshToken;

import com.self.member.domain.refreshtoken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Integer> {

    RefreshToken findByRefreshToken(String token);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO refresh_token " +
            "(refresh_token, expiry_date , member_email) " +
            "VALUES " +
            "(:refreshToken, :expiryDate, :email)", nativeQuery = true)
    void saveToken(@Param("refreshToken") String name, @Param("expiryDate") Instant expiryDate,
                    @Param("email") String email); // 리프레시 토큰 저장
}
