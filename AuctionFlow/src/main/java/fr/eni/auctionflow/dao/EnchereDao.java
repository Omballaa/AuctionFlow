package fr.eni.auctionflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Enchere;
import org.springframework.stereotype.Repository;


public interface EnchereDao extends JpaRepository<Enchere, Long> {
}