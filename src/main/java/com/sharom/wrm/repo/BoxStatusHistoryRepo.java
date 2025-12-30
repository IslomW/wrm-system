package com.sharom.wrm.repo;

import com.sharom.wrm.entity.BoxStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxStatusHistoryRepo extends JpaRepository<BoxStatusHistory, String > {

    List<BoxStatusHistory>
    findByBoxIdOrderByUpdatedAtDesc(String boxId);
}
