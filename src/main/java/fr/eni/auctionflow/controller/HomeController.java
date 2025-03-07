package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.model.Categorie;
import fr.eni.auctionflow.service.ArticleService;
import fr.eni.auctionflow.service.CategorieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategorieService categorieService;

    @GetMapping("/")
    public String afficherAccueil(
        HttpSession session, 
        Model model,
        @RequestParam(name = "nomArticle", required = false) String nomArticle,
        @RequestParam(name = "categorieId", required = false) Long categorieId) 
        {
            // Vérifier si l'utilisateur est authentifié
            boolean isLoggedIn = isUserAuthenticated();

            // Ajouter les articles en cours dans le modèle
            List<Article> encheresEnCours = articleService.getEnchereEnCours(nomArticle, categorieId);
            model.addAttribute("encheres", encheresEnCours);

            // Ajouter la variable isLoggedIn pour conditionner l'affichage sur la page
            model.addAttribute("isLoggedIn", isLoggedIn);
            
            //Ajout des catégories pour l'affichage du filtre
            List<Categorie> categories = categorieService.getAllCategories();
            model.addAttribute("categories", categories);

            return "accueil"; // Retourne la vue d'accueil
        }


    // Méthode pour vérifier si l'utilisateur est authentifié
    private boolean isUserAuthenticated() 
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("DEBUG: Authentication = " + SecurityContextHolder.getContext().getAuthentication());
            return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        }

    @GetMapping("/rechercher")
    @ResponseBody
    public List<Article> rechercherArticles(
    @RequestParam(name = "nomArticle", required = false) String nomArticle,
    @RequestParam(name = "categorieId", required = false) Long categorieId) 
        {
            return articleService.getEnchereEnCours(nomArticle, categorieId);
        }
  

}
