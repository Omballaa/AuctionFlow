package fr.eni.auctionflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import jakarta.transaction.Transactional;

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
    public void supprimerUtilisateur(Long noUtilisateur) {
        utilisateurDao.deleteById(noUtilisateur);
    }

	@Override
	@Transactional
	public Utilisateur inscription(Utilisateur utilisateur) throws BusinessException {
		
		// Erreur si pseudo pas unique
		if(   this.utilisateurDao.findByPseudo(utilisateur.getPseudo())!=null   ) {
			throw new BusinessException("Ce pseudo existe déjà");
		}
		
		// ainsi que l’email. 
		if ( this.utilisateurDao.findByEmail(utilisateur.getEmail())!=null) {
			throw new BusinessException("Cet email est déjà pris");
		}
		
		// Le pseudo n’accepte que des caractères alphanumériques (CHATGPT)
		if (!utilisateur.getPseudo().matches("^[a-zA-Z0-9]+$")) {
		    throw new BusinessException("Le pseudo ne doit contenir que des lettres et chiffres.");
		}

		
		//Un crédit initial de 100 points est alloué à la création du compte.
		utilisateur.setCredit(100);
		
		// Ajout util en BD
		return utilisateurDao.save(utilisateur);
	}

	@Override
	public Utilisateur rechercherParPseudoOuEmailEtMotDePasse(String pseudo, String email, String motDePasse) {
		
		return utilisateurDao.findByPseudoOrEmailAndMotDePasse(pseudo, email, motDePasse);
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



}
