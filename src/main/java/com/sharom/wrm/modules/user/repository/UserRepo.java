package com.sharom.wrm.modules.user.repository;

import com.sharom.wrm.modules.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByUserName(String userName);
    
    boolean existsUserByUserName(String userName);

    boolean existsByUserNameIgnoreCase(String userName);

    boolean existsUserByPhone(String phone);

}
