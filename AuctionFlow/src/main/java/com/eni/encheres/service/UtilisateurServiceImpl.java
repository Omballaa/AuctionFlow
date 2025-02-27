package com.eni.encheres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.eni.encheres.dao.UtilisateurDao;
import com.eni.encheres.model.Utilisateur;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    private UtilisateurDao utilisateurDao;

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDao.findAll();
    }

    @Override
    public Utilisateur getUtilisateurByPseudo(String pseudo) {
        return utilisateurDao.findByPseudo(pseudo);
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurDao.findByEmail(email);
    }

    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        return utilisateurDao.save(utilisateur);
    }

    @Override
    public void supprimerUtilisateur(Long id) {
        utilisateurDao.deleteById(id);
    }
}
