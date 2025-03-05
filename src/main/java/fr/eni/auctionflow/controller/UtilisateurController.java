package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.dto.UtilisateurConnexionDTO;
import fr.eni.auctionflow.dto.UtilisateurInscriptionDTO;
import fr.eni.auctionflow.exception.BusinessException;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/connexion")
    public String connexionGet(Model model) {
        model.addAttribute("dto", new UtilisateurConnexionDTO());
        return "utilisateurs/connexion";
    }

    @PostMapping("/connexion")
    public String connexionPost(@ModelAttribute("dto") UtilisateurConnexionDTO dto,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "utilisateurs/connexion";

        try {
            utilisateurService.connexion(dto.getPseudo(),dto.getEmail(), dto.getMotDePasse());
            return "redirect:/accueil";
        } catch (BusinessException e) {
            model.addAttribute("erreurConnexion", e.getMessage());
            return "utilisateurs/connexion";
        }
    }

    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) { 
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new UtilisateurInscriptionDTO());
        return "utilisateurs/inscription";
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
