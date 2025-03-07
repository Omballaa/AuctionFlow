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

import java.util.List;

@Controller
public class VentesController {

    private boolean isUserAuthenticated() 
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authentication = " + SecurityContextHolder.getContext().getAuthentication());
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
    }

    
    @Autowired
    private ArticleService articleService;
    

    @GetMapping("/lister-mes-ventes")
    public String listerMesVentes(Model model, HttpSession session) {
        
        //------------------------------------------------------------------
        //----------------   Gestion du isLoggedIn   -----------------------
        //------------------------------------------------------------------
        boolean isLoggedIn = isUserAuthenticated();
        model.addAttribute("isLoggedIn", isLoggedIn);
        //------------------------------------------------------------------
        //------------------------------------------------------------------

    	List<Article> articles = articleService.getMesVentes( (long) session.getAttribute("userID") ) ;
    	model.addAttribute("articles", articles);
    	return "lister-mes-ventes";
    }
    
    
}
