package fr.eni.auctionflow.service;

import fr.eni.auctionflow.dao.ArticleDao;
import fr.eni.auctionflow.dao.EnchereDao;
import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.model.Enchere;
import fr.eni.auctionflow.model.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {


    @Autowired
    private EnchereDao enchereDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private ArticleDao articleDao;
    
    /* 
     * ENCHERIR
     */
    @Override
    public void encherir(Long noArticle, Long userID, Integer montant) throws Exception {
  //vérif si l'article existe
        Article article = articleDao.findById(noArticle).orElse(null);
        if (article == null) {
            throw new BusinessException("Article introuvable.");
        }
  //vérif si l'util existe
        Utilisateur utilisateur = utilisateurDao.findById(userID).orElse(null);
        if (utilisateur == null) {
            throw new BusinessException("Utilisateur introuvable.");
        }
  //vérif que l'util n'est pas le vendeur de l'article
        if (article.getUtilisateur().getNoUtilisateur().equals(userID)) {
            throw new BusinessException("Vous ne pouvez pas enchérir sur votre propre article.");
        }
  //vérif si l'enchère est > à l'offre actuelle
        Enchere meilleureEnchere = enchereDao.findTopByArticleNoArticleOrderByMontantEnchereDesc(noArticle);
        if (meilleureEnchere != null) {
            if (montant <= meilleureEnchere.getMontantEnchere()) {
                throw new BusinessException("Votre enchère doit être supérieure à l'offre actuelle.");
            }
        }
 //vérif si l'util a assez de crédits pour enchérir
        if (utilisateur.getCredit() < montant) {
            throw new BusinessException("Vous n'avez pas assez de crédits pour cette enchère.");
        }
  //rembourser l'ancien meilleur enchérisseur (si existant)
        if (meilleureEnchere != null) {
            Utilisateur ancienMeilleur = meilleureEnchere.getUtilisateur();
            ancienMeilleur.setCredit(ancienMeilleur.getCredit() + meilleureEnchere.getMontantEnchere());
            utilisateurDao.save(ancienMeilleur);
        }
 //débiter nouvel enchérisseur
        utilisateur.setCredit(utilisateur.getCredit() - montant);
        utilisateurDao.save(utilisateur);
 //enregistrer nouvelle enchère
        Enchere nouvelleEnchere = new Enchere();
        nouvelleEnchere.setArticle(article);
        nouvelleEnchere.setUtilisateur(utilisateur);
        nouvelleEnchere.setMontantEnchere(montant);
        nouvelleEnchere.setDateEnchere(new Date());

        enchereDao.save(nouvelleEnchere);
    }

    /* 
     * REMPORTER UNE VENTE
     */
    @Override
    public Utilisateur determinerGagnant(Long noArticle) { 
  //vérif si article existe
        Article article = articleDao.findBynoArticle(noArticle).orElse(null);
        if (article == null) {
        	return null;
        }
  //trouver enchère la plus haute
        Enchere meilleureEnchere = enchereDao.findTopByArticleNoArticleOrderByMontantEnchereDesc(noArticle);
        if (meilleureEnchere == null) {
        	return null;
        }
  //return l'util qui a fait la meilleure enchère
        return meilleureEnchere.getUtilisateur();
    }

    
    @Scheduled(fixedRate = 60000)// Lance ttes les minutes
    public void cloturerVentesExpirees() {
    	
    	List<Article> articlesNonClotures = articleDao.findByVenteCloturee(false);
    	
    	for(Article article : articlesNonClotures) {
            cloturerVente(article.getNoArticle()); 
    	}
    }
    
    @Override
    public void cloturerVente(Long noArticle) {
  //vérif si article existe
        Article article = articleDao.findById(noArticle).orElse(null);
        if (article == null) {
            return;
        }
        
        // Vérir si la date de cloture est dépassée
        if( article.getDateFinEncheres().before( new Date() ) ) {
        	return;
        }
        
        // La vente doit être cloturée
        
        //trouver gagnant de l'enchère
        Utilisateur gagnant = determinerGagnant(noArticle);
        if (gagnant != null) {
            //marquer l'article comme vendu en mettant à jour l'util acquéreur
            article.setUtilisateur(gagnant);
        }
        
        // Cloture la vente
        article.setVenteCloturee(true);
        articleDao.save(article);
    }

    
    
    
    
    
    
    @Override
    public Enchere ajouterEnchere(Enchere enchere) {
        enchere.setDateEnchere(new Date());
        return enchereDao.save(enchere);
    }

	@Override
	public List<Enchere> listerEnchereArticleAppartenantAUtil(long noArticle, long userID) {
		return enchereDao.findByArticleNoArticleAndArticleUtilisateurNoUtilisateurOrderByDateEnchereDesc(noArticle, userID);
	}

	@Override
	public Enchere getMeilleureEnchere(long noArticle) {
		return enchereDao.findTopByArticleNoArticleOrderByMontantEnchereDesc(noArticle);
	}
	
	
}
