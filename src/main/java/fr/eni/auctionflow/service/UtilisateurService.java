package fr.eni.auctionflow.service;

import java.util.List;
import fr.eni.auctionflow.model.Utilisateur;

public interface UtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurByPseudo(String pseudo);
    Utilisateur getUtilisateurByEmail(String email);
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);
    void supprimerUtilisateur(Long id);
}
