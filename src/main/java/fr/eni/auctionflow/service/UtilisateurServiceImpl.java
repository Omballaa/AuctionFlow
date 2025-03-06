package fr.eni.auctionflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;


@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurDao utilisateurDao;

	@Autowired
	private PasswordEncoder passwordEncoder; // Pour hacher les mots de passe

	@Override
	public Optional<Utilisateur> getUtilisateurByPseudo(String pseudo) {
		return Optional.ofNullable(utilisateurDao.findByPseudo(pseudo));
	}

	@Override
	public void supprimerUtilisateur(Long noUtilisateur) throws BusinessException {
		if (!utilisateurDao.existsById(noUtilisateur)) {
			throw new BusinessException("L'utilisateur n'existe pas.");
		}
		utilisateurDao.deleteById(noUtilisateur);
	}

	@Override
	public Optional<Utilisateur> getUtilisateurByEmail(String email) {
		return Optional.ofNullable(utilisateurDao.findByEmail( email));
	}


	@Override
	@Transactional
	public Utilisateur inscription(Utilisateur utilisateur) throws BusinessException {
		// Vérifier si le pseudo est unique
		if (utilisateurDao.findByPseudo(utilisateur.getPseudo()) != null) {
			throw new BusinessException("Ce pseudo existe déjà.");
		}

		// Vérifier si l’email est unique
		if (utilisateurDao.findByEmail(utilisateur.getEmail()) != null) {
			throw new BusinessException("Cet email est déjà pris.");
		}

		// Vérifier si le pseudo est valide (alphanumérique uniquement)
		if (!utilisateur.getPseudo().matches("^[a-zA-Z0-9]+$")) {
			throw new BusinessException("Le pseudo ne doit contenir que des lettres et chiffres.");
		}

		// Initialiser le crédit à 100 points
		utilisateur.setCredit(100);

		// 🔒 Hachage du mot de passe avant stockage en base
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

		// Sauvegarde en base
		Utilisateur returnUtilisateur = utilisateurDao.save(utilisateur);
		return returnUtilisateur;
	}

	@Override
	public Utilisateur rechercherParPseudoOuEmailEtMotDePasse(String pseudo, String email, String motDePasse) {
		Utilisateur user = utilisateurDao.findByPseudoOrEmail(pseudo, email);
		
		return user;
	}

	@Override
	public Utilisateur rechercherParID(long id) {

		return utilisateurDao.findById(id).orElse(null);
	}

	@Override
	public void save(Utilisateur utilisateur) {
	    utilisateurDao.save(utilisateur);
	}

	@Override
	public boolean estAdministrateur(Long userID) {
	    if (userID == null) { //check si util est connecté
	        return false;
	    }
	    Utilisateur utilisateur = utilisateurDao.findById(userID).orElse(null); //cherche util en bdd
	    if (utilisateur == null) { //check si util existe et est admin
	        return false;
	    }
	    return utilisateur.isAdministrateur();
	}



	public Utilisateur connexion(String identifiant, String email, String motDePasse) throws BusinessException {
		// 🔍 Recherche de l'utilisateur en base par pseudo ou email
		Utilisateur utilisateur = utilisateurDao.findByPseudoOrEmail(identifiant, email);

		// Vérification du mot de passe haché avec BCrypt
		if (utilisateur == null || !passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
			throw new BusinessException("Identifiants incorrects.");
		}

		return utilisateur;
	}

	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		return utilisateurDao.findAll();
	}
}
