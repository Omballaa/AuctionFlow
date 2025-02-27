package fr.eni.auctionflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RETRAITS")
public class Retrait {
    @Id
    private Long noArticle;
    
    private String rue;
    private String codePostal;
    private String ville;
    
    @OneToOne
    @JoinColumn(name = "no_article")
    private Article article;
    
    // Getters et Setters
    public Long getNoArticle() { return noArticle; }
    public void setNoArticle(Long noArticle) { this.noArticle = noArticle; }
    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
}
