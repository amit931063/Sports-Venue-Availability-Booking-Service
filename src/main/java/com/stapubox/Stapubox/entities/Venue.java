package com.stapubox.Stapubox.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "venues", indexes = {@Index(columnList = "sportCode")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String location;
    private String sportCode;
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("venue")
    private
    List<Slot> slots;
}
