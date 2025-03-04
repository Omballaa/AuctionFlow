package fr.eni.auctionflow.service;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import java.util.Optional;

public interface UtilisateurService {
    Optional<Utilisateur> getUtilisateurByPseudo(String pseudo);
    Optional<Utilisateur> getUtilisateurByEmail(String email);
    void supprimerUtilisateur(Long noUtilisateur) throws BusinessException;
    Utilisateur inscription(Utilisateur utilisateur) throws BusinessException;
    Utilisateur connexion(String identifiant, String email, String motDePasse) throws BusinessException;
}
