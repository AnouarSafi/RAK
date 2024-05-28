package com.example.myapplication.model;
import com.example.myapplication.model.Client;
import com.example.myapplication.model.Facture;

import java.util.Date;
public class Paiement {
    private int id;
    private Client client;
    private Facture facture;
    private float montant;
    private Date date;

    public Paiement() {
    }
    public Paiement(int id, Client client, Facture facture, float montant, Date date) {
        this.id = id;
        this.client = client;
        this.facture = facture;
        this.montant = montant;
        this.date = date;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Facture getFacture() {
        return facture;
    }
    public void setFacture(Facture facture) {
        this.facture = facture;
    }
    public float getMontant() {
        return montant;
    }
    public void setMontant(float montant) {
        this.montant = montant;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}