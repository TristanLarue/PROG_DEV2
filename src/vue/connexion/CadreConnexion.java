package vue.connexion;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import modele.Banque;
import vue.GestionnaireVue;

public class CadreConnexion extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private GestionnaireVue gestionnaireVue;
    private JPanel panneauPrincipal;
    private JLabel lblNomUtilisateur;
    private JTextField txtNomUtilisateur;
    private JLabel lblMotDePasse;
    private JPasswordField txtMotDePasse;
    private JButton btnConnexion;
    private JLabel lblMessageErreur;
    
    // Constructor
    public CadreConnexion(GestionnaireVue gestionnaireVue) {
        this.gestionnaireVue = gestionnaireVue;
        
        // fenetre
        setTitle("Connexion");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        initialiserComposants();
    }
    
    // Initialisation du panel
    private void initialiserComposants() {
        panneauPrincipal = new JPanel(new GridBagLayout());
        panneauPrincipal.setBackground(Color.DARK_GRAY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // username label
        lblNomUtilisateur = new JLabel("Nom utilisateur");
        lblNomUtilisateur.setForeground(Color.WHITE);
        lblNomUtilisateur.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panneauPrincipal.add(lblNomUtilisateur, gbc);
        
        // username zone
        txtNomUtilisateur = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panneauPrincipal.add(txtNomUtilisateur, gbc);
        
        // mdp label
        lblMotDePasse = new JLabel("Mot de passe");
        lblMotDePasse.setForeground(Color.WHITE);
        lblMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panneauPrincipal.add(lblMotDePasse, gbc);
        
        // mdp zone
        txtMotDePasse = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panneauPrincipal.add(txtMotDePasse, gbc);
        
        // Bouton submit
        btnConnexion = new JButton("Soumettre");
        btnConnexion.addActionListener(this);
        btnConnexion.setPreferredSize(new Dimension(100, btnConnexion.getPreferredSize().height));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panneauPrincipal.add(btnConnexion, gbc);
        
        // Message d'erreur
        lblMessageErreur = new JLabel("Accès refusé");
        lblMessageErreur.setForeground(Color.RED);
        lblMessageErreur.setHorizontalAlignment(SwingConstants.CENTER);
        lblMessageErreur.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panneauPrincipal.add(lblMessageErreur, gbc);
        
        add(panneauPrincipal);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConnexion) {
            String nomUtilisateur = txtNomUtilisateur.getText();
            String motDePasse = new String(txtMotDePasse.getPassword());
            Banque banque = Banque.getInstance();
            boolean connexionReussie = banque.verifier(nomUtilisateur, motDePasse);
            
            if (connexionReussie) {
                banque.setUtilisateurActif(nomUtilisateur);
                txtNomUtilisateur.setText("");
                txtMotDePasse.setText("");
                lblMessageErreur.setVisible(false);
                gestionnaireVue.activerModeCompte();
            } else {
                lblMessageErreur.setVisible(true);
            }
        }
    }
}
