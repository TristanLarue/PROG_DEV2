package baseDonnees.utils;

import baseDonnees.BaseDonnees;
import baseDonnees.modeles.Transaction;
import baseDonnees.modeles.Utilisateur;

/**
 * Module utilitaire qui permet d'initialiser la base de donn�es
 *
 * @author Fr�d�ric simard | ETS
 * @version A2020
 * @revision et commentaire Pierre B�lisle
 */
public class SeedDB {

    /**
     * M�thode permettant d'ajouter les donn�es � la base de donn�es
     *
     * @param bd
     */
    public static void seed(BaseDonnees bd) {
        // les utilisateurs d'abord
        ajouterLesUtilisateurs(bd);
        // les transactions ensuite
        ajouterLesTransactions(bd);
    }

    /**
     * M�thode permettant d'ajouter les utilisateurs
     *
     * @param bd
     */
    private static void ajouterLesUtilisateurs(BaseDonnees bd) {

        // Table d'information
        Object[][] utilisateurs = {
                {"Fred", "fred12", "00-150-001", new Double(1500.0)},
                {"o", "o", "00-150-002", new Double(1500.0)},
                {"Jimmy Parent", "jim12", "00-150-003", new Double(1500.0)},
                {"Claudia Marcy", "clau12", "00-150-004", new Double(1500.0)},
                {"Lily Jacques", "lil12", "00-150-005", new Double(1500.0)}
        };

        // ajoute tous les utilisateurs
        for (Object[] utilisateur : utilisateurs) {
            bd.ajouterUtilisateur(new Utilisateur((String) utilisateur[0],
                    (String) utilisateur[1],
                    (String) utilisateur[2],
                    (double) utilisateur[3]));
        }

    }

    /**
     * M�thode permettant d'ajouter les transactions
     *
     * @param bd
     */
    private static void ajouterLesTransactions(BaseDonnees bd) {

        // Table d'information
        Object[][] transactions = {
                {"00-150-001", "00-150-002", new Double(150.0), Transaction.ACCEPTE},
                {"00-150-003", "00-150-002", new Double(151.0), Transaction.ACCEPTE},
                {"00-150-005", "00-150-002", new Double(152.0), Transaction.ACCEPTE},
                {"00-150-001", "00-150-004", new Double(153.0), Transaction.ACCEPTE},
                {"00-150-003", "00-150-004", new Double(154.0), Transaction.ACCEPTE},
                {"00-150-005", "00-150-004", new Double(155.0), Transaction.ACCEPTE},
                {"00-150-003", "00-150-001", new Double(156.0), Transaction.ACCEPTE},
                {"00-150-005", "00-150-001", new Double(157.0), Transaction.ACCEPTE}
        };

        // ajoute toutes les transactions
        for (Object[] transaction : transactions) {
            bd.ajouterTransaction(new Transaction((String) transaction[0],
                    (String) transaction[1],
                    (double) transaction[2],
                    (String) transaction[3]));
        }
    }
}
