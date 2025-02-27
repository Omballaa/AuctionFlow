package fr.eni.auctionflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {
    List<Article> findByCategorie_noCategorie(Long noCategorie);

    @Query ("SELECT a FROM Article a WHERE a.dateFinEncheres > CURRENT_TIMESTAMP ")
    List<Article> findEncheresEnCours();

    List<Article> findByNomArticle(String nomArticle);

    // Nouvelle méthode pour filtrer par nom et catégorie
    List<Article> findByNomArticleContainingIgnoreCaseAndCategorie_noCategorie(String nomArticle, Long noCategorie);

}
