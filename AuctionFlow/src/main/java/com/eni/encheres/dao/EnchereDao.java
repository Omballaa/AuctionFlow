package com.eni.encheres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eni.encheres.model.Enchere;

public interface EnchereDao extends JpaRepository<Enchere, Long> {
}