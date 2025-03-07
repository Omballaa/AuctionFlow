package fr.eni.auctionflow.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.model.Utilisateur;

@Service
public class EnchereUserDetailService implements UserDetailsService {

    @Autowired
    private UtilisateurDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Tentative de connexion avec le login : " + username);

        Utilisateur user = userDao.findByPseudoOrEmail(username.toLowerCase(), username.toLowerCase());

        if (user == null) {
            System.out.println("Aucun utilisateur trouvé avec l'email : " + username);
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        } else {
            System.out.println("Utilisateur trouvé : " + user.getEmail());
        }

        // Retourner une instance de CustomUserDetails avec l'utilisateur trouvé
        return new CustomUserDetails(user);
    }
}