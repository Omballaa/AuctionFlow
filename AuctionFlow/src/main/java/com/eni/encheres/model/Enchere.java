package com.eni.encheres.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ENCHERES")
public class Enchere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noEnchere;
    
    private Date dateEnchere;
    private Integer montantEnchere;
    
    @ManyToOne
    @JoinColumn(name = "no_article")
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "no_utilisateur")
    private Utilisateur utilisateur;
    
        // Getters et Setters
        public Long getNoEnchere() { return noEnchere; }
        public void setNoEnchere(Long noEnchere) { this.noEnchere = noEnchere; }
        public Date getDateEnchere() { return dateEnchere; }
        public void setDateEnchere(Date dateEnchere) { this.dateEnchere = dateEnchere; }
        public Integer getMontantEnchere() { return montantEnchere; }
        public void setMontantEnchere(Integer montantEnchere) { this.montantEnchere = montantEnchere; }
        public Article getArticle() { return article; }
        public void setArticle(Article article) { this.article = article; }
        public Utilisateur getUtilisateur() { return utilisateur; }
        public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
}
