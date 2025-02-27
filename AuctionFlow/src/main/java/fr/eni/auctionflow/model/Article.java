package fr.eni.auctionflow.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ARTICLES_VENDUS")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noArticle;
    
    private String nomArticle;
    private String description;
    private Date dateDebutEncheres;
    private Date dateFinEncheres;
    private Integer prixInitial;
    private Integer prixVente;
    
    @ManyToOne
    @JoinColumn(name = "no_utilisateur")
    private Utilisateur utilisateur;
    
    @ManyToOne
    @JoinColumn(name = "no_categorie")
    private Categorie categorie;
    
    // Getters et Setters
    public Long getNoArticle() { return noArticle; }
    public void setNoArticle(Long noArticle) { this.noArticle = noArticle; }
    public String getNomArticle() { return nomArticle; }
    public void setNomArticle(String nomArticle) { this.nomArticle = nomArticle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDateDebutEncheres() { return dateDebutEncheres; }
    public void setDateDebutEncheres(Date dateDebutEncheres) { this.dateDebutEncheres = dateDebutEncheres; }
    public Date getDateFinEncheres() { return dateFinEncheres; }
    public void setDateFinEncheres(Date dateFinEncheres) { this.dateFinEncheres = dateFinEncheres; }
    public Integer getPrixInitial() { return prixInitial; }
    public void setPrixInitial(Integer prixInitial) { this.prixInitial = prixInitial; }
    public Integer getPrixVente() { return prixVente; }
    public void setPrixVente(Integer prixVente) { this.prixVente = prixVente; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
