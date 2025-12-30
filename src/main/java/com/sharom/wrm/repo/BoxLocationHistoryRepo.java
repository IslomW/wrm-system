package com.sharom.wrm.repo;

import com.sharom.wrm.entity.BoxLocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoxLocationHistoryRepo extends JpaRepository<BoxLocationHistory, String> {

    Optional<BoxLocationHistory>
    findTopByBoxIdOrderByArrivedAtDesc(String boxId);

    List<BoxLocationHistory>
    findByBoxIdOrderByArrivedAtDesc(String boxId);
}
