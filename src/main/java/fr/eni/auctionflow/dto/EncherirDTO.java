package fr.eni.auctionflow.dto;

public class EncherirDTO {

	private int montantEnchere;
	private long noArticle;
	
	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	public long getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(long noArticle) {
		this.noArticle = noArticle;
	}
}
