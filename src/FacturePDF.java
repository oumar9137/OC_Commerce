/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oumar camara
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.*;
public class FacturePDF {
    
     public static void generer(int idVente){
        try{
            Connection con = ConnexionBD.getConnection();
            // 1. Récupérer l'entête de la vente
            PreparedStatement pst = con.prepareStatement(
                "SELECT v.id_vente, v.date_vente, c.nom,c.prenom, c.telephone " +
                "FROM vente v LEFT JOIN client c ON v.id_client = c.id_client " +
                "WHERE v.id_vente = ?"
            );
            pst.setInt(1, idVente);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()) return;
            String date = rs.getString("date_vente");
            String client = rs.getString("nom");
         
            if(client==null) client = "Client Comptant";
            // 2. Créer le PDF
            String fileName = "Facture_Vente_"+idVente+".pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();
            // Logo / Titre
            Font titreFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titre = new Paragraph("OC COMMERCE - FACTURE DE VENTE\n\n", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            doc.add(titre);
            doc.add(new Paragraph("Facture N°: " + idVente));
            doc.add(new Paragraph("Date: " + date));
            String nomComplet = rs.getString("nom");
            try{ nomComplet += " "+ rs.getString("prenom");} catch(Exception e){}
           
            doc.add(new Paragraph("Client: " +nomComplet));
            
            doc.add(new Paragraph("\n"));
            // 3. Tableau des produits
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Produit");
            table.addCell("Quantité");
            table.addCell("Prix Unit.");
            table.addCell("Total");
            PreparedStatement pstDetail = con.prepareStatement(
                "SELECT p.designation, d.quantite, d.prix_unitaire, (d.quantite * d.prix_unitaire) as total " +
                "FROM detail_vente d JOIN produit p ON d.id_produit = p.id_produit " +
                "WHERE d.id_vente = ?"
            );
            pstDetail.setInt(1, idVente);
            ResultSet rsDetail = pstDetail.executeQuery();
            double grandTotal = 0;
            while(rsDetail.next()){
                table.addCell(rsDetail.getString("designation"));
                table.addCell(rsDetail.getString("quantite"));
                table.addCell(rsDetail.getString("prix_unitaire") + " FCFA");
                table.addCell(rsDetail.getString("total") + " FCFA");
                grandTotal += rsDetail.getDouble("total");
            }
            doc.add(table);
            doc.add(new Paragraph("\n"));
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph total = new Paragraph("TOTAL A PAYER: " + grandTotal + "FCFA", totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            doc.add(total);
            doc.add(new Paragraph("\n\nMerci pour votre confiance!"));
            doc.close();
            // Ouvrir le PDF automatiquement
            java.awt.Desktop.getDesktop().open(new java.io.File(fileName));
        }catch(Exception e){
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Erreur Facture: " +
e.getMessage());
        }
    }
    
}
