package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String afficherAccueil(HttpSession session, Model model,
                                  @RequestParam(required = false) String nomArticle,
                                  @RequestParam(required = false) Long categorieId) {

        // Vérifier si l'utilisateur est authentifié
        if (isUserAuthenticated()) {
            return "redirect:/dashboard"; // Redirige vers une autre page si l'utilisateur est connecté
        }

        // Ajouter les articles en cours dans le modèle
        List<Article> encheresEnCours = articleService.getEnchereEnCours(nomArticle, categorieId);
        model.addAttribute("encheres", encheresEnCours);
        model.addAttribute("isLoggedIn", false);

        return "home"; // Affiche la page d'accueil pour les utilisateurs non connectés
    }


    // Méthode pour vérifier si l'utilisateur est authentifié
    private boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
    }
}
