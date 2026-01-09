package com.stapubox.Stapubox.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "sports")
@Getter
@Setter
public class Sport {
          @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Integer sportId;
        private String sportName;
    }


