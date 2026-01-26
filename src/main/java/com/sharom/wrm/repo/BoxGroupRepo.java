package com.sharom.wrm.repo;

import com.sharom.wrm.entity.BoxGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoxGroupRepo extends JpaRepository<BoxGroup, String> {

    int countByOrderId(String orderId);

    @Query("""
                SELECT DISTINCT bg
                FROM BoxGroup bg
                JOIN bg.boxes b
                WHERE b.currentWarehouse.id = :warehouseId
            """)
    Page<BoxGroup> findByWarehouseId(@Param("warehouseId") String warehouseId,
                                     Pageable pageable);
}
