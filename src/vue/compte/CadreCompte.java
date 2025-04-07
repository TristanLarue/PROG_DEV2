package vue.compte;

import javax.swing.JFrame;
import vue.GestionnaireVue;

public class CadreCompte extends JFrame {
    private static final long serialVersionUID = 1L;
    
    public CadreCompte(GestionnaireVue gestionnaireVue) {
        setTitle("Compte Bancaire");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //AZIZ J'ai seulement fait la base de la fonction pour que je test ma partie, ceci n'est pas le vrai/bon code
    }
}