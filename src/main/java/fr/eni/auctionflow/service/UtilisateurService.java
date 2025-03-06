package fr.eni.auctionflow.service;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import java.util.Optional;

public interface UtilisateurService {

    Utilisateur inscription(Utilisateur utilisateur) throws BusinessException;

}
