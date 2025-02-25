package com.eni.encheres.controllers;

import com.eni.encheres.bo.Vente;
import com.eni.encheres.services.VenteService;
import com.eni.encheres.services.VenteServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventes")
public class VenteController {
	
	@Autowired
    private VenteService venteService;

	@GetMapping("/ajouter")
    public String ajouterGet(Model model) {
        
		model.addAttribute("vente", new Vente());
		
		return "/ventes/ajouter.html";
    }
	
    @PostMapping("/ajouter")
    public String ajouterPost(Model model,  /*@Valid*/ Vente vente,  BindingResult bindingResult ) {
    	if(bindingResult.hasErrors()) {
    		return "index";
    	}
        venteService.ajouterVente(vente);
        return "redirect:/vente/lister";
    }

    @GetMapping("/lister")
    public String listerVentes() {
        //venteService.listerVentes();
    	
    	return "/ventes/lister.html";
    }
}
