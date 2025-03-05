package fr.eni.auctionflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.auctionflow.dto.UtilisateurConnexionDTO;
import fr.eni.auctionflow.dto.UtilisateurInscriptionDTO;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {
	private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);
	
    @Autowired
    private UtilisateurService utilisateurService;


    //en tant qu'util je peux supp mon compte
    @GetMapping("/supprimer-compte")
    public String supprimerCompteGet(HttpSession session) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/utilisateurs/connexion";
        }
        //utilisateurService.supprimerUtilisateur((Long) session.getAttribute("userID")); //supp util
        try {
			utilisateurService.supprimerUtilisateur((Long) session.getAttribute("userID"));
		} catch (BusinessException e) {
			e.printStackTrace();			
		}
        session.invalidate();//déco util
        return "redirect:/"; //page accueil
    }

    //en tant qu'admin, je peux supp des comptes util
    @GetMapping("/admin/supprimer-utilisateur/{id}")
    public String supprimerUtilisateurAdminGet(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("userID") == null || !utilisateurService.estAdministrateur((Long) session.getAttribute("userID"))) {
            return "redirect:/utilisateurs/connexion";
        }
        try {
			utilisateurService.supprimerUtilisateur(id);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//supp util ciblé
        return "redirect:/admin/liste-utilisateurs"; //redirect vers liste des util
    }

    
    //afficher le profil
    @GetMapping("/afficher-profil")
    public String afficherProfilGet(HttpSession session, Model model) {
    	 if (session.getAttribute("userID") == null) { //vérifie si util est connecté
    	     return "redirect:/utilisateurs/connexion"; //redirige vers connexion si non connecté
    	}
    	Utilisateur utilisateur = utilisateurService.rechercherParID((long) session.getAttribute("userID"));	
    	model.addAttribute("util", utilisateur);
    	return "profil";
    }
    
    //modifier le profil 
    //formulaire de modification profil
    @GetMapping("/modifier-profil")
    public String modifierProfilGet(HttpSession session, Model model) {
        if (session.getAttribute("userID") == null) {
            return "redirect:/utilisateurs/connexion";
        }
        Utilisateur utilisateur = utilisateurService.rechercherParID((Long) session.getAttribute("userID"));
        model.addAttribute("util", utilisateur);
        return "modifier-profil"; 
    }
    
    //traitement de la modification profil
    @PostMapping("/modifier-profil")
    public String modifierProfilPost(@ModelAttribute("util") Utilisateur utilisateur) {
        utilisateurService.save(utilisateur); //save les modif
        return "redirect:/utilisateurs/afficher-profil"; //redirige vers profil mis à jour
    }

    
    
    @GetMapping("/connexion")
    public String connexionGet(Model model) {
        model.addAttribute("dto", new UtilisateurConnexionDTO());
        return "utilisateurs/connexion";  
    }

    @PostMapping("/connexion")
    public String connexionPost(
            @ModelAttribute("dto") UtilisateurConnexionDTO dto,
            BindingResult bindingResult, HttpSession session) 
    {
    	//verifier si utilisateur existe en bdd
    	Utilisateur utilisateur = utilisateurService.rechercherParPseudoOuEmailEtMotDePasse(dto.getPseudo(), dto.getPseudo(), dto.getMotDePasse()); 
    	
    	//renvoyer vers page d'erreur si connexion echouée 
    	if (utilisateur == null) {
    		return "erreur";
    	}
    	
    	//mettre utilisateur en session
    	session.setAttribute("userID", utilisateur.getNoUtilisateur());
    	session.setAttribute("pseudoUtilisateur", utilisateur.getPseudo());
    	return "redirect:/";
    }

    
    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
    

       
    //afficher le formulaire d'inscription
    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "redirect:/utilisateurs/inscription"; 
    }

        
    @PostMapping("/inscription")
    public String traiterFormulaireInscription(@ModelAttribute("utilisateur") UtilisateurInscriptionDTO utilisateurDTO,
                                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "utilisateurs/inscription";

        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(utilisateurDTO.getNom());
            utilisateur.setPrenom(utilisateurDTO.getPrenom());
            utilisateur.setRue(utilisateurDTO.getAdresse());
            utilisateur.setVille(utilisateurDTO.getVille());
            utilisateur.setCodePostal(utilisateurDTO.getCodePostal());
            utilisateur.setEmail(utilisateurDTO.getEmail());
            utilisateur.setPseudo(utilisateurDTO.getPseudo());
            utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());

            utilisateurService.inscription(utilisateur);
        } catch (BusinessException e) {
            model.addAttribute("erreur", e.getMessage());
            return "utilisateurs/inscription";
        }

        return "redirect:/utilisateurs/connexion";
    }



}
