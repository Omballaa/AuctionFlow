package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.dto.UtilisateurConnexionDTO;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    
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
        return "connexion"; 
    }
    
    @PostMapping("/connexion")
    public String connexionPost(
            @ModelAttribute("dto") UtilisateurConnexionDTO dto,
            BindingResult bindingResult, HttpSession session) 
    {
    	//verifier si utilisateur existe en bdd
    	Utilisateur utilisateur = utilisateurService.rechercherParPseudoOuEmailEtMotDePasse(dto.getPseudoOuEmail(), dto.getPseudoOuEmail(), dto.getMotDePasse()); 
    	
    	//renvoyer vers page d'erreur si connexion echouée 
    	if (utilisateur == null) {
    		return "erreur";
    	}
    	
    	//mettre utilisateur en session
    	session.setAttribute("userID", utilisateur.getNoUtilisateur());
    	session.setAttribute("pseudoUtilisateur", utilisateur.getPseudo());
    	return "redirect://";
    }
    
    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) {
    	session.invalidate();
    	return "redirect://";
    }
       
    //afficher le formulaire d'inscription
    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscription"; 
    }

    //traitement du formulaire d'inscription
    @PostMapping("/inscription")
    public String traiterFormulaireInscription(
            @ModelAttribute("utilisateur") Utilisateur utilisateur,
            BindingResult bindingResult, Model model) {

        // Si erreurs, on retourne au formulaire
        if (bindingResult.hasErrors()) {
            return "inscription";
        }

        try {
            utilisateurService.inscription(utilisateur);
        } catch (BusinessException e) {
            model.addAttribute("erreur", e.getMessage());
            return "inscription";
        }

        //redirige vers page d'accueil une fois l'inscription réussie
        return "redirect:/";
    }
}
