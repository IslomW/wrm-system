package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoxRepo extends JpaRepository<Box, String> {

    @Modifying
    @Query(value = "UPDATE box SET box_group_id = NULL WHERE id = :boxId", nativeQuery = true)
    void detachBox(@Param("boxId") String boxId);
}
