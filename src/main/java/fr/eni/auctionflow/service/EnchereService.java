package fr.eni.auctionflow.service;

import java.util.List;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Enchere;
import fr.eni.auctionflow.model.Utilisateur;

public interface EnchereService {
	List<Enchere> listerEnchereArticleAppartenantAUtil(long noArticle, long userID);
    Enchere ajouterEnchere(Enchere enchere);
    void encherir(Long noArticle, Long userID, Integer montant) throws Exception;
    Utilisateur determinerGagnant(Long noArticle) throws BusinessException;
    void cloturerVente(Long noArticle) throws BusinessException;
	Enchere getMeilleureEnchere(long noArticle);

}
