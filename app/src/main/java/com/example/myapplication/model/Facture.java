package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Facture implements Serializable {
    private int id;
    private float montant;
    private String date;
    private String gerance;
    private Client client;
    private List<Paiement> paiements;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGerance() {
        return gerance;
    }

    public void setGerance(String gerance) {
        this.gerance = gerance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }
}