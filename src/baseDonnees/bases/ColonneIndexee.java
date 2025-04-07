package baseDonnees.bases;
/**
 * Cette classe propose une interface � une base de donn�es.
 * 
 * Pour le prototype de l'interface banquaire, seule deux tables sont pr�sentes:
 * 		- tables des transactions
 *	 	- tables des utilisateurs
 *
 * Les services offers:
 * 		- ajouterValeur
 * 		- changerValeur
 * 		- obtenirIndex
 * 		- afficherContenu
 * 		- estUnique
 *
 * Conception: cette classe est impl�ment� en "lazy singleton", pour garantir 
 * qu'il n'y aura qu'une base de donn�es et pour faciliter l'acc�s � l'objet.
 *
 * @author Fred Simard | ETS
 * @version H2025
 * @revision Pierre B�lisle
 * 
 * @param <E>  Le type d'une colonne indexee.
 */

@SuppressWarnings("rawtypes")
public class ColonneIndexee<E extends Comparable<E>> extends Colonne<E>  {
	
	
	/**
	 * Strat�gie : On met les valeurs dans la colonne parent et on cr�e en
	 * parall�lle un tableau statique avec index et position qu'on regroupe 
	 * dans un objet de la classe interne ValIndexee.
	 * 
	 * Les donn�es du tableau de valIndexee sont ordonn�es pour utiliser
	 * la fouille binaire.
	 */
	
	
	/**
	 * Classe interne qui permet de regrouper une valeur et sa position
	 * dans la colonne parent (super) qui n'est pas index�e.
	 * 
	 * L'�l�ment doit �tre Comparable pour �tre ins�r�e dans la liste binaire.
	 * 
	 */
	protected class ValIndexee <E extends Comparable<E>> {
		
		public int index;
		public E valeur;
		
 		
		/**
		 * Constructeur par param�tres.  Not� que celui par d�faut
		 * n'existe pas et est inutile.
		 * 
		 * @param index
		 * @param valeur
		 */
		public ValIndexee(int index, E valeur) {
			this.index = index;
			this.valeur = valeur;
		}


		public int comparer(E valeur) {
			
			// La valeur doit �tre Comparable
			return this.valeur.compareTo(valeur);
		}
		
	}
	
	// La liste des valeurs avec leur position dans la colonne parent.
	private ValIndexee[] valIndexee;
	

	/**
	 * Constructeur qui cr�e une colonne de taille Colonne.CAPACITE au depart.
	 */
	public ColonneIndexee() {
		
		valIndexee = new ValIndexee[Colonne.CAPACITE];
		
	}
 
	/**
	 * Ajoute la valeur � la fin de la colonne.
	 * 
	 * @valeur La valeur � ajouter.
	 */
	public void ajouterValeur(E valeur) {
		
		// Utilisation de la proc�dure locale avec index.
		ajouterValeur(super.nbElements, valeur);
	}

	/**
	 * Proc�dure locale qui ajoute dans la colonne parent et qui regroupe la
	 * valeur et son index avant de l'ins�rer en ordre dans la liste locale.
	 * 
	 * @param index de la valeur � ajouter
	 * @param valeur � ajouter 
	 */
	@SuppressWarnings("unchecked")
	private void ajouterValeur(int index, E valeur) {
		
		
		/* 
		 * Agrandit le tableau du tableau de valIndexee s'il est plein.
		 * Le tableau de la Colonne parent a �t� ajust� par super.ajouterValeur
	     * si tel est le cas.
	     */
		ajusterTailleValIndexe();
		
		// On regroupe la valeur et son index.
		ValIndexee valeuAAjouter = new ValIndexee(index, valeur);
		
		int i = 0;
		
		// La colonne ne doit pas �tre vide pour la recherche.
		if(super.getNbElements() != 0) {
			
			
			//  On obtient la position de la valeur dans le tableau 
			// de valeur index�e mais on recoit l'index dans la colonne originale.
		    i = obtenirIndex(valeur);
			
			// Si l'�l�ment est absent, il faut convertir la position car la
		    // valeur re�cue est n�gative.
			if( i < 0) {
				
				i = Math.abs(i+1);
			}
			
			// D�calage avant insertion.
			decalerVersHaut(i);
		}
		
		// Ajout dans la colonne d'origine.
		super.ajouterValeur(valeur);	

		// S'il n'avait aucun �l�ment, on ajoute � la position 0.
		valIndexee[i] = valeuAAjouter;
			
	}
	
	
	/**
	 * On ne permet pas � une colonne index�e de remplacer de valeur.
	 * 
	 * @throws On ne peut remplacer dans une colonne index�e.
	 * @Override
	 */
	public void changerValeur(Object valeur, int index) throws Exception{
		
		throw new Exception("Il n'y a pas de modification possible " +
				            "dans cette liste");
	}
	
	/**
	 * 
	 * Retourne la position de la valeur ou -positiionInsertion -1 si 
	 * la valeur est inexistante.
	 * 
	 * @param valeur : La valeur cherch�e.
	 * @return la position de l'�l�ment trouv�e ou la position o� elle devrait 
	 * �tre si elle est absente (voir Arrays.binarySearch).
	 * 
	 */
	@SuppressWarnings("unchecked")
	public int obtenirIndex(E valeur) {
		
		/*
		 * Strat�gie : Impl�mentstion de l'algorithme de la fouille binaire.
		 */
	    int debut = 0;
	    int fin = nbElements -1;
	    int milieu = 0;
		
		int indexARetourner = ELEMENT_ABSENT;
		
		while(debut <= fin && indexARetourner == ELEMENT_ABSENT) {
	        milieu = (debut + fin) / 2;

	        if (valIndexee[milieu].comparer(valeur) > 0) {
	            fin = milieu - 1;
	        }else if (valIndexee[milieu].comparer(valeur) < 0) {
	            debut = milieu + 1;
	        }else {
	        	indexARetourner = valIndexee[milieu].index;
	        }
		}
		
		return (indexARetourner == ELEMENT_ABSENT)?-debut-1:indexARetourner;
	}
	

	/**
	 * M�thode qui affiche le contenu de la colonne, utilis� pour deverminage
	 */
	@Override
	public void afficherContenu() {

		for(int i=0;i<nbElements;i++) {
			System.out.println(valIndexee[i].index + ":" + valIndexee[i].valeur);
		}
		
		System.out.println("Valeur dans le tableau");
		super.afficherContenu();
		
	}

	/**
	 * Retourne true si la valeur n'existe pas dans la colonne. 
	 * Donc elle sera unique.
	 * 
	 * @param valeur La valeur cherch�e
	 * 
	 * @return Si la valeur est absente de la colonne.
	 */
	public boolean estUnique(E valeur) {
		
		return obtenirIndex(valeur) < 0;
	}
	
	
	/*
	 * D�place les donn�es d'une case vers le plus haut indice du tableau
	 * de la fin jusqu'� l'index inclusivement.
	 */
	private void decalerVersHaut(int index) {
		
		for(int i=nbElements;i>index;i--) {
			valIndexee[i] = valIndexee[i-1];
		}
	}


	/*
	 * Ajuste la taille de la colonne index�e lorsque pleine.
	 */
	private void ajusterTailleValIndexe(){
		
		if(valIndexee.length == nbElements) {
			
			ValIndexee[] nouveauTab = new ValIndexee[valIndexee.length*2];
			
			super.copierTab(nouveauTab, valIndexee);
			
			valIndexee = nouveauTab;
		}
	}

}

