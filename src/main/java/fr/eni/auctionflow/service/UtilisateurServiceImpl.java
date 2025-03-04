package fr.eni.auctionflow.service;

import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

	private final UtilisateurDao utilisateurDao;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, PasswordEncoder passwordEncoder) {
		this.utilisateurDao = utilisateurDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<Utilisateur> getUtilisateurByPseudo(String pseudo) {
		return Optional.ofNullable(utilisateurDao.findByPseudoOrEmail(pseudo, pseudo));
	}

	@Override
	public Optional<Utilisateur> getUtilisateurByEmail(String email) {
		return Optional.ofNullable(utilisateurDao.findByPseudoOrEmail(email, email));
	}

	@Override
	@Transactional
	public Utilisateur inscription(Utilisateur utilisateur) throws BusinessException {
		if (utilisateurDao.existsByPseudo(utilisateur.getPseudo()) || utilisateurDao.existsByEmail(utilisateur.getEmail())) {
			throw new BusinessException("Ce pseudo ou email est déjà utilisé.");
		}

		if (!utilisateur.getPseudo().matches("^[a-zA-Z0-9]+$")) {
			throw new BusinessException("Le pseudo ne doit contenir que des lettres et chiffres.");
		}

		utilisateur.setCredit(100);
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		return utilisateurDao.save(utilisateur);
	}

	@Override
	public Utilisateur connexion(String identifiant, String email, String motDePasse) throws BusinessException {
		Utilisateur utilisateur = utilisateurDao.findByPseudoOrEmail(identifiant, email);
		if (utilisateur == null || !passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
			throw new BusinessException("Pseudo ou mot de passe incorrect.");
		}
		return utilisateur;
	}

	@Override
	public void supprimerUtilisateur(Long noUtilisateur) throws BusinessException {
		if (!utilisateurDao.existsById(noUtilisateur)) {
			throw new BusinessException("L'utilisateur n'existe pas.");
		}
		utilisateurDao.deleteById(noUtilisateur);
	}
}
