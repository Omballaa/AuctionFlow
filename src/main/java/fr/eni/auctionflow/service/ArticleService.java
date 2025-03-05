package fr.eni.auctionflow.service;

import java.util.List;
import fr.eni.auctionflow.model.Article;

public interface ArticleService {
	List<Article> getMesVentes(long utilId);
    void supprimerArticle(Long noCategorie);
    List<Article> getAllArticles();
    Article ajouterArticle(Article article);
    List<Article> getArticlesByCategorie(Long noCategorie);
    List<Article> getEnchereEnCours(String nomArticle, Long categorieId);
	Article getArticleParNoArticle(long noArticle);
}
