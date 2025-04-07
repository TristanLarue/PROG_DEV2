package vue;

import vue.compte.CadreCompte;
import vue.connexion.CadreConnexion;

/**
 * Gestionnaire de la vue. Initialise les cadres et d�termine quel cadre est actif.
 * 
 * @author Fr�d�ric simard | ETS
 * @version H2025
 * @revision et commentaire Pierre B�lisle
 *
 */
public class GestionnaireVue implements Runnable{
	
	CadreConnexion cadreConnexion; 
	CadreCompte cadreCompte;
	
	@Override
	public void run() {
		// instantie les cadres
		cadreConnexion = new CadreConnexion(this);
		cadreCompte = new CadreCompte(this);
		
		activerModeConnexion();
	}
	
	/**
	 * m�thode pour activer le cadre de connexion
	 */
	public void activerModeConnexion() {

		cadreCompte.setVisible(false);
		cadreConnexion.setVisible(true);
	}

	/**
	 * m�thode pour activer le cadre de compte
	 */
	public void activerModeCompte() {

		cadreConnexion.setVisible(false);
		cadreCompte.setVisible(true);
	}
	
	/**
	 * m�thode pour obtenir une r�f�rence au cadre compte
	 * @return r�f�rence au cadre compte
	 */
	public CadreCompte getCadreCompte() {
		return cadreCompte;
	}

}
