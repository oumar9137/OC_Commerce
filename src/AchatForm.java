/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oumar camara
 */
import java.sql.*;
import javax.swing.JOptionPane;
public class AchatForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AchatForm.class.getName());

    /**
     * Creates new form AchatForm
     */
    public AchatForm() {
        initComponents();
        setTitle("OC Commerce — Approvisionnements");
        if(ThemeOC.iconeFenetre()!=null)setIconImage(ThemeOC.iconeFenetre());
        ThemeOC.styliserTable(tableAchat);
        chargerComboProduits();
        chargerComboFournisseurs();
        chargerProduits();
        chargerFournisseurs();
        chargerTableAchats();
        chargerAchats();
        setLocationRelativeTo(null);
    }
    
   public void chargerTableAchats() {
 try {
 Connection con = ConnexionBD.getConnection();
 String sql = "SELECT da.id_achat, a.date_achat, CONCAT(f.nom,' ',f.prenom) as fournisseur, p.designation, da.quantite, da.prix_achat " +
 "FROM detail_achat da " +
 "JOIN achat a ON da.id_achat = a.id_achat " +
 "JOIN produit p ON da.id_produit = p.id_produit " +
 "JOIN fournisseur f ON a.id_fournisseur = f.id_fournisseur " +
 "ORDER BY a.date_achat DESC";
 
 java.sql.Statement st = con.createStatement();
 java.sql.ResultSet rs = st.executeQuery(sql);
 javax.swing.table.DefaultTableModel model =
(javax.swing.table.DefaultTableModel) tableAchat.getModel();
 model.setRowCount(0);
 while(rs.next()){
 model.addRow(new Object[]{
 rs.getInt("id_achat"),
 rs.getDate("date_achat"),
 rs.getString("fournisseur"),
 rs.getString("designation"),
 rs.getInt("quantite"),
 rs.getDouble("prix_achat")
 });
 }
 } catch(Exception e){
 System.out.println("Erreur table: " + e.getMessage());
 }

}
    
    // Charge tous les produits dans le combo
