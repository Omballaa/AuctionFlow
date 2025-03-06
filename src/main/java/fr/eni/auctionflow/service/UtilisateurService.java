package fr.eni.auctionflow.service;

import java.util.List;
import java.util.Optional;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;

public interface UtilisateurService {
    List<Utilisateur> getAllUtilisateurs();

    Utilisateur rechercherParPseudoOuEmailEtMotDePasse (String pseudo, String email, String motDePasse);
	Utilisateur rechercherParID(long l);
	void save(Utilisateur utilisateur);
	boolean estAdministrateur(Long attribute);
    Optional<Utilisateur> getUtilisateurByPseudo(String pseudo);
    Optional<Utilisateur> getUtilisateurByEmail(String email);
    void supprimerUtilisateur(Long noUtilisateur) throws BusinessException;
    Utilisateur inscription(Utilisateur utilisateur) throws BusinessException;
    Utilisateur connexion(String identifiant, String email, String motDePasse) throws BusinessException;
}
