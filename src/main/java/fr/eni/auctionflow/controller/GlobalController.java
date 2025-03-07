package fr.eni.auctionflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class GlobalController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalController.class);

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUserAuthenticated = authentication != null && authentication.isAuthenticated() 
                                      && !"anonymousUser".equals(authentication.getPrincipal());

        logger.info("DEBUG: isLoggedIn = {}", isUserAuthenticated);

        return isUserAuthenticated;
    }
}
