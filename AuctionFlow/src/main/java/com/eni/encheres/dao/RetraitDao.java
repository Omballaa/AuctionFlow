package com.eni.encheres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eni.encheres.model.Retrait;

public interface RetraitDao extends JpaRepository<Retrait, Long> {
}
