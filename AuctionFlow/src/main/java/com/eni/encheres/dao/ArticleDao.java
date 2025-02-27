package com.eni.encheres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eni.encheres.model.Article;

public interface ArticleDao extends JpaRepository<Article, Long> {
}
