package fr.eni.auctionflow.dto;

public class UtilisateurConnexionDTO {

    private String login;
    private String motDePasse ;

    public String getLogin() {
        return login;
    }

    public void setLogin(String pseudo) {
        this.login = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
