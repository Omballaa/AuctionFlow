package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.dto.UtilisateurInscriptionDTO;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/login")
    public String connexionGet() {
        return "login"; // Affiche la page login.html
    }

    @GetMapping("/register")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new UtilisateurInscriptionDTO());
        return "register";
    }

    @PostMapping("/register")
    public String traiterFormulaireInscription(@ModelAttribute("utilisateur") UtilisateurInscriptionDTO utilisateurDTO,
                                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "register";

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
            return "register";
        }

        return "redirect:/login"; // Redirige vers la page de connexion
    }
}
