package vue.connexion;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import modele.Banque;
import vue.GestionnaireVue;

/**
 * Panneau de connexion pour l'authentification d'utilisateur.
 * 
 * @author Tristan Larue | ETS
 * @version H2025
 *
 */

 public class CadreConnexion extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private GestionnaireVue gestionnaireVue;
    private JPanel panneauPrincipal;
    private JLabel lblTitre;
    private JLabel lblNomUtilisateur;
    private JTextField txtNomUtilisateur;
    private JLabel lblMotDePasse;
    private JPasswordField txtMotDePasse;
    private JButton btnConnexion;
    private JLabel lblMessageErreur;
    
    // Constructeur
    public CadreConnexion(GestionnaireVue gestionnaireVue) {
        this.gestionnaireVue = gestionnaireVue;
        
        // Config de la fenêtre
        setTitle("Connexion - Application Bancaire");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Mettre au centre!
        initialiserComposants();
    }
    //Initialiser le panneau
    private void initialiserComposants() {
        panneauPrincipal = new JPanel(new GridBagLayout());
        panneauPrincipal.setBackground(Color.DARK_GRAY);
        
        // Config des contrainte
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Config du titre
        lblTitre = new JLabel("Bienvenu dans la banque!");
        lblTitre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panneauPrincipal.add(lblTitre, gbc);
        
        // username
        lblNomUtilisateur = new JLabel("Nom dutilisateur: ");
        lblNomUtilisateur.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panneauPrincipal.add(lblNomUtilisateur, gbc);
        txtNomUtilisateur = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panneauPrincipal.add(txtNomUtilisateur, gbc);
        
        // password
        lblMotDePasse = new JLabel("Mot de passe:");
        lblMotDePasse.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panneauPrincipal.add(lblMotDePasse, gbc);
        txtMotDePasse = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panneauPrincipal.add(txtMotDePasse, gbc);
        
        // Bouton Connexion
        btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panneauPrincipal.add(btnConnexion, gbc);
        
        // Message d'erreur
        lblMessageErreur = new JLabel("Nom d'utilisateur ou mot de passe incorrect!");
        lblMessageErreur.setForeground(Color.RED);
        lblMessageErreur.setVisible(false); //Invisible!
        gbc.gridx = 0;
        gbc.gridy = 4;
        panneauPrincipal.add(lblMessageErreur, gbc);
        
        add(panneauPrincipal);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConnexion) {
            // get username
            String nomUtilisateur = txtNomUtilisateur.getText();

            // get password
            String motDePasse = new String(txtMotDePasse.getPassword());
            
            // Verif de connexion
            Banque banque = Banque.getInstance();
            boolean connexionReussie = banque.verifier(nomUtilisateur, motDePasse);
            
            if (connexionReussie) { //Tout reset
                banque.setUtilisateurActif(nomUtilisateur);
                txtNomUtilisateur.setText("");
                txtMotDePasse.setText("");
                lblMessageErreur.setVisible(false);
                gestionnaireVue.activerModeCompte();
            } else {
                // Sinon montrer l'erreur
                lblMessageErreur.setVisible(true);
            }
        }
    }
}