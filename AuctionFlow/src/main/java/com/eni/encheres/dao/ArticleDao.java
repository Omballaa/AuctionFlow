package com.eni.encheres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eni.encheres.model.Article;
import java.util.List;

public interface ArticleDao extends JpaRepository<Article, Long> {
    List<Article> findByCategorieId(Long categorieId);
}
