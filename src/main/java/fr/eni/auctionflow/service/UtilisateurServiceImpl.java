package fr.eni.auctionflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurDao utilisateurDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Utilisateur inscription(Utilisateur utilisateur) throws BusinessException {
		// Vérifier si le pseudo est unique
		if (utilisateurDao.findByPseudo(utilisateur.getPseudo()) != null) {
			throw new BusinessException("Ce pseudo est déjà utilisé.");
		}

		// Vérifier si l’email est unique
		if (utilisateurDao.findByEmail(utilisateur.getEmail()) != null) {
			throw new BusinessException("Cet email est déjà enregistré.");
		}

		// Vérifier si le pseudo est valide (alphanumérique uniquement)
		if (!utilisateur.getPseudo().matches("^[a-zA-Z0-9]+$")) {
			throw new BusinessException("Le pseudo ne doit contenir que des lettres et des chiffres.");
		}

		// Initialiser le crédit par défaut
		utilisateur.setCredit(100);

		// 🔒 Hachage du mot de passe avant stockage en base
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

		// Sauvegarde en base
		utilisateur = utilisateurDao.save(utilisateur);

		// Log pour vérifier la sauvegarde
		System.out.println("Utilisateur sauvegardé : " + utilisateur);

		return utilisateur;
	}

}
