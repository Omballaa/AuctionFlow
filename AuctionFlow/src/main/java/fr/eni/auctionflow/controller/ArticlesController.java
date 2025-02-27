package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticlesController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String afficherAccueil(Model model,
                                  @RequestParam(required = false) String nomArticle,
                                  @RequestParam(required = false) Long categorieId) {

        List<Article> encheresEnCours = articleService.getEnchereEnCours(nomArticle, categorieId);
        model.addAttribute("encheres", encheresEnCours);
        return "accueil";
    }
}
