package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.dao.CategorieDao;
import fr.eni.auctionflow.dao.UtilisateurDao;
import fr.eni.auctionflow.dto.EncherirDTO;
import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.model.Categorie;
import fr.eni.auctionflow.model.Enchere;
import fr.eni.auctionflow.model.Utilisateur;
import fr.eni.auctionflow.service.ArticleService;
import fr.eni.auctionflow.service.CustomUserDetails;
import fr.eni.auctionflow.service.EnchereService;
import fr.eni.auctionflow.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class EnchereController {

    // Méthode pour vérifier si l'utilisateur est authentifié
    private boolean isUserAuthenticated()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authentication = " + SecurityContextHolder.getContext().getAuthentication());
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
    }

    /*private Utilisateur getUserAuthenticated()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            return UtilisateurServiceImpl.findByPseudoOrEmail(authentication.getName(), authentication.getName()); // Retourne le nom d'utilisateur
        }
        return null; // Aucun utilisateur authentifié
    }*/



    private final EnchereService enchereService;
    private final ArticleService articleService;
    private final CategorieDao categorieDao;

    public EnchereController(EnchereService enchereService, ArticleService articleService, CategorieDao categorieDao) {
        this.enchereService = enchereService;
        this.articleService = articleService;
        this.categorieDao = categorieDao;
    }

    @Autowired
    private UtilisateurDao utilisateurDao;



    @GetMapping("/enchrir/{noArticle}")
    public String encherirGET(@RequestParam long noArticle, Model model) {


        Article article = articleService.getArticleParNoArticle(noArticle);
        EncherirDTO dto = new EncherirDTO();
        dto.setNoArticle(noArticle);// THEO : un champ hidden
        Enchere topEnchere = enchereService.getMeilleureEnchere(dto.getNoArticle());
        dto.setMontantEnchere(topEnchere.getMontantEnchere() + 1);

        model.addAttribute("article", article);
        model.addAttribute("dto", dto);

        return "/nomDeLaVueQuiAfficheLesEncherechesDeArtiucleEtFormPourEnchere";
    }

    //Encherir
    @PostMapping("/encherir")
    public String encherirPOST(
            @ModelAttribute(name = "dto") EncherirDTO encherirDTO,
            HttpSession session,
            Model model
    ) {

        // Vérifier si l'utilisateur est connecté
        if (session.getAttribute("userID") == null) {
            return "redirect:/utilisateurs/connexion";
        }

        Long userID = (Long) session.getAttribute("userID");

        try {
            enchereService.encherir(encherirDTO.getNoArticle(), userID, encherirDTO.getMontantEnchere());
            model.addAttribute("message", "Votre enchère a été prise en compte !");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/enchere/detail/" + encherirDTO.getNoArticle();
    }


    @GetMapping("/enchere/detail-encheres-sur-mon-article/{noArticle}")
    public String detailEncheresSurMonArticle(long noArticle, Model model, HttpSession session) {

        List<Enchere> encheres = enchereService.listerEnchereArticleAppartenantAUtil(noArticle, (long) session.getAttribute("userID"));
        model.addAttribute("encheres", encheres);
        return "/enchere/detail";
    }

    // Méthode GET pour afficher la page d'ajout d'enchère avec les catégories disponibles
    @GetMapping("/ventes/ajouter")
    public String afficherFormulaireAjoutEnchere(Model model) {

        //------------------------------------------------------------------
        //----------------   Gestion du isLoggedIn   -----------------------
        //------------------------------------------------------------------
        boolean isLoggedIn = isUserAuthenticated();
        model.addAttribute("isLoggedIn", isLoggedIn);
        //------------------------------------------------------------------
        //------------------------------------------------------------------

        // Récupérer toutes les catégories
        List<Categorie> categories = categorieDao.findAll();
        model.addAttribute("categories", categories);
        return "./ventes/ajouter"; // Cette vue affichera le formulaire d'ajout d'enchère
    }


    @PostMapping("/ventes/ajouter")
    public String ajouterEnchere(
            @RequestParam String articleName,
            @RequestParam String articleDescription,
            @RequestParam Long no_Categorie,
            @RequestParam Integer startingPrice,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @AuthenticationPrincipal CustomUserDetails userDetails, // Récupération de l'utilisateur connecté
            Model model
    ) {
        if (userDetails == null) {
            model.addAttribute("error", "Vous devez être connecté pour ajouter une enchère !");
            return "redirect:/utilisateurs/connexion";
        }

        Long utilisateurId = userDetails.getId(); // Récupération de l'ID utilisateur
        System.out.println("Utilisateur connecté ID : " + utilisateurId);

        Utilisateur utilisateur = utilisateurDao.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Création et sauvegarde de l'article
        Article article = new Article();
        article.setNomArticle(articleName);
        article.setDescription(articleDescription);
        article.setCategorie(categorieDao.findById(no_Categorie).orElseThrow());
        article.setPrixInitial(startingPrice);
        article.setDateDebutEncheres(startDate);
        article.setDateFinEncheres(endDate);
        article.setUtilisateur(utilisateur);

        article = articleService.ajouterArticle(article);

        // Création et sauvegarde de l'enchère
        Enchere enchere = new Enchere();
        enchere.setArticle(article);
        enchere.setMontantEnchere(startingPrice);
        enchere.setDateEnchere(new Date());
        enchere.setUtilisateur(utilisateur);

        enchereService.ajouterEnchere(enchere);

        model.addAttribute("message", "L'enchère a été ajoutée avec succès !");
        return "accueil";
    }
}