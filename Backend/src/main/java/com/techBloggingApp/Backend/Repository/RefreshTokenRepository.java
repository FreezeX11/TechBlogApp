package com.techBloggingApp.Backend.Repository;

import com.techBloggingApp.Backend.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteByUser_Id(Long userId);
}
