package com.eni.encheres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.eni.encheres.dao.ArticleDao;
import com.eni.encheres.model.Article;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public void supprimerArticle(Long id) {
        articleDao.deleteById(id);
    }
    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> getAllArticles() {
        return articleDao.findAll();
    }

    @Override
    public Article ajouterArticle(Article article) {
        return articleDao.save(article);
    }
}
