package fr.eni.auctionflow.service;

import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private final UtilisateurDao utilisateurDao;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, PasswordEncoder passwordEncoder) {
		this.utilisateurDao = utilisateurDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		return utilisateurDao.findAll();
	}

	@Override
	public Optional<Utilisateur> getUtilisateurByPseudo(String pseudo) {
		return Optional.ofNullable(utilisateurDao.findByPseudo(pseudo));
	}

	@Override
	public Optional<Utilisateur> getUtilisateurByEmail(String email) {
		return Optional.ofNullable(utilisateurDao.findByEmail(email));
	}

	@Override
	public void supprimerUtilisateur(Long noUtilisateur) {
		if (!utilisateurDao.existsById(noUtilisateur)) {
			throw new BusinessException("L'utilisateur avec l'ID " + noUtilisateur + " n'existe pas.");
		}
		utilisateurDao.deleteById(noUtilisateur);
	}

	@Override
	@Transactional
	public Utilisateur inscription(Utilisateur utilisateur) throws BusinessException {

		// Vérifier si le pseudo est unique
		if (utilisateurDao.findByPseudo(utilisateur.getPseudo()) != null) {
			throw new BusinessException("Ce pseudo est déjà utilisé. Veuillez en choisir un autre.");
		}

		// Vérifier si l’email est unique
		if (utilisateurDao.findByEmail(utilisateur.getEmail()) != null) {
			throw new BusinessException("Cet email est déjà associé à un compte existant.");
		}

		// Vérifier si le pseudo est valide (alphanumérique uniquement)
		if (!utilisateur.getPseudo().matches("^[a-zA-Z0-9]+$")) {
			throw new BusinessException("Le pseudo ne doit contenir que des lettres et chiffres (sans espace ni caractères spéciaux).");
		}

		// Initialiser le crédit à 100 points
		utilisateur.setCredit(100);

		// 🔒 Hachage du mot de passe avant stockage en base
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

		// Sauvegarde en base
		return utilisateurDao.save(utilisateur);
	}

	@Override
	public Optional<Utilisateur> rechercherParPseudoOuEmailEtMotDePasse(String identifiant, String motDePasse) {
		// 🔍 Recherche de l'utilisateur en base par pseudo ou email
		Utilisateur utilisateur = utilisateurDao.findByPseudoOrEmail(identifiant, identifiant);

		// Vérification du mot de passe haché avec BCrypt
		if (utilisateur != null && passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
			return Optional.of(utilisateur);
		}
		return Optional.empty(); // Mauvais mot de passe ou utilisateur inexistant
	}
}
