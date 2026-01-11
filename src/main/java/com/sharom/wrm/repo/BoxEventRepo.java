package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoxEventRepo extends JpaRepository<BoxEvent, String> {
    Optional<BoxEvent> findTopByBoxOrderByEventTimeDesc(Box box);

    Page<BoxEvent> findByBoxIdOrderByEventTimeAsc(String boxId, Pageable pageable);


    // Последнее событие бокса
    Optional<BoxEvent> findTopByBoxIdOrderByEventTimeDesc(String boxId);

    // Все события для shipment
    Page<BoxEvent> findByShipmentIdOrderByEventTimeAsc(String shipmentId, Pageable pageable);

    // Последнее событие каждого бокса для shipment
    @Query("""
            SELECT e FROM BoxEvent e 
            WHERE e.shipment.id = :shipmentId 
            AND e.type = 'LOADED_TO_TRUCK'
            ORDER BY e.eventTime DESC
            """)
    Page<BoxEvent> findLastLoadedEventsByShipment(
            @Param("shipmentId") String shipmentId,
            Pageable pageable
    );


}
