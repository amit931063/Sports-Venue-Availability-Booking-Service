package com.stapubox.Stapubox.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "slots", indexes = {
        @Index(name = "idx_venue_time", columnList = "venue_id, startTime, endTime")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    @JsonIgnore
    private Venue venue;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean isBooked = false;

    @Version
    private Integer version;

}
