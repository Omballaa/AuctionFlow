package fr.eni.auctionflow.service;

import java.util.List;
import fr.eni.auctionflow.model.Article;

public interface ArticleService {
    void supprimerArticle(Long noCategorie);
    List<Article> getAllArticles();
    Article ajouterArticle(Article article);
    List<Article> getArticlesByCategorie(Long noCategorie);
}
