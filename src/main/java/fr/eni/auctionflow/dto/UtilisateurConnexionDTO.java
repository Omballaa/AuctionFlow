package fr.eni.auctionflow.dto;

public class UtilisateurConnexionDTO {

    private String pseudoOuEmail ;

    private String motDePasse ;

    public String getPseudoOuEmail() {
        return pseudoOuEmail;
    }

    public void setPseudoOuEmail(String pseudoOuEmail) {
        this.pseudoOuEmail = pseudoOuEmail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}