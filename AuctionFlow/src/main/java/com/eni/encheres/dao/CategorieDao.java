package com.eni.encheres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eni.encheres.model.Categorie;

public interface CategorieDao extends JpaRepository<Categorie, Long> {
}