
package proyectosoft;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;


public class imgTabla extends DefaultTableCellRenderer {

    /*Metodo auxiliar usado para cargar las imagenes en la tabla correctamente
    */
    
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JLabel) {
            JLabel lbl = (JLabel) value;
            return lbl;
        }

        if (value instanceof JButton) {
            JButton button = (JButton) value;
            if (isSelected) {
                button.setForeground(tabla.getSelectionForeground());
                button.setBackground(tabla.getSelectionBackground());
            } else {
                button.setForeground(tabla.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            return button;
        }

        return super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column); 
    }

}
