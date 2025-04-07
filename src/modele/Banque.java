package modele;

import java.util.Vector;

import baseDonnees.BaseDonnees;
import baseDonnees.modeles.Transaction;
import baseDonnees.modeles.Utilisateur;
import baseDonnees.utils.SeedDB;
import observer.MonObservable;
/**
 * La banque est l'interface de la partie dorsale du programme. Impl�ment� selon le patron
 * Lazy Singleton.
 * 
 * Elle offre les services relatifs � l'utilisateur connect� suivant:
 *  - setUtilisateurActif, pour activer un utilisateur
 *  - rafraichirUtilisateurActif, rafraichir l'information
 *  - getUtilisateurActif, obtient l'utilisateur actif
 *  - deconnecterUtilisateur, deconnecte l'utilisateur
 *  - estConnecte, d�terminer s'il y a un utilisateur connect�
 *  - obtenirTransactionsPourCompte, obtient la s�rie de transactions pour ce compte
 * 
 * Elle offre les services d'acc�s � la base de donn�es suivants:
 * 	-  verifier, autorisation de l'utilisateur
 * 	-  soumettreTransaction, soumets une transaction au syst�me
 * 
 * @author Fred Simard | ETS
 * @version H2025
 * @revision et commentaires Pierre B�lisle
 */



public class Banque extends MonObservable{

	// Sert � obtenir l'instance de la base de donn�es.
	BaseDonnees bd;
	// Sert � retenir l'utilisateur actif.
	Utilisateur utilisateurActif;

	// La banque en singleton.
	private static Banque instance = new Banque();
	
    // �tat de la connexion entre le frontal et le dorsal.
	private boolean connecte = false;

	/**
	 * constructeur de la Banque
	 * instancie et seed la Base de don��e
	 */
	private Banque() {
		bd = new BaseDonnees();		
		SeedDB.seed(bd);
	}

	/**
	 * M�thode pour obtenir une instance de la Banque
	 * @return instance de la Banque
	 */
	public static Banque getInstance() {return instance;};

	/**
	 * Indique que le nom d'utilisateur et le mot de passe sont ceux de la bd
	 * ou non.
	 * .
	 * @param nomUtilisateur Le nom � authentifier.
	 * @param motDePasse Le mot de passe correspondant.
	 * @return Si le nom et le mot de passe sont bons.
	 */
	public boolean verifier(String nomUtilisateur, String motDePasse) {
		Utilisateur utilisateur = bd.obtenirUtilisateurParNom(nomUtilisateur);
		
		if(utilisateur != null) {
			
			System.out.println("v�rification de l'utilisateur");
			System.out.println(utilisateur.toString());
			return utilisateur.authentifier(nomUtilisateur, motDePasse);
		}else {
			return false;
		}
	}

	/**
	 * Ajoute une nouvelle transaction, la m�thode �tablit le status de la transaction
	 * @param nouvelleTransaction la transaction � ajouter
	 */
	public void soumettreTransaction(Transaction nouvelleTransaction) {
		Utilisateur source;
		Utilisateur destination;
		
		try {
			// obtient le num�ro de compte source
			source = bd.obtenirUtilisateurPourCompte(nouvelleTransaction.getNoCompteSource());

			// obtient le num�ro de compte destination
			destination = bd.obtenirUtilisateurPourCompte(nouvelleTransaction.getNoCompteDestination());
		}catch(Exception E) {
			System.out.println(E);
			return;
		}

		// La destination et la source �gales ou � nulles, 
		// on ne peut pas transiger.
		if(source == null || destination==null || source.equals(destination)) {
			nouvelleTransaction.setStatus(Transaction.REFUSE);
			
		// Il faut suffisament d'argent dans la source.
		}else {
			
			if(nouvelleTransaction.getMontant() > source.getSolde() || nouvelleTransaction.getMontant() <= 0) {
				nouvelleTransaction.setStatus(Transaction.REFUSE);
				
			// Tout est beau, la transaction est affect�e et lea bd mise � jour.	
			}else {
				System.out.println("Transaction accepte");
				nouvelleTransaction.setStatus(Transaction.ACCEPTE);
				bd.mettreAJourSoldeUtilisateur(source, source.getSolde()-nouvelleTransaction.getMontant());
				bd.mettreAJourSoldeUtilisateur(destination, destination.getSolde()+nouvelleTransaction.getMontant());
			}
		}
		// ajoute la transaction
		bd.ajouterTransaction(nouvelleTransaction);

		// rafraichit l'information
		rafraichirUtilisateurActif();

		// averti les observers de la mise � jours
		this.avertirLesObservers();
	}

	/**
	 * Permet d'obtenir un utilisateur actif � partir de son nom.
	 * Les observateurs sont avis�s.
	 * 
	 * @param nomUtilisateur Le nom de l'utilisateur actif voulu
	 */
	public void setUtilisateurActif(String nomUtilisateur) {
		utilisateurActif = bd.obtenirUtilisateurParNom(nomUtilisateur);
		
		System.out.println("activation de l'utilisateur");
		System.out.println(utilisateurActif);
		
		connecte = true;
		this.avertirLesObservers();
	}

	/**
	 * Permet de rafraichir la r�f�rence de l'utilisateur actif de la banque.
	 */
	public void rafraichirUtilisateurActif() {
		
		if(utilisateurActif!=null) {
			utilisateurActif = bd.obtenirUtilisateurParNom(utilisateurActif.getNomUtilisateur());
		}
	}

	/**
	 * Retourne la r�f�rence sur l'Utilisateur actif.
	 * 
	 * @return L'utilisateur actif ou null si aucun.
	 */
	public Utilisateur getUtilisateurActif() {
		return utilisateurActif;
	}

	/**
	 * Permet de d�connecter l'utilisateur actif.  Les observateurs sont avis�s.
	 */
	public void deconnecterUtilisateur() {
		utilisateurActif = null;
		connecte = false;
		this.avertirLesObservers();
	}

	/**
	 * Accesseur de l'�tat de connection.
	 * @return l'�tat de connection.
	 */
	public boolean estConnecte() {
		return connecte;
	}

	/**
	 * Retourne les transactions de l'utilisateur actif pour son num�ro de compte.
	 * 
	 * @return Un Vector des transactions du compte de l'utilisateur actif.
	 */
	public Vector<Transaction> obtenirTransactionsPourCompte() {
		return bd.obtenirTransactionsPourCompte(utilisateurActif.getNumeroDeCompte());
	}
}



