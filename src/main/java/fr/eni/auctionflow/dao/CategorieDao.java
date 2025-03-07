package fr.eni.auctionflow.dao;

import java.util.List;

import fr.eni.auctionflow.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieDao extends JpaRepository<Categorie, Long> {
    List<Categorie> findAll();
}
