package com.eni.encheres.service;

import java.util.List;
import com.eni.encheres.model.Article;

public interface ArticleService {
    void supprimerArticle(Long noCategorie);
    List<Article> getAllArticles();
    Article ajouterArticle(Article article);
    List<Article> getArticlesByCategorie(Long noCategorie);
}
