package fr.eni.auctionflow.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Retrait;
import org.springframework.stereotype.Repository;

@Repository
public interface RetraitDao extends JpaRepository<Retrait, Long> {
}
