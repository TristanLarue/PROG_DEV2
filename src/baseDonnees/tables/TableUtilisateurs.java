package baseDonnees.tables;

import baseDonnees.bases.Colonne;
import baseDonnees.bases.ColonneIndexee;
import baseDonnees.modeles.Utilisateur;
import baseDonnees.utils.UtilitairesDB;
/**
 * Repr�sente une table d'une base de donn�es qui contient 3 colonnes visibles
 * et 2 colonnes cach�es pour le mot de passe et le salage.
 * 
 *
 * La classe TableUtilisateur offre plusieurs services permettant de g�rer la
 * les colonnes.
 * 
 * Les services offerts:
 * 		- ajouterUnUtilisateur
 *      - obtenirUtilisateurParNom
 *      - obtenirUtilisateurParCompte
 *      - mettreAJourSoldeUtilisateur
 *
 * @author Fred Simard | ETS
 * @version H2025
 * @revision et commentaire Pierre B�lisle
 */
public class TableUtilisateurs{

	/*
	 * Strat�gie : La colonne utilisateur et la colonne du num�ro de compte qui 
	 * sont index�e mais pas les autres colonnes.
	 */
	
	// Une table est consitu� de plusieurs colonnes.
	private ColonneIndexee<String> nomUtilisateurs;
	private Colonne<byte[]> hashMotDePasse;
	private Colonne<byte[]> salt;
	private ColonneIndexee<String> numeroDeCompte;
	private Colonne<Double> solde;
	
	/**
	 * Constucteur par d�faut qui cr�e une table avec des colonnes vides.
	 */
	public TableUtilisateurs(){
		
		/*
		 * Strat�gie : C'est ici qu'on instancie explicitement les attributs
		 */
		nomUtilisateurs = new ColonneIndexee<String>();
		hashMotDePasse = new Colonne<byte[]>();
		salt = new Colonne<byte[]>();
		numeroDeCompte = new ColonneIndexee<String>();
		solde = new Colonne<Double>();
		
	}

	/**
	 * Ajoute un utilisateur dans la bd (s'il n'existe pas d�j� ?).
	 * 
	 * @param utilisateur � ajouter
	 * 
	 * @return Si l'ajout s'est bien pass�e.
	 */
	public boolean ajouterUnUtilisateur(Utilisateur utilisateur) {
		
		// test l'unicit� de l'utilisateur et du num�ro de compte
		if(this.numeroDeCompte.estUnique(utilisateur.getNumeroDeCompte()) &&
		   this.nomUtilisateurs.estUnique(utilisateur.getNomUtilisateur())){

			// ajoute les attributs aux colonnes
			this.numeroDeCompte.ajouterValeur(utilisateur.getNumeroDeCompte());
			this.nomUtilisateurs.ajouterValeur(utilisateur.getNomUtilisateur());
			this.hashMotDePasse.ajouterValeur(utilisateur.getHashMotDePasse());
			this.salt.ajouterValeur(utilisateur.getSalt());
			this.solde.ajouterValeur(utilisateur.getSolde());
			
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sert � obtenir l'utilisateur qui correspond au nom fourni 
	 * ou null si absent.
	 * 
	 * @param nomUtilisateur L'utilisateur d�sir�.
	 * 
	 * @return L'utilisateur correspondant ou null.
	 */
	public Utilisateur obtenirUtilisateurParNom(String nomUtilisateur) {
		
		/*
		 * Strat�gie : On obtient l'index par le nom d'utilisateur dans la
		 * colonne du nom et si l'utilisateur existe, une copie de l'instance 
		 * est retourn�e.
		 */
		int indexElement = this.nomUtilisateurs.obtenirIndex(nomUtilisateur);
		
		if(indexElement==ColonneIndexee.ELEMENT_ABSENT) {
			return null;	
		}
		
		try {
			return new Utilisateur(nomUtilisateur, 
					               this.hashMotDePasse.obtenirValeur(indexElement), 
					               this.salt.obtenirValeur(indexElement),
					               this.numeroDeCompte.obtenirValeur(indexElement),
					               this.solde.obtenirValeur(indexElement));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Sert � obtenir l'utilisateur qui correspond au num�ro de compte fourni 
	 * ou null si absent.
	 * 
	 * @param numeroDeCompte Num�ro de compte d�sir�.
	 * 
	 * @return L'utilisateur correspondant ou null.
	 */
	public Utilisateur obtenirUtilisateurParCompte(String numeroDeCompte) {

		/*
		 * Strat�gie : On obtient l'index par le num�ro de compte dans la
		 * colonne du num�ro et si l'utilisateur existe, une copie de l'instance 
		 * est retourn�e.
		 */
		int indexElement = this.numeroDeCompte.obtenirIndex(numeroDeCompte);
		
		if(indexElement==ColonneIndexee.ELEMENT_ABSENT) {
			return null;	
		}
		
		try {
			return new Utilisateur(this.nomUtilisateurs.obtenirValeur(indexElement), 
						           this.hashMotDePasse.obtenirValeur(indexElement),
						           this.salt.obtenirValeur(indexElement), 
						           numeroDeCompte,
						           this.solde.obtenirValeur(indexElement));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Met � jour le solde dans la bonne colonne selon l'utilisateur.
	 * 
	 * @param utilisateur utilisateur pour lequel on veut changer le solde
	 * @param solde nouveau solde
	 */
	public void mettreAJourSoldeUtilisateur(Utilisateur utilisateur, 
			                                double solde) {
		
		/*
		 * Strat�gie : On obtient la position de l'index par 
		 * la colonne num�ro de compte.
		 */

		int indexElement = 
			  this.numeroDeCompte.obtenirIndex(utilisateur.getNumeroDeCompte());

		if(indexElement != Colonne.ELEMENT_ABSENT) {
			
			try{
				
				System.out.println("[TableUtilisateur] Changement de solde: " +
						indexElement);
			
				this.solde.changerValeur(indexElement, solde);
			}		
			
			// Ne devrait pas arriver
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		else {

			System.out.println("[TableUtilisateur] �chec au changement de solde");

		}
	}
}
