package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.dao.CategorieDao;
import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.model.Categorie;
import fr.eni.auctionflow.model.Enchere;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.ArticleService;
import fr.eni.auctionflow.service.EnchereService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class EnchereController {

    private final EnchereService enchereService;
    private final ArticleService articleService;
    private final CategorieDao categorieDao;

    public EnchereController(EnchereService enchereService, ArticleService articleService, CategorieDao categorieDao) {
        this.enchereService = enchereService;
        this.articleService = articleService;
        this.categorieDao = categorieDao;
    }

    // Méthode GET pour afficher la page d'ajout d'enchère avec les catégories disponibles
    @GetMapping("/ventes/ajouter")
    public String afficherFormulaireAjoutEnchere(Model model) {
        // Récupérer toutes les catégories
        List<Categorie> categories = categorieDao.findAll();
        model.addAttribute("categories", categories);
        return "./ventes/ajouter"; // Cette vue affichera le formulaire d'ajout d'enchère
    }

    // Méthode POST pour ajouter l'enchère
    @PostMapping("/ventes/ajouter")
    public String ajouterEnchere(
            @RequestParam String articleName,
            @RequestParam String articleDescription,
            @RequestParam Long no_Categorie,
            @RequestParam Integer startingPrice,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @RequestParam Long utilisateurId,
            Model model
    ) {
        // Récupérer la catégorie
        Optional<Categorie> categorieOptional = categorieDao.findById(no_Categorie);
        if (categorieOptional.isEmpty()) {
            model.addAttribute("error", "Catégorie introuvable !");
            return "accueil";
        }
        Categorie categorie = categorieOptional.get();

        // Récupérer l'utilisateur (à implémenter dans un UserService)
        Utilisateur utilisateur = new Utilisateur(); // ⚠️ Remplace ceci par une vraie récupération depuis la base
        utilisateur.setNoUtilisateur(utilisateurId);

        // Création de l'article
        Article article = new Article();
        article.setNomArticle(articleName);
        article.setDescription(articleDescription);
        article.setCategorie(categorie);
        article.setPrixInitial(startingPrice);
        article.setDateDebutEncheres(startDate);
        article.setDateFinEncheres(endDate);
        article.setUtilisateur(utilisateur);

        // Sauvegarder l'article
        article = articleService.ajouterArticle(article);

        // Création de l'enchère
        Enchere enchere = new Enchere();
        enchere.setArticle(article);
        enchere.setMontantEnchere(startingPrice);
        enchere.setDateEnchere(new Date());

        // Sauvegarde de l'enchère
        enchereService.ajouterEnchere(enchere);

        model.addAttribute("message", "L'enchère a été ajoutée avec succès !");
        return "accueil";
    }
}
