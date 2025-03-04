package fr.eni.auctionflow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Enchere;
import org.springframework.stereotype.Repository;

@Repository
public interface EnchereDao extends JpaRepository<Enchere, Long> {

	public List<Enchere> findByArticleNoArticleAndArticleUtilisateurNoUtilisateurOrderByDateEnchereDesc(long noArticle, long ownerId);
}