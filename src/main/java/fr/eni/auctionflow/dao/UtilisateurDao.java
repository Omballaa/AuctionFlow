package fr.eni.auctionflow.dao;

import fr.eni.auctionflow.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
	//Utilisateur findByPseudoOrEmailAndMotDePasse(String pseudo, String email, String motDepasse);
    Utilisateur findByPseudoOrEmail(String identifiant, String email);
    Utilisateur findByEmail(String email);
    Utilisateur findByPseudo(String pseudo);
    Utilisateur findBynoUtilisateur(Long noUtilisateur);
    boolean existsByEmail(String email);
    boolean existsByPseudo(String pseudo);
}
