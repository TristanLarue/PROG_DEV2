package vue.compte;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import baseDonnees.modeles.Transaction;
import baseDonnees.modeles.Utilisateur;
import modele.Banque;
import vue.GestionnaireVue;

public class CadreCompte extends JFrame implements ActionListener, observer.MonObserver {
    private static final long serialVersionUID = 1L;
    private GestionnaireVue gestionnaireVue;
    private JButton nouvelleTransactionButton;
    private JButton deconnexionButton;
    private JLabel infoLabel;
    private String name;
    private String numeroCompte;
    private Double solde;
    Color vertPale = new Color(121, 176, 136);
    Color vertFonce = new Color(42, 71, 50);
    Utilisateur utilisateurActif = null;
    Banque banque = Banque.getInstance();
    private DefaultTableModel[] tableModel = {null};

    public CadreCompte(GestionnaireVue gestionnaireVue) {
        this.gestionnaireVue = gestionnaireVue;
        banque.attacherObserver(this);

        setTitle("Compte Bancaire");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        Container window = getContentPane();
        window.setLayout(new GridBagLayout());
        window.setBackground(vertPale);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;


        BufferedImage img = null;
        ImageIcon scaledIcon = null;
        /* SECTION IMAGE PANEL TOP-LEFT*/
        try {
            img = ImageIO.read(new File("src/res/dollar.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (img != null) {
            ImageIcon originalIcon = new ImageIcon(img);
            Image originalImg = originalIcon.getImage();
            Image scaledImage = originalImg.getScaledInstance(
                    80,
                    (originalIcon.getIconHeight() * 80) / originalIcon.getIconWidth(),
                    Image.SCALE_SMOOTH
            );
            scaledIcon = new ImageIcon(scaledImage);
        }

        ImageIcon finalScaledIcon = scaledIcon; // obligatoire pour acceder a scaledIcon lorsqu'a l'interieur du override.
        JPanel imagePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalScaledIcon != null) {
                    int imgWidth = finalScaledIcon.getIconWidth();
                    int imgHeight = finalScaledIcon.getIconHeight();
                    int x = (getWidth() - imgWidth) / 2;
                    int y = (getHeight() - imgHeight) / 2;
                    g.drawImage(finalScaledIcon.getImage(), x, y, imgWidth, imgHeight, this);
                }
            }
        };
        imagePanel.setBackground(vertPale);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.33;
        window.add(imagePanel, gbc);
        /* FIN SECTION IMAGE PANEL */

        // panneau Middle-Left
        JPanel panelML = new JPanel();
        panelML.setBackground(vertPale);
        panelML.setLayout(new BoxLayout(panelML, BoxLayout.Y_AXIS));

        nouvelleTransactionButton = new JButton("Nouvelle Transaction");
        nouvelleTransactionButton.addActionListener(this);
        deconnexionButton = new JButton("Deconnexion");
        deconnexionButton.addActionListener(this);
        nouvelleTransactionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deconnexionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelML.add(nouvelleTransactionButton);
        panelML.add(deconnexionButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.33;
        window.add(panelML, gbc);

        // panneau Bottom-Left
        JPanel panelBL = new JPanel();
        panelBL.setBackground(vertPale);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.33;
        window.add(panelBL, gbc);

        // panneau Top-Right
        JPanel panelTR = new JPanel();
        panelTR.setBackground(vertPale);
        panelTR.setLayout(new BorderLayout());

        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        panelTR.add(infoLabel, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.33;
        window.add(panelTR, gbc);

        /* SECTION TABLE PANEL */
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 2;
        gbc.weightx = 0.8;
        gbc.weighty = 0.66;
        /* Puisque java ne supporte pas l'approche "pass-by-reference" de C, il faut utiliser un tableau
        pour atteindre le meme effet qu'un pass-by-reference. Ceci est la meilleure facon que j'ai trouve
        de changer tableModel dans la helper method */
        window.add(createTable(tableModel), gbc);
        /* FIN SECTION TABLE PANEL */

        // A chaque fois que la fenetre devient visible, executer le code pour mettre a jour la table et les infos et l'utilisateur actif
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateInfoLabel();
                updateTable(tableModel);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nouvelleTransactionButton) {
            // Ouvrir la fenêtre de dialogue pour une nouvelle transaction
            DialogueTransaction dialogue = new DialogueTransaction(this);
            dialogue.setVisible(true);
        } else if (e.getSource() == deconnexionButton) {
            banque.deconnecterUtilisateur();
            gestionnaireVue.activerModeConnexion();
        }
    }

    private void updateInfoLabel() {
        utilisateurActif = banque.getUtilisateurActif();
        if (utilisateurActif != null) {
            this.name = utilisateurActif.getNomUtilisateur();
            this.numeroCompte = utilisateurActif.getNumeroDeCompte();
            this.solde = utilisateurActif.getSolde();
        } else {
            System.out.println("ERREUR, INCAPABLE DE RETROUVER L'UTILISATEUR ACTIF");
        }

        String htmlText = "<html><div style='text-align: start;'><span style='font-size: 14px;font-weight: 900;'>" +
                this.name + "</span><br>" +
                "Numero de compte: " + numeroCompte + "<br>" +
                "Solde: " + this.solde + "$</div></html>";
        infoLabel.setText(htmlText);
    }

    private JPanel createTable(DefaultTableModel[] tableModel) {
        JPanel panelBR = new JPanel();
        panelBR.setLayout(new BorderLayout());

        String[] columnNames = {"Source", "Destination", "Montant", "Status"};
        tableModel[0] = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 2:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        JTable table = new JTable(tableModel[0]) {
            // Bizzare, ceci est la seule facon j'ai trouve de faire en sorte que le tableau ne prend pas plus de place que permis
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(0, 0);
            }
        };

        // Alignement du texte a gauche comme dans l'image du TP
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setBackground(vertFonce);
        tableHeader.setForeground(Color.WHITE);

        // Permet de scroller s'il y a trop de donnees
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelBR.add(scrollPane, BorderLayout.CENTER);

        return panelBR;
    }

    private void emptyTable(DefaultTableModel[] tableModel) {
        tableModel[0].setRowCount(0);
    }

    private void updateTable(DefaultTableModel[] tableModel) {
        Vector<Transaction> transactions = banque.obtenirTransactionsPourCompte();

        emptyTable(tableModel);

        for (Transaction transaction : transactions) {
            String noCompteSource = transaction.getNoCompteSource();
            String noCompteDestination = transaction.getNoCompteDestination();
            double montant = transaction.getMontant();
            String status = transaction.getStatus();
            Object[] rowData = {noCompteSource, noCompteDestination, montant, status};
            
            tableModel[0].addRow(rowData);
        }
    }
    @Override
    public void avertir() {
        // Mise à jour des informations affichées
        updateInfoLabel();

        // Vérifier si l'utilisateur est connecté avant de mettre à jour la table
        if (banque.estConnecte()) {
            updateTable(tableModel);
        }
    }
}