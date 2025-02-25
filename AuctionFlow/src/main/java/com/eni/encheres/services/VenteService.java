package com.eni.encheres.services;

import com.eni.encheres.bo.Vente;
import java.util.List;

public interface VenteService {
    void ajouterVente(Vente vente);
    List<Vente> listerVentes();
}
