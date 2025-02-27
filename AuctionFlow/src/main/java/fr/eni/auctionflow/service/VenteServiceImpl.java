package fr.eni.auctionflow.service;

import com.eni.encheres.bo.Vente;
import fr.eni.auctionflow.dal.VenteRepositoryJdbcImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenteServiceImpl implements VenteService {
	
	@Autowired
    private VenteRepositoryJdbcImpl venteRepository;

    @Override
    public void ajouterVente(Vente vente) {
        venteRepository.ajouterVente(vente);
    }

    @Override
    public List<Vente> listerVentes() {
        return venteRepository.listerVentes();
    }
}
