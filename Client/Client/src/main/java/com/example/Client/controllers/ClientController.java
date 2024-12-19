package com.example.Client.controllers;

import com.example.Client.entities.Client;
import com.example.Client.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        logger.info("Fetching all clients");
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        try {
            logger.info("Fetching client with ID: {}", id);
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new Exception("Client not found"));
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            logger.error("Error fetching client with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(404).body("Client not found");
        }
    }
}