public void chargerComboProduits() {
 try {
 comboProduit.removeAllItems(); // vide le combo
 Connection con = ConnexionBD.getConnection();
 String sql = "SELECT id_produit, designation FROM produit ORDER BY designation,id_produit";
 Statement st = con.createStatement();
 ResultSet rs = st.executeQuery(sql);
 while (rs.next()) {
 comboProduit.addItem(rs.getString("designation")+" [#"+rs.getInt("id_produit")+"]");
 }
 } catch (Exception e) {
 JOptionPane.showMessageDialog(this, "Erreur combo produit: " +
e.getMessage());
 }
}
// Pour récupérer l'ID à partir du nom
public int getIdProduit(String designation) {
 return lireIdSelection(designation);
}
public void chargerComboFournisseurs() {
 try {
 comboFournisseur.removeAllItems();
 Connection con = ConnexionBD.getConnection();
 // On concatène nom et prénom pour l'affichage
 String sql = "SELECT id_fournisseur, CONCAT(nom, ' ', prenom) AS nom_complet FROM fournisseur ORDER BY nom,prenom,id_fournisseur";
 ResultSet rs = con.createStatement().executeQuery(sql);
 while(rs.next()){
 comboFournisseur.addItem(rs.getString("nom_complet")+" [#"+rs.getInt("id_fournisseur")+"]");
 }
 } catch(Exception e){
 System.out.println(e.getMessage());
 }
}
public int getIdFournisseur(String nomComplet) {
 return lireIdSelection(nomComplet);
}
private int lireIdSelection(String valeur){if(valeur==null)return -1;int debut=valeur.lastIndexOf("[#"),fin=valeur.lastIndexOf(']');if(debut<0||fin<=debut+2)return -1;try{return Integer.parseInt(valeur.substring(debut+2,fin));}catch(NumberFormatException e){return -1;}}
    void chargerProduits(){
 try{
 comboProduit.removeAllItems();
 Connection con = ConnexionBD.getConnection();
 if(con == null){
 JOptionPane.showMessageDialog(this, "Connexion échouée !");
 return;
 }
 // Ta colonne s'appelle designation
 String sql = "SELECT id_produit,designation FROM produit ORDER BY designation,id_produit";
 ResultSet rs = con.createStatement().executeQuery(sql);
 while(rs.next()){
 comboProduit.addItem(rs.getString("designation")+" [#"+rs.getInt("id_produit")+"]");
 }
 }catch(Exception e){
 JOptionPane.showMessageDialog(this, "Erreur chargerProduits: "+e.getMessage());
 }
}
void chargerFournisseurs(){
 try{
 comboFournisseur.removeAllItems();
 Connection con = ConnexionBD.getConnection();
 if(con == null){
 JOptionPane.showMessageDialog(this, "Connexion échouée !");
 return;
 }
 String sql = "SELECT id_fournisseur,nom,prenom FROM fournisseur ORDER BY nom,prenom,id_fournisseur";
 ResultSet rs = con.createStatement().executeQuery(sql);
 while(rs.next()){
 // On affiche Nom + Prénom ensemble
 String nomComplet = rs.getString("nom") + " " + rs.getString("prenom");
 comboFournisseur.addItem(nomComplet+" [#"+rs.getInt("id_fournisseur")+"]");
 }
 }catch(Exception e){
 JOptionPane.showMessageDialog(this, "Erreur chargerFournisseurs:"+e.getMessage());
 }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        comboProduit = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboFournisseur = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtQuantite = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPrix = new javax.swing.JTextField();
        btnAcheter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAchat = new javax.swing.JTable();
        btnPlusProduit = new javax.swing.JButton();
        btnPlusFournisseur = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Produit");

        jLabel2.setText("Fournisseur");

        jLabel3.setText("Quantité");

        jLabel4.setText("Prix");

        btnAcheter.setText("Acheter");
        btnAcheter.addActionListener(this::btnAcheterActionPerformed);

        tableAchat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Achat", "Date", "Fournisseur", "Produit", "Quantité", "Prix"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableAchat);

        btnPlusProduit.setText("+");
        btnPlusProduit.addActionListener(this::btnPlusProduitActionPerformed);

        btnPlusFournisseur.setText("+");
        btnPlusFournisseur.addActionListener(this::btnPlusFournisseurActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAcheter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboProduit, 0, 88, Short.MAX_VALUE)
                            .addComponent(comboFournisseur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQuantite)
                            .addComponent(txtPrix))))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPlusProduit, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnPlusFournisseur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPlusProduit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboFournisseur, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPlusFournisseur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtQuantite, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrix, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(btnAcheter, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcheterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcheterActionPerformed
        // TODO add your handling code here:
        
        String produit=(String)comboProduit.getSelectedItem(),fournisseur=(String)comboFournisseur.getSelectedItem();
        int idProduit=getIdProduit(produit),idFournisseur=getIdFournisseur(fournisseur),idUtilisateur=SessionUtilisateur.getIdUtilisateur();
        final int quantite;final double prix;
        try{quantite=Integer.parseInt(txtQuantite.getText().trim());prix=Double.parseDouble(txtPrix.getText().trim().replace(',','.'));}
        catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Entrez une quantité entière et un prix valides.");return;}
        if(idProduit<1||idFournisseur<1){JOptionPane.showMessageDialog(this,"Choisissez un produit et un fournisseur valides.");return;}
        if(quantite<1||!Double.isFinite(prix)||prix<=0){JOptionPane.showMessageDialog(this,"La quantité et le prix d'achat doivent être supérieurs à zéro.");return;}
        if(idUtilisateur<1){JOptionPane.showMessageDialog(this,"Votre session a expiré. Reconnectez-vous.");return;}
        try(Connection con=ConnexionBD.getConnection()){
            if(con==null)throw new SQLException("Connexion à la base indisponible.");con.setAutoCommit(false);
            try{
                try(PreparedStatement p=con.prepareStatement("INSERT INTO achat(date_achat,id_fournisseur,id_utilisateur) VALUES(NOW(),?,?)",Statement.RETURN_GENERATED_KEYS)){
                    p.setInt(1,idFournisseur);p.setInt(2,idUtilisateur);p.executeUpdate();try(ResultSet keys=p.getGeneratedKeys()){if(!keys.next())throw new SQLException("Impossible de créer l'achat.");int idAchat=keys.getInt(1);
                        try(PreparedStatement d=con.prepareStatement("INSERT INTO detail_achat(id_achat,id_produit,quantite,prix_achat) VALUES(?,?,?,?)")){d.setInt(1,idAchat);d.setInt(2,idProduit);d.setInt(3,quantite);d.setDouble(4,prix);d.executeUpdate();}}
                }
                try(PreparedStatement p=con.prepareStatement("UPDATE produit SET stock=stock+?,prix_achat=? WHERE id_produit=?")){p.setInt(1,quantite);p.setDouble(2,prix);p.setInt(3,idProduit);if(p.executeUpdate()!=1)throw new SQLException("Le produit n'existe plus.");}
                con.commit();
            }catch(Exception ex){con.rollback();throw ex;}
        }catch(Exception ex){JOptionPane.showMessageDialog(this,"Achat non enregistré : "+ex.getMessage(),"Achat",JOptionPane.ERROR_MESSAGE);return;}
        JOptionPane.showMessageDialog(this,"Achat enregistré !");chargerTableAchats();txtQuantite.setText("");txtPrix.setText("");

    }//GEN-LAST:event_btnAcheterActionPerformed

    private void btnPlusProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusProduitActionPerformed
        // TODO add your handling code here:
        
 // Petite fenêtre pour créer un produit rapide
 javax.swing.JTextField txtDes = new javax.swing.JTextField();
 javax.swing.JTextField txtPrixA = new javax.swing.JTextField();
 javax.swing.JTextField txtPrixV = new javax.swing.JTextField();
 
 Object[] message = {
 "Désignation:", txtDes,
 "Prix Achat:", txtPrixA,
 "Prix Vente:", txtPrixV
 };
 int option = JOptionPane.showConfirmDialog(this, message, "Nouveau Produit Rapide", JOptionPane.OK_CANCEL_OPTION);
 if (option == JOptionPane.OK_OPTION) {
 try {
 String designation = txtDes.getText();
 double prixA = Double.parseDouble(txtPrixA.getText());
 double prixV = Double.parseDouble(txtPrixV.getText());
 
 if(designation.isEmpty()){
 JOptionPane.showMessageDialog(this, "Donne un nom!");
 return;
 }
 Connection con = ConnexionBD.getConnection();
 // On met stock 0, seuil 5, catégorie 1 par défaut (tu pourras modifier après dans ProduitForm)
 String sql = "INSERT INTO produit(designation, prix_achat, prix_vente, stock, seuil_minimal, id_categorie) VALUES(?,?,?,?,?,1)";
 PreparedStatement pst = con.prepareStatement(sql);
 pst.setString(1, designation);
 pst.setDouble(2, prixA);
 pst.setDouble(3, prixV);
 pst.setInt(4, 0);
 pst.setInt(5, 5);
 pst.executeUpdate();
 
 JOptionPane.showMessageDialog(this, "Produit créé ! Tu peux maintenant l'acheter.");
 
 // IMPORTANT : On recharge le comboProduit pour qu'il apparaisse  chargerComboProduits(); // utilise ta même méthode que tu as déjà
 chargerComboProduits();
 // On le sélectionne directement
 comboProduit.setSelectedItem(designation);
 
 } catch(Exception e){
 JOptionPane.showMessageDialog(this, "Erreur: "+e.getMessage());
 }
 } 
    }//GEN-LAST:event_btnPlusProduitActionPerformed

    private void btnPlusFournisseurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusFournisseurActionPerformed
        // TODO add your handling code here:
        
 
 javax.swing.JTextField txtNom = new javax.swing.JTextField();
 javax.swing.JTextField txtPrenom = new javax.swing.JTextField();
 javax.swing.JTextField txtTel = new javax.swing.JTextField();
 
 Object[] message = {
 "Nom:", txtNom,
 "Prénom:", txtPrenom,
 "Téléphone:", txtTel
 };
 int option = JOptionPane.showConfirmDialog(this, message, "Nouveau Fournisseur", JOptionPane.OK_CANCEL_OPTION);
 if (option == JOptionPane.OK_OPTION) {
 try {
 String nom = txtNom.getText();
 String prenom = txtPrenom.getText();
 String tel = txtTel.getText();
 
 if(nom.isEmpty() || prenom.isEmpty()){
 JOptionPane.showMessageDialog(this, "Nom et prénom obligatoires!");
 return;
 }
 Connection con = ConnexionBD.getConnection();
 // On insère bien les 3 colonnes
 String sql = "INSERT INTO fournisseur(nom, prenom, telephone) VALUES(?,?,?)";
 PreparedStatement pst = con.prepareStatement(sql);
 pst.setString(1, nom);
 pst.setString(2, prenom);
 pst.setString(3, tel);
 pst.executeUpdate();
 
 JOptionPane.showMessageDialog(this, "Fournisseur créé !");
 
 chargerComboFournisseurs();
 comboFournisseur.setSelectedItem(nom + " " + prenom);
 
 } catch(Exception e){
 JOptionPane.showMessageDialog(this, "Erreur: "+e.getMessage());
 }
 }


    }//GEN-LAST:event_btnPlusFournisseurActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AchatForm().setVisible(true));
    }
    
    
