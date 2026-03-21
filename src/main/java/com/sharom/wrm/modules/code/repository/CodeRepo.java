package com.sharom.wrm.modules.code.repository;

import com.sharom.wrm.modules.code.model.VerificationCode;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CodeRepo extends JpaRepository<VerificationCode, String> {


    @Query("""
    SELECT c FROM VerificationCode c
    WHERE c.userId = :userId 
    AND c.expiredAt < :now  
    ORDER BY c.createdAt DESC
    LIMIT 1
        """)
    Optional<VerificationCode> findByUserIdAndExpiredAt(@Param("userId") String userId, @Param("now") LocalDateTime now);


    @Query("""
    SELECT c FROM VerificationCode c
    WHERE c.userId = :userId 
    ORDER BY c.createdAt DESC
    LIMIT 1
        """)
    Optional<VerificationCode> findByUserId(@Param("userId") String userId);


    @Query("""
        SELECT c FROM VerificationCode c
        WHERE c.userId = :userId
        """)
    void deleteByUserId(@Param("userId") String userId);
}
