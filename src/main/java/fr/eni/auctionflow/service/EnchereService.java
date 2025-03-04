package fr.eni.auctionflow.service;

import java.util.List;

import fr.eni.auctionflow.model.Enchere;

public interface EnchereService {
	List<Enchere> listerEnchereArticleAppartenantAUtil(long noArticle, long userID);
    Enchere ajouterEnchere(Enchere enchere);
}
