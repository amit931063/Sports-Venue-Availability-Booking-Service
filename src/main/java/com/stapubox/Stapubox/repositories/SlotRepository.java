package com.stapubox.Stapubox.repositories;

import com.stapubox.Stapubox.entities.Slot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public   interface  SlotRepository  extends JpaRepository<Slot,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Slot s WHERE s.id = :id")
    Optional<Slot> findByIdWithLock(@Param("id") Long id);
    @Query("SELECT s FROM Slot s WHERE s.venue.id = :venueId AND " +
            "(:startTime < s.endTime AND :endTime > s.startTime)")
    List<Slot> findOverlappingSlots(@Param("venueId") Long venueId,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);
}
