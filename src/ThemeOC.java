import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/** Thème commun de l'application et présentation des tableaux. */
public final class ThemeOC {
    public static final Color BLEU = new Color(24, 76, 132);
    public static final Color BLEU_CLAIR = new Color(231, 241, 251);
    public static final Color FOND = new Color(246, 249, 253);
    public static final Color TEXTE = new Color(38, 49, 62);
    public static final Color ALERTE = new Color(255, 235, 235);

    private ThemeOC() { }

    public static void appliquer() {
        try {
            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(laf.getName())) { UIManager.setLookAndFeel(laf.getClassName()); break; }
            }
        } catch (Exception ignored) { }
        UIManager.put("control", FOND);
        UIManager.put("text", TEXTE);
        UIManager.put("nimbusBase", BLEU);
        UIManager.put("nimbusBlueGrey", new Color(216, 227, 240));
        UIManager.put("nimbusFocus", new Color(70, 145, 220));
        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.alternateRowColor", new Color(243, 247, 252));
        UIManager.put("Table.selectionBackground", BLEU);
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.gridColor", new Color(229, 235, 242));
        UIManager.put("Table.rowHeight", 27);
        UIManager.put("TableHeader.background", new Color(231, 239, 248));
        UIManager.put("TableHeader.foreground", BLEU);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 13));
    }

    public static void styliserTable(JTable table) {
        table.setRowHeight(27);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(229, 235, 242));
        table.setSelectionBackground(BLEU);
        table.setSelectionForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(BLEU);
        table.getTableHeader().setBackground(new Color(231, 239, 248));
        table.getTableHeader().setBorder(new LineBorder(new Color(216, 227, 240)));
        DefaultTableCellRenderer cellules = new DefaultTableCellRenderer() {
            @Override public java.awt.Component getTableCellRendererComponent(JTable t, Object value, boolean selected,
                    boolean focus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(t, value, selected, focus, row, column);
                if (!selected) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(243, 247, 252));
                c.setForeground(selected ? Color.WHITE : TEXTE);
                setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8));
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, cellules);
        table.setDefaultRenderer(String.class, cellules);
        table.setDefaultRenderer(Number.class, cellules);
    }

    public static ImageIcon logo(int largeur, int hauteur) {
        java.net.URL resource = ThemeOC.class.getResource("/assets/oc_commerce_logo.png");
        if (resource == null) return new ImageIcon();
        Image image = new ImageIcon(resource).getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static Image iconeFenetre() {
        java.net.URL resource = ThemeOC.class.getResource("/assets/oc_commerce_icon.png");
        return resource == null ? null : new ImageIcon(resource).getImage();
    }
}
