package com.sharom.wrm.modules.warehouse.repository;

import com.sharom.wrm.modules.warehouse.model.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepo extends JpaRepository<Warehouse, String> {

    Optional<Warehouse>findByCode(String code);

    Page<Warehouse> findAllByActiveTrue(Pageable pageable);
}
