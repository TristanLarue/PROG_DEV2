package vue.compte;

import modele.Banque;
import baseDonnees.modeles.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre modale pour saisir une nouvelle transaction.
 * Affiche deux champs (compte destination, montant) et deux boutons (confirmer, annuler).
 */
public class DialogueTransaction extends JDialog implements ActionListener {

    private JTextField txtCompteDestination;
    private JTextField txtMontant;
    private JButton btnConfirmer;
    private JButton btnAnnuler;
    private Banque banque;

    /**
     * Constructeur de la boîte de dialogue pour une transaction.
     *
     * @param parent le JFrame parent de la fenêtre
     */
    public DialogueTransaction(JFrame parent) {
        super(parent, "Nouvelle Transaction", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        banque = Banque.getInstance();

        initUI();

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Initialise les composants de l'interface graphique.
     */
    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblCompte = new JLabel("Compte destination (00-150-00X):");
        lblCompte.setFont(lblCompte.getFont().deriveFont(Font.BOLD));
        panel.add(lblCompte, gbc);

        gbc.gridy++;
        txtCompteDestination = new JTextField(20);
        panel.add(txtCompteDestination, gbc);

        gbc.gridy++;
        JLabel lblMontant = new JLabel("Montant:");
        lblMontant.setFont(lblMontant.getFont().deriveFont(Font.BOLD));
        panel.add(lblMontant, gbc);

        gbc.gridy++;
        txtMontant = new JTextField(20);
        panel.add(txtMontant, gbc);

        gbc.gridy++;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnAnnuler = new JButton("Annuler");
        btnConfirmer = new JButton("Confirmer");

        btnAnnuler.addActionListener(this);
        btnConfirmer.addActionListener(this);

        btnPanel.add(btnAnnuler);
        btnPanel.add(btnConfirmer);
        panel.add(btnPanel, gbc);

        setContentPane(panel);
    }

    /**
     * Gère les clics sur les boutons "Annuler" et "Confirmer".
     *
     * @param e l'événement déclenché
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnnuler) {
            dispose();
        } else if (e.getSource() == btnConfirmer) {
            soumettreTransaction();
        }
    }

    /**
     * Tente de créer et soumettre une transaction à la banque.
     * Affiche une boîte d'erreur en cas de problème.
     */
    private void soumettreTransaction() {
        try {
            String compteDest = txtCompteDestination.getText().trim();
            String montantStr = txtMontant.getText().trim();

            if (compteDest.isEmpty() || montantStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double montant = Double.parseDouble(montantStr);
            String compteSource = banque.getUtilisateurActif().getNumeroDeCompte();
            Transaction transaction = new Transaction(compteSource, compteDest, montant);

            banque.soumettreTransaction(transaction);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Montant invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
