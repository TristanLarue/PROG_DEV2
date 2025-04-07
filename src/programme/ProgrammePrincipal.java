package programme;

import javax.swing.SwingUtilities;

import vue.GestionnaireVue;

public class ProgrammePrincipal {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new GestionnaireVue());
	}
}
