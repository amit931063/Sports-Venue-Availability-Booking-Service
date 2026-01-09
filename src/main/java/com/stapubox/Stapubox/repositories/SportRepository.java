package com.stapubox.Stapubox.repositories;

import com.stapubox.Stapubox.entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {


    Optional<Sport> findBySportId(Long sportId);

    Optional<Sport> findBySportName(String sportName);
}
