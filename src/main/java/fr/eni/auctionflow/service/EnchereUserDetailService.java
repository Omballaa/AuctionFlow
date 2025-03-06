package fr.eni.auctionflow.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.security.core.userdetails.User;

import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.model.Utilisateur;

public class EnchereUserDetailService implements UserDetailsService{
    @Autowired
    private UtilisateurDao userDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Utilisateur user = userDao.findByEmail(username);
        
        UserBuilder userBuilder = User.builder();

        UserDetails userDetail = User.builder()
                .username(user.getEmail()) // L'email est utilisé comme identifiant
                .password(user.getMotDePasse()) // Le mot de passe doit être encodé
                .build();

        return userDetail;
    }
}
