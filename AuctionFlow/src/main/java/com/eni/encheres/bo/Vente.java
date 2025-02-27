package com.eni.encheres.bo;

import java.time.LocalDateTime;


public class Vente {
    private Long id;
    private String nomArticle;
    private String description;
    private int miseAPrix;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Long idCategorie;
    private String adresseRetrait;
    private Long idVendeur;
    private Integer meilleureOffre;

    public Vente() {}
    
    public Vente(Long id, String nomArticle, String description, int miseAPrix, LocalDateTime dateDebut, LocalDateTime dateFin, Long idCategorie, String adresseRetrait, Long idVendeur, Integer meilleureOffre) {
        this.id = id;
        this.nomArticle = nomArticle;
        this.description = description;
        this.miseAPrix = miseAPrix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idCategorie = idCategorie;
        this.adresseRetrait = adresseRetrait;
        this.idVendeur = idVendeur;
        this.meilleureOffre = meilleureOffre;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public LocalDateTime getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDateTime getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDateTime dateFin) {
		this.dateFin = dateFin;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getAdresseRetrait() {
		return adresseRetrait;
	}

	public void setAdresseRetrait(String adresseRetrait) {
		this.adresseRetrait = adresseRetrait;
	}

	public Long getIdVendeur() {
		return idVendeur;
	}

	public void setIdVendeur(Long idVendeur) {
		this.idVendeur = idVendeur;
	}

	public Integer getMeilleureOffre() {
		return meilleureOffre;
	}

	public void setMeilleureOffre(Integer meilleureOffre) {
		this.meilleureOffre = meilleureOffre;
	}
    
    
}