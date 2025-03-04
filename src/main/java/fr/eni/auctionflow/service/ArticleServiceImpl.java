package fr.eni.auctionflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import fr.eni.auctionflow.dao.ArticleDao;
import fr.eni.auctionflow.model.Article;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public void supprimerArticle(Long noCategorie) {
        articleDao.deleteById(noCategorie);
    }
    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> getAllArticles() {
        return articleDao.findAll();
    }

    @Override
    public Article ajouterArticle(Article article) {
        return articleDao.save(article);
    }

    @Override
    public List<Article> getArticlesByCategorie(Long noCategorie) {
        return articleDao.findByCategorie_noCategorie(noCategorie);
    }

    @Override
    public List<Article> getEnchereEnCours(String nomArticle, Long categorieId) {
        if (nomArticle != null && categorieId != null) {
            return articleDao.findByNomArticleContainingIgnoreCaseAndCategorie_noCategorie(nomArticle, categorieId);
        }
        // Si uniquement le nom est spécifié
        else if (nomArticle != null) {
            return articleDao.findByNomArticle(nomArticle);
        }
        // Si uniquement la catégorie est spécifiée
        else if (categorieId != null) {
            return articleDao.findByCategorie_noCategorie(categorieId);
        }
        // Si aucun filtre n'est spécifié, on récupère toutes les enchères en cours
        return articleDao.findEncheresEnCours();
    }

	@Override
	public List<Article> getMesVentes(long utilId) {
		
		return articleDao.findByUtilisateurNoUtilisateur(utilId);
	}


}


