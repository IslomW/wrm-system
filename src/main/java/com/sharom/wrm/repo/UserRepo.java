package com.sharom.wrm.repo;

import com.sharom.wrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByUserName(String userName);
    
    boolean existsUserByUserName(String userName);
    
}
