package baseDonnees.modeles;
/**
 * Repr�sente une transaction bancaire entre 2 utilisateurs.
 * 
 * Une transaction peut-�tre ACCEPTE, REFUSE ou A_DETERMINER
 * @author Fred�ric Simard
 * @version H2025
 * @revision et commentaire Pierre B�lisle
 *
 */
public class Transaction{
	
	// Les �tats possible d'une transaction.
	public static final String ACCEPTE = "Accepte";
	public static final String REFUSE = "Refuse";
	public static final String A_DETERMINER = "A determiner";

	// Les infos d'une transaction.
	private String noCompteSource;
	private String noCompteDestination;
	private double montant;
	private String status;
	
	/**
	 * Constructeur par param�tres avec status.
	 * 
	 * @param noCompteSource num�ro du compte source
	 * @param noCompteDestination num�ro du compte destination
	 * @param montant montant de la transaction
	 * @param status statut de la transaction
	 */
	public Transaction(String noCompteSource, 
			           String noCompteDestination, 
			           double montant, 
			           String status) {
		
		this.noCompteSource = noCompteSource;
		this.noCompteDestination = noCompteDestination;
		this.montant = montant;
		this.status = status;
	}

	/**
	 * Constructeur par param�tres sans status.
	 * 
	 * @param noCompteSource num�ro du compte source
	 * @param noCompteDestination num�ro du compte destination
	 * @param montant montant de la transaction
	 */ 
	public Transaction(String noCompteSource, 
			           String noCompteDestination, 
			           double montant) {
		
		this.noCompteSource = noCompteSource;
		this.noCompteDestination = noCompteDestination;
		this.montant = montant;
		this.status = A_DETERMINER;
	}

	/**
	 * 	Accesseur du compte source.
	 *  
	 * @return le compte source de la transaction
	 */
	public String getNoCompteSource() {
		return noCompteSource;
	}

	/**
	 * 	Accesseur du compte destination.
	 *  
	 * @return le compte destination de la transaction
	 */
	public String getNoCompteDestination() {
		return noCompteDestination;
	}

	/**
	 * Accesseur du montant.
	 * 
	 * @return le montant de la transaction
	 */
	public double getMontant() {
		return montant;
	}

	/**
	 * Accesseur du status.
	 * 
	 * @return le status de la transaction
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Mutateur du status.
	 * 
	 * @param status Le nouveau status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
