package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
}
