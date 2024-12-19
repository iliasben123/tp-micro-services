package com.example.Voiture.repositories;

import com.example.Voiture.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findByIdClient(Long idClient); // Updated to match field name
}