void chargerAchats(){
 try{
 Connection con = ConnexionBD.getConnection();
 String sql = "SELECT a.id_achat, a.date_achat, CONCAT(f.nom,' ',f.prenom) as fournisseur, p.designation, d.quantite, d.prix_achat " + "FROM achat a " + "JOIN detail_achat d ON a.id_achat = d.id_achat "  + "JOIN fournisseur f ON a.id_fournisseur = f.id_fournisseur " + "JOIN produit p ON d.id_produit = p.id_produit ORDER BY a.id_achat DESC";
 ResultSet rs = con.createStatement().executeQuery(sql);
 javax.swing.table.DefaultTableModel model =
(javax.swing.table.DefaultTableModel) tableAchat.getModel();
 model.setRowCount(0);
 while(rs.next()){
 model.addRow(new Object[]{
 rs.getInt("id_achat"),
 rs.getString("date_achat"),
 rs.getString("fournisseur"),
 rs.getString("designation"),
 rs.getInt("quantite"),
 rs.getDouble("prix_achat")
 });
 }
 }catch(Exception e){
 JOptionPane.showMessageDialog(this, "Erreur chargement: "+e.getMessage());
 }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcheter;
    private javax.swing.JButton btnPlusFournisseur;
    private javax.swing.JButton btnPlusProduit;
    private javax.swing.JComboBox<String> comboFournisseur;
    private javax.swing.JComboBox<String> comboProduit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAchat;
    private javax.swing.JTextField txtPrix;
    private javax.swing.JTextField txtQuantite;
    // End of variables declaration//GEN-END:variables
}
