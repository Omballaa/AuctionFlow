package com.eni.encheres.service;

import java.util.List;
import com.eni.encheres.model.Article;

public interface ArticleService {
    void supprimerArticle(Long id);
    List<Article> getAllArticles();
    Article ajouterArticle(Article article);
}
