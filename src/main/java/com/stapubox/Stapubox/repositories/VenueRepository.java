package com.stapubox.Stapubox.repositories;

import com.stapubox.Stapubox.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    @Query("SELECT DISTINCT v FROM Venue v JOIN Slot s ON v.id = s.venue.id " +
            "WHERE v.sportCode = :sport " +
            "AND s.isBooked = false " +
            "AND s.startTime >= :start " +
            "AND s.endTime <= :end")
    List<Venue> findAvailableVenues(@Param("sport") String sport,
                                    @Param("start") LocalDateTime start,
                                    @Param("end") LocalDateTime end);

}
