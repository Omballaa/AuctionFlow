package fr.eni.auctionflow.service;


import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        // Vérifie si l'utilisateur existe dans la base de données
        Utilisateur utilisateur = utilisateurDao.findByPseudo(pseudo);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le pseudo : " + pseudo);
        }
        // Retourner un objet UserDetails basé sur l'utilisateur trouvé
        return new org.springframework.security.core.userdetails.User(
                utilisateur.getPseudo(),
                utilisateur.getMotDePasse(),
                Collections.emptyList() // Ajoute des rôles ou permissions si nécessaire
        );
    }
}
