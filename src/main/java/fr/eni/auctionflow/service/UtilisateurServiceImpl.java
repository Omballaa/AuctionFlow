package fr.eni.auctionflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.model.Utilisateur;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurDao utilisateurDao;

	@Autowired
	private PasswordEncoder passwordEncoder; // Ajout du hachage des mots de passe

	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		return utilisateurDao.findAll();
	}

<<<<<<< Updated upstream
    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        return utilisateurDao.save(utilisateur);
    }

    @Override
    public void supprimerUtilisateur(Long noUtilisateur) {
        utilisateurDao.deleteById(noUtilisateur);
    }
=======
	@Override
	public Utilisateur getUtilisateurByPseudo(String pseudo) {
		return utilisateurDao.findByPseudo(pseudo);
	}

	@Override
	public Utilisateur getUtilisateurByEmail(String email) {
		return utilisateurDao.findByEmail(email);
	}

	@Override
	public void supprimerUtilisateur(Long noUtilisateur) {
		utilisateurDao.deleteById(noUtilisateur);
	}

	@Override
	@Transactional
	public Utilisateur inscription(Utilisateur utilisateur) throws BusinessException {

		// Vérifier si le pseudo est unique
		if (utilisateurDao.findByPseudo(utilisateur.getPseudo()) != null) {
			throw new BusinessException("Ce pseudo existe déjà");
		}

		// Vérifier si l’email est unique
		if (utilisateurDao.findByEmail(utilisateur.getEmail()) != null) {
			throw new BusinessException("Cet email est déjà pris");
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
		return utilisateurDao.save(utilisateur);
	}

	@Override
	public Utilisateur rechercherParPseudoOuEmailEtMotDePasse(String pseudoOuEmail, String email, String motDePasse) {
		// 🔍 Recherche de l'utilisateur en base par pseudo ou email (sans le mot de passe)
		Utilisateur utilisateur = utilisateurDao.findByPseudoOrEmail(pseudoOuEmail, email);

		// Vérification du mot de passe haché avec BCrypt
		if (utilisateur != null && passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
			return utilisateur;
		} else {
			return null; // Mauvais mot de passe ou utilisateur inexistant
		}
	}

	@Override
	public Utilisateur rechercherParID(long id) {

		return utilisateurDao.findById(id).orElse(null);
	}

	@Override
	public void save(Utilisateur utilisateur) {
	    utilisateurDao.save(utilisateur);
	}

>>>>>>> Stashed changes
}
