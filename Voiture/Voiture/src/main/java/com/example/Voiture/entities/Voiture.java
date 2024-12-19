package com.example.Voiture.entities;

import jakarta.persistence.*;

@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String matricule;
    private String model;
    private Long idClient; // Change from 'id_client' to 'idClient'

    @Transient
    private Client client;

    // Default constructor
    public Voiture() {}

    // Parameterized constructor
    public Voiture(Long id, String marque, String matricule, String model, Long idClient, Client client) {
        this.id = id;
        this.marque = marque;
        this.matricule = matricule;
        this.model = model;
        this.idClient = idClient;
        this.client = client;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getIdClient() { // Update getter name to 'getIdClient'
        return idClient;
    }

    public void setIdClient(Long idClient) { // Update setter name to 'setIdClient'
        this.idClient = idClient;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
