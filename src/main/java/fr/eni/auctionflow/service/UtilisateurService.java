package fr.eni.auctionflow.service;

import java.util.List;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;

public interface UtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurByPseudo(String pseudo);
    Utilisateur getUtilisateurByEmail(String email);

	Utilisateur inscription(Utilisateur utilisateur) throws BusinessException;
    void supprimerUtilisateur(Long id);
    Utilisateur rechercherParPseudoOuEmailEtMotDePasse (String pseudo, String email, String motDePasse);
}
