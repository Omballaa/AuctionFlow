package com.eni.encheres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eni.encheres.model.Utilisateur;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
}
