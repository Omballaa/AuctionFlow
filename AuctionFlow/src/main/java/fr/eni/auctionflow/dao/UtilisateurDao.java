package fr.eni.auctionflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Utilisateur;


@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
}
