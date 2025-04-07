package baseDonnees.bases;

/**
 * Interface d'une colonne
 * 
 * @author Fred Simard | ETS
 * @version H2025
 * @revision Pierre B�lisle
 * 
 * @param <V>  Le type d'une colonne indexee.
 */
public interface InterfaceColonne<V> {

	public abstract void ajouterValeur(V valeur);
	public abstract V obtenirValeur(int index) throws Exception;
	public abstract int obtenirIndex(V valeur);
	public abstract void changerValeur(int index, V valeur) throws Exception;
	public abstract int getNbElements();
	public abstract void afficherContenu();
	
}
