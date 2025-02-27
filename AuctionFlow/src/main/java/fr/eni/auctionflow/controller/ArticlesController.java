package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ArticlesController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String afficherAccueil(Model model) {
        List<Article> encheresEnCours = articleService.getEnchereEnCours();
        model.addAttribute("encheres", encheresEnCours);
        return "accueil";
    }
}
