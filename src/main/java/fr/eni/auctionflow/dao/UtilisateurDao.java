package fr.eni.auctionflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Utilisateur;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
	Utilisateur findByPseudoOrEmailAndMotDePasse(String pseudo, String email, String motDepasse);
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findByEmail(String email);
}
