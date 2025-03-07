package fr.eni.auctionflow.service;

import fr.eni.auctionflow.dao.CategorieDao;
import fr.eni.auctionflow.model.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieDao categorieDao;

    @Override
    public List<Categorie> getAllCategories() {
        return categorieDao.findAll();
    }
}
