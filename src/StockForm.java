/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oumar camara
 */
import java.sql.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableCellRenderer;
public class StockForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StockForm.class.getName());
    private final javax.swing.JTextField txtRecherche = new javax.swing.JTextField(24);
    private final javax.swing.JLabel lblResume = new javax.swing.JLabel("Stock magasin");
    private TableRowSorter<DefaultTableModel> trieur;

    /**
     * Creates new form StockForm
     */
    public StockForm() {
        initComponents();
        setTitle("OC Commerce — État des stocks");
        if(ThemeOC.iconeFenetre()!=null)setIconImage(ThemeOC.iconeFenetre());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        DefaultTableModel model=(DefaultTableModel)tableStock.getModel();
        model.setColumnIdentifiers(new Object[]{"Produit","Catégorie","Stock actuel","Seuil d'alerte","État"});
        tableStock.setAutoCreateRowSorter(true);trieur=new TableRowSorter<>(model);tableStock.setRowSorter(trieur);
        ThemeOC.styliserTable(tableStock);
        DefaultTableCellRenderer alertRenderer=new DefaultTableCellRenderer(){
            @Override public Component getTableCellRendererComponent(javax.swing.JTable t,Object value,boolean selected,boolean focus,int row,int column){
                Component c=super.getTableCellRendererComponent(t,value,selected,focus,row,column);
                int modelRow=t.convertRowIndexToModel(row);boolean alert="À COMMANDER".equals(String.valueOf(t.getModel().getValueAt(modelRow,4)));
                if(!selected)c.setBackground(alert?ThemeOC.ALERTE:(row%2==0?Color.WHITE:new Color(243,247,252)));
                c.setForeground(selected?Color.WHITE:(alert?new Color(160,45,45):ThemeOC.TEXTE));setBorder(javax.swing.BorderFactory.createEmptyBorder(2,8,2,8));return c;
            }
        };
        tableStock.setDefaultRenderer(Object.class,alertRenderer);tableStock.setDefaultRenderer(String.class,alertRenderer);tableStock.setDefaultRenderer(Number.class,alertRenderer);
        JPanel filtre=new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));filtre.setBackground(ThemeOC.FOND);
        filtre.add(new JLabel("Rechercher :"));filtre.add(txtRecherche);filtre.add(lblResume);
        javax.swing.JButton actualiser=new javax.swing.JButton("Actualiser"),exporter=new javax.swing.JButton("Exporter CSV");filtre.add(actualiser);filtre.add(exporter);
        JPanel racine=new JPanel(new BorderLayout(8,8));racine.setBorder(javax.swing.BorderFactory.createEmptyBorder(12,12,12,12));racine.setBackground(ThemeOC.FOND);
        racine.add(filtre,BorderLayout.NORTH);racine.add(new javax.swing.JScrollPane(tableStock),BorderLayout.CENTER);setContentPane(racine);
        txtRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            private void filtrer(){String q=txtRecherche.getText().trim();trieur.setRowFilter(q.isEmpty()?null:javax.swing.RowFilter.regexFilter("(?i)"+java.util.regex.Pattern.quote(q),0,1,4));}
            public void insertUpdate(javax.swing.event.DocumentEvent e){filtrer();}public void removeUpdate(javax.swing.event.DocumentEvent e){filtrer();}public void changedUpdate(javax.swing.event.DocumentEvent e){filtrer();}
        });
        actualiser.addActionListener(e->chargerStock());exporter.addActionListener(e->exporterCSV());
        chargerStock();pack();setMinimumSize(new Dimension(760,460));setLocationRelativeTo(null);
    }

    private void chargerStock(){
        DefaultTableModel model=(DefaultTableModel)tableStock.getModel();model.setRowCount(0);
        String sql="SELECT p.designation,COALESCE(c.libelle_categorie,'Sans catégorie'),p.stock,p.seuil_minimal,CASE WHEN p.stock<=p.seuil_minimal THEN 'À COMMANDER' ELSE 'Disponible' END FROM produit p LEFT JOIN categorie c ON c.id_categorie=p.id_categorie ORDER BY (p.stock<=p.seuil_minimal) DESC,p.designation";
        try(Connection con=ConnexionBD.getConnection()){
            if(con==null)throw new SQLException("Connexion à la base indisponible.");
            try(PreparedStatement p=con.prepareStatement(sql);ResultSet r=p.executeQuery()){
                int alertes=0;while(r.next()){model.addRow(new Object[]{r.getString(1),r.getString(2),r.getInt(3),r.getInt(4),r.getString(5)});if("À COMMANDER".equals(r.getString(5)))alertes++;}
                lblResume.setText(model.getRowCount()+" référence(s)  •  "+alertes+" sous le seuil");
            }
        }catch(Exception e){JOptionPane.showMessageDialog(this,"Impossible de charger le stock : "+e.getMessage(),"Stock",JOptionPane.ERROR_MESSAGE);}
    }

    private void exporterCSV(){
        javax.swing.JFileChooser choix=new javax.swing.JFileChooser();choix.setSelectedFile(new java.io.File("etat_stock.csv"));
        if(choix.showSaveDialog(this)!=javax.swing.JFileChooser.APPROVE_OPTION)return;
        Path path=choix.getSelectedFile().toPath();if(!path.toString().toLowerCase(java.util.Locale.ROOT).endsWith(".csv"))path=Path.of(path+".csv");
        try(BufferedWriter out=Files.newBufferedWriter(path,StandardCharsets.UTF_8)){
            out.write("Produit;Catégorie;Stock actuel;Seuil d'alerte;État\n");
            for(int row=0;row<tableStock.getRowCount();row++){
                for(int col=0;col<tableStock.getColumnCount();col++){if(col>0)out.write(';');Object value=tableStock.getValueAt(row,col);String v=value==null?"":value.toString().replace("\"","\"\"");out.write("\""+v+"\"");}
                out.newLine();
            }
            JOptionPane.showMessageDialog(this,"État des stocks exporté : "+path);
        }catch(Exception e){JOptionPane.showMessageDialog(this,"Échec de l'export : "+e.getMessage(),"Export CSV",JOptionPane.ERROR_MESSAGE);}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableStock = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tableStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Produit", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableStock);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.awt.EventQueue.invokeLater(() -> new StockForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableStock;
    // End of variables declaration//GEN-END:variables
}
