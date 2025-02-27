package com.eni.encheres.service;

import java.util.List;
import com.eni.encheres.model.Utilisateur;

public interface UtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurByPseudo(String pseudo);
    Utilisateur getUtilisateurByEmail(String email);
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);
    void supprimerUtilisateur(Long id);
}
