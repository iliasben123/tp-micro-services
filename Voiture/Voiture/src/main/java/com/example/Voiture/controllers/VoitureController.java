package com.example.Voiture.controllers;

import com.example.Voiture.entities.Voiture;
import com.example.Voiture.entities.Client;
import com.example.Voiture.repositories.VoitureRepository;
import com.example.Voiture.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientService clientService;

    // Get all voitures
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllVoitures() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();
            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching voitures: " + e.getMessage());
        }
    }

    // Get voiture by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getVoitureById(@PathVariable Long id) {
        try {
            Optional<Voiture> voitureOpt = voitureRepository.findById(id);
            if (voitureOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Voiture Introuvable");
            }

            Voiture voiture = voitureOpt.get();
            Client client = clientService.getClientById(voiture.getIdClient()); // Updated to use `getIdClient`
            voiture.setClient(client);
            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Voiture not found with ID " + id + ": " + e.getMessage());
        }
    }

    // Get voiture by client ID
    @GetMapping("/client/{id}")
    public ResponseEntity<?> getVoituresByClientId(@PathVariable Long id) {
        try {
            List<Voiture> voitures = voitureRepository.findByIdClient(id); // Updated method name
            if (voitures.isEmpty()) {
                return ResponseEntity.status(404).body("No voitures found for client ID " + id);
            }
            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching voitures for client ID " + id + ": " + e.getMessage());
        }
    }

    // Add a new voiture
    @PostMapping("/client/{clientId}")
    public ResponseEntity<?> addVoiture(@RequestBody Voiture voiture, @PathVariable Long clientId) {
        try {
            Client client = clientService.getClientById(clientId);
            if (client != null) {
                voiture.setClient(client);
                voiture.setIdClient(clientId); // Updated to use `setIdClient`
                Voiture savedVoiture = voitureRepository.save(voiture);
                return ResponseEntity.ok(savedVoiture);
            } else {
                return ResponseEntity.status(404).body("Client not found with ID " + clientId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving voiture: " + e.getMessage());
        }
    }

    // Update a voiture by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoiture(@RequestBody Voiture voiture, @PathVariable Long id) {
        try {
            Optional<Voiture> existingVoitureOpt = voitureRepository.findById(id);
            if (existingVoitureOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Voiture not found with ID " + id);
            }

            Voiture existingVoiture = existingVoitureOpt.get();
            if (voiture.getMarque() != null) existingVoiture.setMarque(voiture.getMarque());
            if (voiture.getMatricule() != null) existingVoiture.setMatricule(voiture.getMatricule());
            if (voiture.getModel() != null) existingVoiture.setModel(voiture.getModel());
            if (voiture.getIdClient() != null) { // Updated to use `getIdClient`
                Client client = clientService.getClientById(voiture.getIdClient());
                if (client != null) {
                    existingVoiture.setClient(client);
                    existingVoiture.setIdClient(voiture.getIdClient()); // Updated to use `setIdClient`
                }
            }

            Voiture updatedVoiture = voitureRepository.save(existingVoiture);
            return ResponseEntity.ok(updatedVoiture);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating voiture: " + e.getMessage());
        }
    }
}
