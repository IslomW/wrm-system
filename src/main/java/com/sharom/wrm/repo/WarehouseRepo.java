package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepo extends JpaRepository<Warehouse, String> {

    Optional<Warehouse>findByCode(String code);

    Page<Warehouse> findAllByActiveTrue(Pageable pageable);
}
