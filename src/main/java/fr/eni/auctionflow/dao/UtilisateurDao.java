package fr.eni.auctionflow.dao;

import fr.eni.auctionflow.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
    Utilisateur findByPseudoOrEmail(String pseudo, String email);
}
