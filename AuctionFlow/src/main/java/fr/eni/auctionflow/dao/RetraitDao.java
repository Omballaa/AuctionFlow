package fr.eni.auctionflow.dao;

import jdk.jfr.Enabled;
import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.auctionflow.model.Retrait;
import org.springframework.stereotype.Repository;


public interface RetraitDao extends JpaRepository<Retrait, Long> {
}
