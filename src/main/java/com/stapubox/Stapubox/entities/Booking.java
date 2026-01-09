package com.stapubox.Stapubox.entities;


import com.stapubox.Stapubox.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Data
@Builder @NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "slot_id")
    private Slot slot;

    private String userName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

}
