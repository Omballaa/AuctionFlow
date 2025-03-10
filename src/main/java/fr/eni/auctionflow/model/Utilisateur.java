package fr.eni.auctionflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UTILISATEURS")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noUtilisateur;
    
    @Column(unique = true)
    private String pseudo;
    
    private String nom;
    private String prenom;
    
    @Column(unique = true)
    private String email;
    
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private String motDePasse;
    private int credit;
    private boolean administrateur;
    
    // Getters et Setters
    public Long getNoUtilisateur() { return noUtilisateur; }
    public void setNoUtilisateur(Long noUtilisateur) { this.noUtilisateur = noUtilisateur; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }
    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public boolean isAdministrateur() { return administrateur; }
    public void setAdministrateur(boolean administrateur) { this.administrateur = administrateur; }
}
