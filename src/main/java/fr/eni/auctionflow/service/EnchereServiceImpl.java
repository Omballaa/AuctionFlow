package fr.eni.auctionflow.service;

import fr.eni.auctionflow.dao.EnchereDao;
import fr.eni.auctionflow.model.Enchere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EnchereServiceImpl implements EnchereService {

    @Autowired
    private EnchereDao enchereDao;

    @Override
    public Enchere ajouterEnchere(Enchere enchere) {
        enchere.setDateEnchere(new Date());
        return enchereDao.save(enchere);
    }
}
