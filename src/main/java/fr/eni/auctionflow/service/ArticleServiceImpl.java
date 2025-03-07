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
    if (nomArticle == null) {
        nomArticle = ""; // Assure qu'on ne filtre pas sur NULL
    }

    if (categorieId != null) {
        return articleDao.findByNomArticleContainingIgnoreCaseAndCategorie_noCategorie(nomArticle, categorieId);
    } else {
        return articleDao.findByNomArticleContainingIgnoreCase(nomArticle);
    }
}

	@Override
	public List<Article> getMesVentes(long utilId) {
		
		return articleDao.findByUtilisateurNoUtilisateur(utilId);
	}

	@Override
	public Article getArticleParNoArticle(long noArticle) {
		return articleDao.findById(noArticle).orElse(null);
	}


}


