
package proyectosoft;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProyectoSoft {
    public static void main(String[] args) throws InterruptedException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
//        Llamamos al JFrame Ingreso();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Ingreso().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
    }    
}
