import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/** Gestion des comptes accessible aux administrateurs. */
public class UtilisateurForm extends JFrame {
    private final JTextField nom = new JTextField(18);
    private final JPasswordField motDePasse = new JPasswordField(18);
    private final JComboBox<String> profil = new JComboBox<>(new String[]{"ADMIN", "VENDEUR", "GESTIONNAIRE"});
    private final DefaultTableModel modele = new DefaultTableModel(new Object[]{"ID", "Nom d'utilisateur", "Profil"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(modele);
    private Integer idSelectionne;

    public static void ouvrir() {
        if (!"ADMIN".equalsIgnoreCase(SessionUtilisateur.getProfil())) {
            JOptionPane.showMessageDialog(null, "Cette page est réservée à l'administrateur.");
            return;
        }
        new UtilisateurForm().setVisible(true);
    }

    public UtilisateurForm() {
        setTitle("OC Commerce — Gestion des utilisateurs");
        if (ThemeOC.iconeFenetre() != null) setIconImage(ThemeOC.iconeFenetre());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(700, 430));
        JPanel formulaire = new JPanel(new GridLayout(3, 2, 8, 8));
        formulaire.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        formulaire.add(new JLabel("Nom d'utilisateur")); formulaire.add(nom);
        formulaire.add(new JLabel("Mot de passe (8 caractères minimum)")); formulaire.add(motDePasse);
        formulaire.add(new JLabel("Profil")); formulaire.add(profil);

        JButton enregistrer = new JButton("Créer le compte / Enregistrer");
        JButton nouveau = new JButton("Nouveau");
        JButton supprimer = new JButton("Supprimer le compte");
        JButton actualiser = new JButton("Actualiser");
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(enregistrer); actions.add(nouveau); actions.add(supprimer); actions.add(actualiser);
        JPanel haut = new JPanel(new BorderLayout()); haut.add(formulaire, BorderLayout.CENTER); haut.add(actions, BorderLayout.SOUTH);
        table.setRowHeight(25); table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.removeColumn(table.getColumnModel().getColumn(0));
        add(haut, BorderLayout.NORTH); add(new JScrollPane(table), BorderLayout.CENTER);
        enregistrer.addActionListener(e -> enregistrer());
        nouveau.addActionListener(e -> nouveau());
        supprimer.addActionListener(e -> supprimer());
        actualiser.addActionListener(e -> charger());
        table.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) selectionnerLigne(); });
        charger(); pack(); setLocationRelativeTo(null);
    }

    private void enregistrer() {
        String identifiant = nom.getText().trim();
        char[] secret = motDePasse.getPassword();
        if (identifiant.length() < 3 || identifiant.length() > 100) {
            JOptionPane.showMessageDialog(this, "Le nom d'utilisateur doit contenir entre 3 et 100 caractères."); return;
        }
        if (idSelectionne == null && secret.length < 8) {
            java.util.Arrays.fill(secret, '\0'); JOptionPane.showMessageDialog(this, "Un nouveau compte doit avoir un mot de passe d'au moins 8 caractères."); return;
        }
        if (secret.length > 0 && secret.length < 8) {
            java.util.Arrays.fill(secret, '\0'); JOptionPane.showMessageDialog(this, "Le nouveau mot de passe doit contenir au moins 8 caractères."); return;
        }
        String role = String.valueOf(profil.getSelectedItem());
        try (Connection con = ConnexionBD.getConnection()) {
            if (con == null) throw new SQLException("Connexion à la base indisponible.");
            if (idSelectionne == null) {
                try (PreparedStatement p = con.prepareStatement("INSERT INTO utilisateur(nom_utilisateur,mot_de_passe,profil) VALUES(?,?,?)")) {
                    p.setString(1, identifiant); p.setString(2, MotDePasseUtil.hacher(secret)); p.setString(3, role); p.executeUpdate();
                }
            } else {
                if (idSelectionne == SessionUtilisateur.getIdUtilisateur() && !"ADMIN".equals(role)) {
                    JOptionPane.showMessageDialog(this, "Vous ne pouvez pas retirer votre propre rôle administrateur."); return;
                }
                if (compterAdmins(con) <= 1 && estAdmin(con, idSelectionne) && !"ADMIN".equals(role)) {
                    JOptionPane.showMessageDialog(this, "Il faut conserver au moins un administrateur."); return;
                }
                String sql = secret.length > 0
                        ? "UPDATE utilisateur SET nom_utilisateur=?,profil=?,mot_de_passe=? WHERE id_utilisateur=?"
                        : "UPDATE utilisateur SET nom_utilisateur=?,profil=? WHERE id_utilisateur=?";
                try (PreparedStatement p = con.prepareStatement(sql)) {
                    p.setString(1, identifiant); p.setString(2, role);
                    if (secret.length > 0) { p.setString(3, MotDePasseUtil.hacher(secret)); p.setInt(4, idSelectionne); }
                    else p.setInt(3, idSelectionne);
                    p.executeUpdate();
                }
                if (idSelectionne == SessionUtilisateur.getIdUtilisateur()) SessionUtilisateur.ouvrir(idSelectionne, identifiant, role);
            }
            JOptionPane.showMessageDialog(this, idSelectionne == null ? "Compte créé." : "Compte mis à jour.");
            nouveau(); charger();
        } catch (SQLException ex) {
            String detail = ex.getErrorCode() == 1062 ? "Ce nom d'utilisateur existe déjà." : ex.getMessage();
            JOptionPane.showMessageDialog(this, "Impossible d'enregistrer le compte : " + detail, "Utilisateurs", JOptionPane.ERROR_MESSAGE);
        } finally {
            java.util.Arrays.fill(secret, '\0');
            motDePasse.setText("");
        }
    }

    private void supprimer() {
        if (idSelectionne == null) { JOptionPane.showMessageDialog(this, "Sélectionnez un compte."); return; }
        if (idSelectionne == SessionUtilisateur.getIdUtilisateur()) { JOptionPane.showMessageDialog(this, "Vous ne pouvez pas supprimer le compte actuellement connecté."); return; }
        try (Connection con = ConnexionBD.getConnection()) {
            if (con == null) throw new SQLException("Connexion à la base indisponible.");
            if (compterAdmins(con) <= 1 && estAdmin(con, idSelectionne)) { JOptionPane.showMessageDialog(this, "Il faut conserver au moins un administrateur."); return; }
            if (JOptionPane.showConfirmDialog(this, "Supprimer définitivement ce compte ?", "Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
            try (PreparedStatement p = con.prepareStatement("DELETE FROM utilisateur WHERE id_utilisateur=?")) { p.setInt(1, idSelectionne); p.executeUpdate(); }
            nouveau(); charger();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Ce compte est peut-être lié à des opérations enregistrées. Il n'a pas été supprimé.\n" + ex.getMessage(), "Utilisateurs", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int compterAdmins(Connection con) throws SQLException {
        try (Statement s = con.createStatement(); ResultSet r = s.executeQuery("SELECT COUNT(*) FROM utilisateur WHERE UPPER(profil)='ADMIN'")) { return r.next() ? r.getInt(1) : 0; }
    }

    private boolean estAdmin(Connection con, int id) throws SQLException {
        try (PreparedStatement p = con.prepareStatement("SELECT UPPER(profil)='ADMIN' FROM utilisateur WHERE id_utilisateur=?")) {
            p.setInt(1, id); try (ResultSet r = p.executeQuery()) { return r.next() && r.getBoolean(1); }
        }
    }

    private void charger() {
        modele.setRowCount(0);
        try (Connection con = ConnexionBD.getConnection(); Statement s = con.createStatement();
             ResultSet r = s.executeQuery("SELECT id_utilisateur,nom_utilisateur,profil FROM utilisateur ORDER BY nom_utilisateur")) {
            while (r.next()) modele.addRow(new Object[]{r.getInt(1), r.getString(2), r.getString(3)});
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Erreur de chargement : " + ex.getMessage()); }
    }

    private void selectionnerLigne() {
        int row = table.getSelectedRow(); if (row < 0) return;
        idSelectionne = ((Number) modele.getValueAt(row, 0)).intValue();
        nom.setText(String.valueOf(modele.getValueAt(row, 1)));
        String role = String.valueOf(modele.getValueAt(row, 2)).toUpperCase(java.util.Locale.ROOT);
        profil.setSelectedItem(java.util.Arrays.asList("ADMIN", "VENDEUR", "GESTIONNAIRE").contains(role) ? role : "VENDEUR");
        motDePasse.setText("");
    }

    private void nouveau() { idSelectionne = null; table.clearSelection(); nom.setText(""); motDePasse.setText(""); profil.setSelectedItem("VENDEUR"); nom.requestFocusInWindow(); }
}
