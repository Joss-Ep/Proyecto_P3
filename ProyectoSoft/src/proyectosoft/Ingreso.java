
package proyectosoft;

import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import static proyectosoft.Procedimientos.*;
import static proyectosoft.tiemporeal.*;


public class Ingreso extends javax.swing.JFrame implements ActionListener,Runnable {

    //procesos necesarios al iniciar la ventana
    public Ingreso() throws InterruptedException {
        Descargausuarios();//preparacion para comparar usuarios
        initComponents();//carga de todos los elementos de la interfaz
        //propiedades de la ventata
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("                                             Ingreso");
        
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
	@Override
	public void windowClosing(WindowEvent e) {
                // Se pide una confirmación antes de finalizar el programa
		int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea salir?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(a==JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
	}
});
        //logo de la ventana
        ImageIcon icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo1.png"));
        this.setIconImage(icono.getImage());
        //fondo usado para la ventana
        fondo.setLocation(0, 0);
        fondo.setSize(430, 310);
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoIngreso.jpg")));
        fondo.setVisible(true);
        this.add(fondo);
        //hilo para saber la hora del sistema
        Thread h1 = new Thread((Runnable) this);
        tiemporeal tiempo= new tiemporeal();
        correhora=false;
        tiempo.start();
        Thread.sleep(1000);
        comprsaludo();//usado para comprobar la hora para el saludo
    }

    public String Usuario[] = new String [50];//usado para descargar todos los nombres de los usarios de la base de datos
    public String Contraseña[] = new String [50];//usado para descargar todas las contraseñas de los usarios de la base de datos
    public String tipo[] = new String [50];//usado para descargar todas los tipos correspondientes a los usarios de la base de datos
    public int codigo[] = new int [50];//usado para descagar todos los codigos de los usuarios de la base de datos
    public int cont;//usado para contar la cantidad de usarios en la base de datos
    public JLabel fondo = new JLabel();//usado para colocar un fondo sin perjudicar la ubicacion de los demas objetos
    public static principal prin;//usado para llamar a la ventana como un objeto estatico
    public static String Nombreing;//usado para enviar a las siguientes ventanas el nombre del usuario
    public static String Tipoing;//usado para enviar a las siguientes ventanas el tipo de usuario que ingresa
    public static int Condigoing;//usado para enviar a las siguientes ventanas el codigo del usuario
    /*Usados para saber si el mouse esta dentro o no del JLabel
    */
    public boolean mousedentro1;
    public boolean mousedentro2;
    
    public static boolean correhora; //usado para que muestre la hora solo cuando se desee
   /*proceso usado para descargar todos los usuarios existentes
    */ 
  public void Descargausuarios(){
      try{
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    String sql="SELECT * FROM Usuario;";
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
    cont=0;
    while(rs.next())
    {
            codigo[cont]=rs.getInt(1);
            Usuario[cont]=rs.getString(2);
            Contraseña[cont]=rs.getString(3);
            tipo[cont]=rs.getString(4);
            cont++;
    }
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se logro descargar los usarios", "Atención", JOptionPane.ERROR_MESSAGE);
        }
  }
    
    /*Proceso usado para verificar las credenciales ingresadas
    */
  public boolean Verificación(){
      boolean aux=false;
      boolean aux2=false;
      String nom = UsuaField.getText();
      String contra = getMD5(new String(ContraField.getPassword()));
      /*Usuario[0]=nom;
      Contraseña[0]=contra;
      tipo[0]="Admin";
      cont=1;*/
      for(int i=0; i<cont;i++)
      {
          if(nom.equals(Usuario[i])){
              aux2=true;
              if(contra.equals(Contraseña[i])){
                  aux=true;
                  Tipoing=tipo[i];
                  Condigoing=codigo[i];
              }
              else{
                  JOptionPane.showMessageDialog(null, "Contraseña incorrecta, por favor llene de nuevo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                  ContraField.setText("");
                  ContraField.grabFocus();
              }
              i=cont;
          }
      }
      if(!aux2){
          JOptionPane.showMessageDialog(null, "Usuario incorrecto, por favor llene de nuevo los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
          ContraField.setText("");
          UsuaField.setText("");
          UsuaField.grabFocus();
      }
      return aux;
  }
  
  /*Preceso usado para elegir un saludo de acuerdo a la hora
   */
  public void comprsaludo(){
      String saludo="";
      if(horanum<=12 && ampm.equals("AM")){
          saludo="Buenos dias";
      }
      if(horanum<6 && ampm.equals("PM")){
          saludo="Buenas tardes";
      }
      if((horanum>=6 && ampm.equals("PM")) || (horanum<=6 && ampm.equals("AM"))){
          saludo="Buenas noches";
      }
      saludo=saludo+", por favor ingrese sus datos";
      
      SaludLabel.setText(saludo);
  }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        SaludLabel = new javax.swing.JLabel();
        UsuaLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        UsuaField = new javax.swing.JTextField();
        ContraField = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seguro Social de Salud \"EsSalud\"");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        SaludLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        UsuaLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        UsuaLabel.setText("Usuario:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Contraseña: ");

        UsuaField.setBackground(new java.awt.Color(88, 193, 231));
        UsuaField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        UsuaField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                UsuaFieldKeyReleased(evt);
            }
        });

        ContraField.setBackground(new java.awt.Color(88, 193, 231));
        ContraField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        ContraField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ContraFieldKeyReleased(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salidaN.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entradaN.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Iniciar Sesión");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Salir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(43, 43, 43)
                                    .addComponent(SaludLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                        .addComponent(UsuaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(53, 53, 53)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ContraField, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(UsuaField, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 53, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(SaludLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UsuaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UsuaLabel))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ContraField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        ContraField.setLocation(55, 60);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*Proceso para pasar al siguiente campo presionando alguna tecla en concreto
    */
    private void UsuaFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsuaFieldKeyReleased
       if(evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            ContraField.grabFocus();
        }
    }//GEN-LAST:event_UsuaFieldKeyReleased

    /*Proceso para realizar la verificacion y la continuacion del programa presionando alguan tecla en concreto
    */
    private void ContraFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ContraFieldKeyReleased
       if(evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            if(UsuaField.getText().equals("") || ContraField.equals(null))
            {
                UsuaField.grabFocus();
               JOptionPane.showMessageDialog(null, "Por favor llene todos los campos", "Atención", JOptionPane.WARNING_MESSAGE);
               
            }
            else{
                if(Verificación())
                    {
                        correhora=true;
                        Nombreing=UsuaField.getText();
                        prin = new principal();
                        prin.setTitle("");
                        prin.setVisible(true);
                        this.dispose();
                    }
            }
        }
       if(evt.getKeyChar()==KeyEvent.VK_TAB)
        {
            jLabel3.grabFocus();
        }
    }//GEN-LAST:event_ContraFieldKeyReleased

    /*Procesos para simular la accion de boton para un JLabel, ademas de dar la sensacion de una animacion extra XD(boton iniciar sesion)
    */
    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        mousedentro1=true;
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entradaA.png")));
    }//GEN-LAST:event_jLabel3MouseEntered
    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        mousedentro1=false;
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entradaN.png")));
    }//GEN-LAST:event_jLabel3MouseExited
    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entradaD.png")));
    }//GEN-LAST:event_jLabel3MousePressed
    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        if(mousedentro1){
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entradaA.png")));
                if(UsuaField.getText().equals("") || ContraField.equals(null))
                {
                    JOptionPane.showMessageDialog(null, "Por favor llene todos los campos", "Atención", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    if(Verificación())
                    {
                        correhora=true;
                        Nombreing=UsuaField.getText();
                        prin = new principal();
                        prin.setTitle("");
                        prin.setVisible(true);
                        this.dispose();
                    }
                }
        }
        else{
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entradaN.png")));
        }
    }//GEN-LAST:event_jLabel3MouseReleased

    /*Procesos para simular la accion de boton para un JLabel(boton salir)
    */
    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
       mousedentro2=true;
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salidaA.png")));
    }//GEN-LAST:event_jLabel2MouseEntered
    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        mousedentro2=false;
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salidaN.png")));
    }//GEN-LAST:event_jLabel2MouseExited
    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salidaD.png")));
    }//GEN-LAST:event_jLabel2MousePressed
    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        if(mousedentro2){
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salidaA.png")));
                int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea salir?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(a==JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
        }
        else{
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salidaN.png")));
        }
    }//GEN-LAST:event_jLabel2MouseReleased

   
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField ContraField;
    public static javax.swing.JLabel SaludLabel;
    private javax.swing.JTextField UsuaField;
    private javax.swing.JLabel UsuaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
