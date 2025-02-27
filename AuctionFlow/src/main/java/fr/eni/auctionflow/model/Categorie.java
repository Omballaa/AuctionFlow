package fr.eni.auctionflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noCategorie;
    
    private String libelle;
    
    // Getters et Setters
    public Long getNoCategorie() { return noCategorie; }
    public void setNoCategorie(Long noCategorie) { this.noCategorie = noCategorie; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
