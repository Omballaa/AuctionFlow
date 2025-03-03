package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.UtilisateurService;
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
