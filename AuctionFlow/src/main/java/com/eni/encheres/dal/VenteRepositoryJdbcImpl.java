package com.eni.encheres.dal;

import com.eni.encheres.bo.Vente;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class VenteRepositoryJdbcImpl {
    private List<Vente> ventes = new ArrayList<>();

    public VenteRepositoryJdbcImpl() {
        ventes.add(new Vente(1L, "Grille-pain", "Grille-pain compact avec 2 fentes", 20, LocalDateTime.now(), LocalDateTime.now().plusDays(10), 1L, "12 rue des Oliviers, Nantes", 1L, 25));
        ventes.add(new Vente(2L, "Lampe de bureau", "Lampe LED avec intensité réglable", 15, LocalDateTime.now(), LocalDateTime.now().plusDays(8), 2L, "45 avenue des Lumières, Lyon", 2L, 18));
        ventes.add(new Vente(3L, "Cafetière", "Cafetière filtre 12 tasses", 30, LocalDateTime.now(), LocalDateTime.now().plusDays(6), 3L, "78 boulevard du Café, Paris", 3L, 35));
        ventes.add(new Vente(4L, "Chaise pliante", "Chaise légère et facile à ranger", 25, LocalDateTime.now(), LocalDateTime.now().plusDays(9), 4L, "5 impasse du Mobilier, Toulouse", 4L, 28));
        ventes.add(new Vente(5L, "Aspirateur à main", "Aspirateur sans fil rechargeable", 40, LocalDateTime.now(), LocalDateTime.now().plusDays(5), 5L, "33 rue de la Propreté, Bordeaux", 5L, 50));
    }

    public void ajouterVente(Vente vente) {
        ventes.add(vente);
    }

    public List<Vente> listerVentes() {
        return this.ventes;
    }
}