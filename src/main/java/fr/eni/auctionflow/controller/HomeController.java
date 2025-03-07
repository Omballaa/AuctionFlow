package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.model.Categorie;
import fr.eni.auctionflow.model.Utilisateur;
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

    // Méthode pour vérifier si l'utilisateur est authentifié
    private boolean isUserAuthenticated() 
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authentication = " + SecurityContextHolder.getContext().getAuthentication());
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
    }
    
    private Utilisateur getUserAuthenticated() 
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            // return UtilisateurDao.findByPseudoOrEmail(authentication.getName(), authentication.getName()); // Retourne le nom d'utilisateur
            return null;
        }
        return null; // Aucun utilisateur authentifié
    }


    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategorieService categorieService;

    //Test pour afficher un message sur la page d'accueil
    @GetMapping("/message")
    public String affichermessageTest(
        HttpSession session, 
        Model model,
        @RequestParam(name = "nomArticle", required = false) String nomArticle,
        @RequestParam(name = "categorieId", required = false) Long categorieId) 
        {
            // Vérifier si l'utilisateur est authentifié
            boolean isLoggedIn = isUserAuthenticated();
            model.addAttribute("isLoggedIn", isLoggedIn);

            // Ajouter les articles en cours dans le modèle
            List<Article> encheresEnCours = articleService.getEnchereEnCours(nomArticle, categorieId);
            model.addAttribute("encheres", encheresEnCours);

            //Ajout des catégories pour l'affichage du filtre
            List<Categorie> categories = categorieService.getAllCategories();
            model.addAttribute("categories", categories);

            model.addAttribute("message", "ceci est un test");

            return "accueil"; // Retourne la vue d'accueil
        }


    @GetMapping("/")
    public String afficherAccueil(
        HttpSession session, 
        Model model,
        @RequestParam(name = "nomArticle", required = false) String nomArticle,
        @RequestParam(name = "categorieId", required = false) Long categorieId) 
        {
            // Vérifier si l'utilisateur est authentifié
            boolean isLoggedIn = isUserAuthenticated();
            model.addAttribute("isLoggedIn", isLoggedIn);

            // Ajouter les articles en cours dans le modèle
            List<Article> encheresEnCours = articleService.getEnchereEnCours(nomArticle, categorieId);
            model.addAttribute("encheres", encheresEnCours);

            
            
            //Ajout des catégories pour l'affichage du filtre
            List<Categorie> categories = categorieService.getAllCategories();
            model.addAttribute("categories", categories);

            return "accueil"; // Retourne la vue d'accueil
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
