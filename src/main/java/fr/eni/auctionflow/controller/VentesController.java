package fr.eni.auctionflow.controller;

import fr.eni.auctionflow.model.Article;
import fr.eni.auctionflow.service.ArticleService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VentesController {

    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/lister-mes-ventes")
    public String listerMesVentes(Model model, HttpSession session) {
    	List<Article> articles = articleService.getMesVentes( (long) session.getAttribute("userID") ) ;
    	model.addAttribute("articles", articles);
    	return "lister-mes-ventes";
    }
    
    
}
