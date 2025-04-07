package baseDonnees.modeles;

import java.util.Arrays;

import baseDonnees.utils.UtilitairesDB;

/**
 * Classe qui repr�sente un utilisateur avec mot de passe.
 * 
 * @author Fr�d�ric Simard | ETS
 * @version H2025
 * @revision et commentaire Pierre B�lisle
 *
 */
public class Utilisateur {

	// Chaque attribut pour l'utilisateur, son num�ro de compte et son solde.
	// Les 2 autre attributs servent au mot de passe.
	private String nomUtilisateur;
	private byte[] hashMotDePasse;
	private byte[] salt;
	private String numeroDeCompte;
	private double solde = 0;


	/**
	 * Constructeur par param�tres sans hachage ni salage.
	 * 
	 * @param nomUtilisateur nom de l'utilisateur
	 * @param motDePasse le mot de passe
	 * @param numeroDeCompte le num�ro de compte
	 * @param solde le solde
	 */
	public Utilisateur(String nomUtilisateur, 
			           String motDePasse, 
			           String numeroDeCompte, 
			           double solde){
		
		/**
		 * C'est ici que le mot de passe et hach� et sal�.
		 */
		this.nomUtilisateur = nomUtilisateur;
		this.salt = UtilitairesDB.obtenirSalt();
		this.hashMotDePasse = UtilitairesDB.hashMotDePasse(motDePasse, this.salt);
		this.numeroDeCompte = numeroDeCompte;
		this.solde = solde;
	}

	/**
	 * /**
	 * Constructeur par param�tres avec hachage et salage.
	 * 
	 * @param nomUtilisateur nom de l'utilisateur
	 * @param numeroDeCompte le num�ro de compte
	 * @param hashMotDePasse hash du mot de passe
	 * @param salt le salage du mot de passe
	 * @param solde le solde
	 * @param nomUtilisateur
	 */
	public Utilisateur(String nomUtilisateur, 
			           byte[] hashMotDePasse, 
			           byte[] salt, 
			           String numeroDeCompte, 
			           double solde){
		
		this.nomUtilisateur = nomUtilisateur;
		this.hashMotDePasse = hashMotDePasse;
		this.salt = salt;
		this.numeroDeCompte = numeroDeCompte;
		this.solde = solde;
	}

	/**
	 * Retourne si le nom et le mot de passe sont les bons
	 * 
	 * @param nomUtilisateur nom d'utilisateur � comparer
	 * @param motDePasse mot de passe � comparer
	 * @return true si l'utilisateur et mot de passe sont �gaux
	 */
	public boolean authentifier(String nomUtilisateur, String motDePasse){
		
		return(this.nomUtilisateur.equals(nomUtilisateur) &&
		       Arrays.equals(this.hashMotDePasse,
				         UtilitairesDB.hashMotDePasse(motDePasse, this.salt)));
			
	}

	/**
	 * Accesseur du nom
	 * @return le nom d'utilisateur
	 */
	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	/**
	 * Accesseur du tableau de hashage.
	 * @return le mot de passe
	 */
	public byte[] getHashMotDePasse() {
		return hashMotDePasse;
	}
	
	/**
	 * Accesseur du tableau de salage.
	 * @return le salt du mot de passe
	 */
	public byte[] getSalt() {
		return salt;
	}

	/**
	 * Accesseur du num�ro de compte.
	 * @return le num�ro de compte
	 */
	public String getNumeroDeCompte() {
		return numeroDeCompte;
	}

	/**
	 * Accesseur du solde.
	 * @return le solde de l'utilisateur
	 */
	public double getSolde() {
		return solde;
	}

	/**
	 * Mutateur du solde.
	 * 
	 * @param differentiel de la transaction
	 */
	public void transactionSurSolde(double differentiel) {
		this.solde += differentiel;
	}
	
	/**
	 * Retourne le nom et le num�ro de compte en String.
	 */
	public String toString(){
		return nomUtilisateur + ":" + numeroDeCompte;
	}
	
	/**
	 * Compare 2 utilisateurs par leur num�ro de compte.
	 * 
	 * @param utilisateur � comparer
	 * @return vrai si num�ro de comptes sont les m�mes
	 */
	public boolean equals(Utilisateur utilisateur){
		return this.numeroDeCompte.equals(utilisateur.numeroDeCompte);
	}
}
