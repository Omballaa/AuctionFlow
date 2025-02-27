package fr.eni.auctionflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {
    List<Article> findByCategorie_noCategorie(Long noCategorie);
}
