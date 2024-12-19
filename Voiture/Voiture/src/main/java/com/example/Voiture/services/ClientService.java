package com.example.Voiture.services;

import com.example.Voiture.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SERVICE-CLIENT") // Ensure "SERVICE-CLIENT" matches your Eureka registration
public interface ClientService {
    @GetMapping("/clients/{id}")
    Client getClientById(@PathVariable("id") Long id);
}
