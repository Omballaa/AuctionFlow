package fr.eni.auctionflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Categorie;
import org.springframework.stereotype.Repository;


public interface CategorieDao extends JpaRepository<Categorie, Long> {
}