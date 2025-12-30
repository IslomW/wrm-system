package com.sharom.wrm.repo;

import com.sharom.wrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
