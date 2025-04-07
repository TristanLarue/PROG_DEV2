package baseDonnees;

/**
 * Cette classe propose une interface � une base de donn�es.
 * 
 * Pour le prototype de l'interface banquaire, seule deux tables sont pr�sentes:
 * 		- tables des transactions
 *	 	- tables des utilisateurs
 *
 * Les services offers:
 * 		- viderLaBd
 * 		- ajouterUtilisateur
 * 		- obtenirUtilisateurParNom
 * 		- obtenirUtilisateurPourCompte
 * 		- ajouterTransaction
 * 		- obtenirTransactionsPourCompte
 * 		- mettreAJourSoldeUtilisateur
 *
 * Conception: cette classe est impl�ment� en "lazy singleton", pour garantir 
 * qu'il n'y aura qu'une base de donn�es et pour faciliter l'acc�s � l'objet.
 *
 * @author Fred Simard | ETS
 * @version Automne 2020
 * @revision Pierre B�lisle
 */

import java.util.Vector;

import baseDonnees.modeles.Transaction;
import baseDonnees.modeles.Utilisateur;
import baseDonnees.tables.TableTransactions;
import baseDonnees.tables.TableUtilisateurs;

public class BaseDonnees {
	
	// Les tables.
	private TableTransactions tableTransactions;
	private TableUtilisateurs tableUtilisateurs;
	
	
	/**
	 * Constructeur par d�faut, private parce que singleton
	 */
	public BaseDonnees() {
		
		tableTransactions = new TableTransactions();
		tableUtilisateurs = new TableUtilisateurs();
	};
	
	/**
	 * Permet de vider la base de donn�es
	 */
	public void viderLaBd() {
		
		tableTransactions = new TableTransactions();
		tableUtilisateurs = new TableUtilisateurs();
	}

	/**
	 * permet d'ajouter un utilisateur � la base de donn�es
	 * @param nouvelUtilisateur l'utilisateur � ajouter
	 * @return (boolean) confirmation que l'ajout a fonctionn�
	 */
	public void ajouterUtilisateur(Utilisateur nouvelUtilisateur) {
		tableUtilisateurs.ajouterUnUtilisateur(nouvelUtilisateur);
	}
	
	/**
	 * Permet d'obtenir un utilisateur � partir de son nom
	 * @param nomUtilisateur nom de l'utilisateur que l'on veut retrouver
	 * @return (Utilisateur) l'utilisateur ou null
	 */
	public Utilisateur obtenirUtilisateurParNom(String nomUtilisateur) {
		return tableUtilisateurs.obtenirUtilisateurParNom(nomUtilisateur);
	}

	/**
	 * Permet d'obtenir un utilisateur � partir de son num�ro de compte
	 * @param numeroDeCompte num�ro de compte de l'utilisateur que l'on veut retrouver
	 * @return (Utilisateur) l'utilisateur ou null
	 */
	public Utilisateur obtenirUtilisateurPourCompte(String numeroDeCompte) {
		return tableUtilisateurs.obtenirUtilisateurParCompte(numeroDeCompte);
	}
	
	/**
	 * Permet de modifier le solde d'un utilisateur
	 * @param utilisateur utilisateur pour lequel on veut changer le solde
	 * @param solde le nouveau solde � attribuer
	 */
	public void mettreAJourSoldeUtilisateur(Utilisateur utilisateur, double solde) {
		tableUtilisateurs.mettreAJourSoldeUtilisateur(utilisateur, solde);
	}
	
	/**
	 * permet d'ajouter une transaction � la base de donn�es
	 * @param transaction la transaction � ajouter
	 * @return (boolean) confirmation que l'ajout a fonctionn�
	 */
	public void ajouterTransaction(Transaction transaction) {
		tableTransactions.ajouterUneTransaction(transaction);
	}

	/**
	 * Permet d'obtenir toutes les transactions associ� � un num�ro de compte
	 * @param numeroDeCompte num�ro de compte de l'utilisateur pour lequel on veut les transactions
	 * @return (Vector) les transactions dans une collection vector
	 */
	public Vector<Transaction> obtenirTransactionsPourCompte(String numeroDeCompte) {
		return tableTransactions.obtenirTransactionsPourCompte(numeroDeCompte);
	}
	

}
