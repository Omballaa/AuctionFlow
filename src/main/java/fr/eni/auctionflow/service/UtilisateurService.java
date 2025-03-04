package fr.eni.auctionflow.service;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    // Récupérer tous les utilisateurs
    List<Utilisateur> getAllUtilisateurs();

    // Récupérer un utilisateur par pseudo
    Optional<Utilisateur> getUtilisateurByPseudo(String pseudo);

    // Récupérer un utilisateur par email
    Optional<Utilisateur> getUtilisateurByEmail(String email);

    // Supprimer un utilisateur par son identifiant
    void supprimerUtilisateur(Long noUtilisateur);

    // Inscription d'un nouvel utilisateur
    Utilisateur inscription(Utilisateur utilisateur) throws BusinessException;

    // Recherche d'un utilisateur par pseudo/email et mot de passe
    Optional<Utilisateur> rechercherParPseudoOuEmailEtMotDePasse(String identifiant, String motDePasse);
}
