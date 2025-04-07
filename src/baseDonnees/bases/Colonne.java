package baseDonnees.bases;

/**
 * Cette classe définit une colonne de la base de données
 * 
 * La colonne offre plusieurs services permettant d'ajouter et d'accéder aux
 * éléments stocké dans la colonne.
 * 
 * Les services offerts:
 * 		- obtenirValeur
 * 		- obtenirIndex
 * 		- ajouterALaFin
 * 		- enleverValeur
 * 		- changerValeur
 *
 * @author Fred Simard | ETS
 * @version Automne 2020
 * @revision et commentaire Pierre Bélisle
 */

@SuppressWarnings("unchecked")
public class Colonne<V> implements InterfaceColonne<V> {

	// Retournée par obtenirIndex si la valeur cherchée est absente.
	public static final int ELEMENT_ABSENT = -1;

	// Capacité d'une colonne au départ.
	public static final int CAPACITE = 10;
	
	// Le tableau contenant les données d'une colonne et 
	// son nombre d'éléments significatifs.
	protected V[] tab;
	protected int nbElements = 0;
	
	/**
	 * Construteur par défaut avec une capacité de Colonne.CAPACITE
	 */
	public Colonne() {
		
		/*
		 * Stratégie : Avec un tableau d'objets transtypé pour le type générique.
		 * Il n'est pas possible d'instancier un type générique directement
		 *  (new V[])
		 */
		tab = (V[]) new Object[CAPACITE];
	}

	/**
	 * Ajoute une valeur à la fin de la colonne.
	 * 
	 * @param valeur à ajouter
	 */
	public void ajouterValeur(V valeur) {
		
		ajusterTailleListe();
		
		tab[nbElements] = valeur;
		nbElements++;
	}
	
	/**
	 * Retourne la valeur à l'index.
	 * 
	 * @param index La position pour laquelle ont veut la valeur
	 * @return La valeur à la posiiton de l'index.
	 */
	public V obtenirValeur(int index) throws Exception{	

		validerIndex(index);
		
		return tab[index];
	}
	
    /**
     * Procédure locale pour éviter de répéter la condition de validation 
     * de l'index.
     * 
     * @param index index à valider
     * @throws Exception index <0 || index > nbElements
     */
	private void validerIndex(int index) throws Exception{
		
		if(index<0 || index>=nbElements) {
			throw new Exception("Index invalide");
		}
	}

	/**
	 * Retourne la position de la valeur dans la colonne ou Colonne.ELEMENT_ABSENT.
	 * 
	 * @param valeur La valeur à chercher.
	 * @return la position de la valeur dans la colonne ou Colonne.ELEMENT_ABSENT.
	 */
	public int obtenirIndex(V valeur) {
		
		/*
		 * Stratégie : Implémentation de la fouille linéaire
		 */
		int indexARetourner = ELEMENT_ABSENT;
		int i=0;
		
		while(i<nbElements && indexARetourner == ELEMENT_ABSENT) {
			if(tab[i].equals(valeur)){
				indexARetourner = i;
			}
			i++;
		}
		
		return indexARetourner;
	}
	
	
	/**
	 * Si l'index est valide, la valeur remplace celle à cette position.
	 * 
	 * @param index l'index de la valeur à changer
	 * @param valeur nouvelle valeur
	 */
	public void changerValeur(int index, V valeur) throws Exception{
		
		validerIndex(index);
		tab[index] = valeur;
	}

	/**
	 * Augmente la taille du tableau lorsqu'il est plein.
	 */
	private void ajusterTailleListe(){
		
		if(tab.length == nbElements) {
			
			V[] nouveauTab = (V[]) new Object[tab.length*2];

			copierTab(nouveauTab, tab);
			
			tab = nouveauTab;
			
		}
	}
	
	/**
	 * Procédure privée qui copie les valeurs d'un tableau dans un autre tableau
	 * 
	 * @param nouveauTab destination des données
	 * @param tab origine des données
	 */
	protected void copierTab(Object[] nouveauTab, Object[] tab) {
		
		for(int i=0;i<tab.length;i++) {
			nouveauTab[i] = tab[i];
		}
		
	}

	/**
	 * Accesseur du nombre d'éléments.
	 */
	public int getNbElements() {
		return nbElements;
	}

	/**
	 * Affiche le contenu des la liste dans la console.
	 * Sert essentiellement au débaogage.
	 */
	public void afficherContenu(){
		
		for(int i=0;i<nbElements;i++) {
			System.out.println(tab[i]);
		}
	}

}
