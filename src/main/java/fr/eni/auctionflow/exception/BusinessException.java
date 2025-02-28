package fr.eni.auctionflow.exception;

/**
 * Exception métier. Le champ message est utilisé pour indiquer l'erreur.
 */
public class BusinessException extends Exception{

	/**
	 * Nouvelle exception métier
	 * @param message Le message décrivant l'erreur métier.
	 */
	public BusinessException(String message) {
		super(message);
	}
}
