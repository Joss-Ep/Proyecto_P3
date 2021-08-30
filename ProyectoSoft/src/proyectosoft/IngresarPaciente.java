
package proyectosoft;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import static proyectosoft.Procedimientos.*;


public class IngresarPaciente extends JDialog{
    
    /*variables para saber si el muose esta dentro del label
    */
    public boolean mousedentro1;
    public boolean mousedentro2;
    public boolean mousedentro3;
    
    /*
    */
    public boolean CamObliLLeno;
    
    /*variables que ayudan en la restriccion de ingreso de datos
    */
    public String antaux;
    public String ultpres;
    
    /*partes de la cabezera
    */
    public JLabel logo = new JLabel();
    public JLabel cabezera = new JLabel();
    public JLabel nomempre = new JLabel();
    
    /*elementos dentro
    */
    public JLabel dnilab = new JLabel();
    public JTextField dniField = new JTextField();
    
    public JLabel nomlab = new JLabel();
    public JTextField nomField = new JTextField();
    
    public JLabel apeplab = new JLabel();
    public JTextField apepField = new JTextField();
    
    public JLabel apemlab = new JLabel();
    public JTextField apemField = new JTextField();
    
    public JLabel telelab = new JLabel();
    public JTextField teleField = new JTextField();
    
    public JLabel direclab = new JLabel();
    public JTextField direcField = new JTextField();
    
    /*parte final y botones
    */
    public JLabel botoningresar = new JLabel();
    public JLabel textoingresar = new JLabel();
    
    public JLabel botonlimpiar = new JLabel();
    public JLabel textolimpiar = new JLabel();
    
    public JLabel botoncancelar = new JLabel();
    public JLabel textocancelar = new JLabel();
      
    public JLabel fondo = new JLabel();//label usado para el fondo
    
    public int cont;//variable usado para contar la cantidad de pacientes
        
    public int dniregistrados[] = new int[1000];//variable usado para almazenar los DNIs anteriormente guardados
            
            
    public  JPanel pPrincipal = new JPanel();//panel donde estara toda la interfaz
    
    
    /*main de la clase
    */
    public IngresarPaciente(JFrame padre) {
        super(padre,true);
        setSize(850, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
	@Override
	public void windowClosing(WindowEvent e) {
                // Se pide una confirmación antes de finalizar el programa
		int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cancelar?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(a==JOptionPane.YES_OPTION){
                        dispose();
                    }
	}
        
});

        DescargaPacientes();
        
       ImageIcon icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo1.png"));
        this.setIconImage(icono.getImage());
        
        /*programacion del boton ingresar
        */
        botoningresar.addMouseListener(new java.awt.event.MouseAdapter(){
            /*proceso usado para registrar que el mouse entro en el label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(CompCampLLenos())
                {
                    mousedentro1=true;    
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoA.png")));
                }
            }
            /*proceso usado para registrar que el mouse salio en el label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(CompCampLLenos())
                {
                    mousedentro1=false;    
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                }
              
            }
            /*proceso usado para cambiar la imagen para ayudar en la animación (no se como explicarlo de otra manera XD)
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(CompCampLLenos())
                {
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoD.png")));
                }
              
            }
            /*proceso de finalizacion de la progamacion del boton, este verificara los datos ingresados y hara la consulta de ingreso a la base de datos
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(CompCampLLenos())
                {
                    if(mousedentro1){
                        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoA.png")));        
                        boolean existe=false;
                        for(int i=0;i<cont;i++)
                        {
                            if(dniField.getText().equals(dniregistrados[i])){
                                existe=true;
                                i=cont;
                            }
                        }
                        if(existe){
                            JOptionPane.showMessageDialog(null, "El paciente ingresado ya fue registrado anteriormente, por favor llene otros datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            limpiar();
                        }
                        else{
                                if(camposvalidos())
                                {
                                int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea ingresar los datos?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                                        if(a==JOptionPane.YES_OPTION){
                                            IngresarPaciente();
                                            limpiar();
                                        } 
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Por favor llene correctamente los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            
                                }
                        }
                        
                    }
                    else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor llene todos los campos obligatorios \"*\"", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        /*programacion del boton limpiar
        */
        botonlimpiar.addMouseListener(new java.awt.event.MouseAdapter(){
            
            /*proceso usado para registrar que el mouse a entrado al label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mousedentro2=true;    
                botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarA.png")));
            }
            
            /*proceso usado para registrar que el mouse a salido del label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mousedentro2=false;    
                botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarN.png")));
              
            }
            
            /*proceso usado para cambiar la imagen para ayudar en la animacion
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarD.png")));
              
            }
            
            /*proceso de finalizacion del boton, este borrara todos los datos ingresados
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(mousedentro2){
                    botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarA.png")));        
                    limpiar();
                }
                else{
                botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarN.png")));
                }
              
            }
        });
        
        /*programacion del boton cancelar
        */
        botoncancelar.addMouseListener(new java.awt.event.MouseAdapter(){
            
            /*proceso usado para registrar que el mouse esta entrando al label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mousedentro3=true;    
                botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarA.png")));
            }
            
            /*proceso usado para registrar que el mouse esta saliendo del label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mousedentro3=false;    
                botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
              
            }
            
            /*proceso usado para cambiar la imagen para ayudar en la animación
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarD.png")));
              
            }
            
            /*proceso de finalizacion del boton; este preguntara si desea cancelar la operación, en caso si, cerrara la ventana
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(mousedentro3){
                    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarA.png")));        
                    int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cancelar?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(a==JOptionPane.YES_OPTION){
                        dispose();
                    }
                }
                else{
                botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
                }
              
            }
        });
   
        /*proceso usado para: permitir escribir solo numeros, pasar al siguiente campo con un enter, restringir en un maximo de 8 digitos, verificar si ya estan todos los campos necesarios llenos
        */
        dniField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyPressed(java.awt.event.KeyEvent evt) {
                antaux=dniField.getText();
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if(!pruebaNum(evt) && evt.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
                {
                    dniField.setText(antaux);
                }
                else{
                    int dni;
                    if(!dniField.getText().equals(""))
                    {
                        dni=Integer.parseInt(dniField.getText());
                        if(dni<0 || dni >99999999)
                            dniField.setText(antaux);
                    }
                }
                if(evt.getKeyChar()==KeyEvent.VK_ENTER){
                    nomField.grabFocus();
                }
                if(!CompCampLLenos()){
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
                    dniField.grabFocus();
                }
                else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoN.png")));
                }
            }
        });
        
        /*proceso usado para: pasar al siguiente campo con un enter, verificar si ya estan todos los campos necesarios llenos
        */
        nomField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if(!CompCampLLenos()){
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
                }
                else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoN.png")));
                }
                if(evt.getKeyChar()==KeyEvent.VK_ENTER){
                    apepField.grabFocus();
                }
            }
        });
        
        /*proceso usado para: pasar al siguiente campo con un enter, verificar si ya estan todos los campos necesarios llenos
        */
        apepField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if(!CompCampLLenos()){
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
                }
                else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoN.png")));
                }
                if(evt.getKeyChar()==KeyEvent.VK_ENTER){
                    apemField.grabFocus();
                }
            }
        });
        
        /*proceso usado para: pasar al siguiente campo con un enter, verificar si ya estan todos los campos necesarios llenos
        */
        apemField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if(!CompCampLLenos()){
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
                }
                else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoN.png")));
                }
                if(evt.getKeyChar()==KeyEvent.VK_ENTER){
                    teleField.grabFocus();
                }
            }
        });
        
        /*proceso usado para;permitir escribir solo numeros,+,(); pasar al siguiente campo con un enter, verificar si ya estan todos los campos necesarios llenos
        */
        teleField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyPressed(java.awt.event.KeyEvent evt) {
                antaux=teleField.getText();
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if(!teleField.getText().equals(null))
                {
                    String aux=teleField.getText();
                    if(aux.length()>=1){
                    ultpres=aux.substring(aux.length()-1,aux.length());
                    }
                    if(!(pruebaNum(evt) ||  ultpres.equals("+") ||  ultpres.equals("(") ||  ultpres.equals(")") || evt.getKeyChar()==KeyEvent.VK_BACK_SPACE))
                    {
                        teleField.setText(antaux);
                    }
                    else{
                        int cant;
                        cant=aux.length();
                        if(cant>12)
                            teleField.setText(antaux);
                    }
                }
                if(evt.getKeyChar()==KeyEvent.VK_ENTER){
                    direcField.grabFocus();
                }
                 if(!CompCampLLenos()){
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
                }
                  else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoN.png")));
                }
                    }
        });
        
        /*proceso usado para llamar el proceso de ingreso con un enter 
        */
        direcField.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent evt) {
                
                        if(evt.getKeyChar()== KeyEvent.VK_ENTER){
                            if(CompCampLLenos())
                            {
                                boolean existe=false;
                                for(int i=0;i<cont;i++)
                                {
                                    if(dniField.getText().equals(dniregistrados[i])){
                                        existe=true;
                                        i=cont;
                                    }
                                }
                                if(existe){
                                    JOptionPane.showMessageDialog(null, "El usuario ingresado ya fue registrado anteriormente, por favor llene otros datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                    limpiar();
                                }
                                else{
                                    if(camposvalidos())
                                    {
                                        int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea ingresar los datos?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                                            if(a==JOptionPane.YES_OPTION){
                                                IngresarPaciente();
                                                limpiar();
                                            } 
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Por favor llene correctamente los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);

                                    }
                                }    
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos obligatorios \"*\"", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                if(!CompCampLLenos()){
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
                }
                  else{
                    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoN.png")));
                }
                
            }
        });
   
        
        
        
        
        
     pPrincipal.setLayout(null);//preparando el panel para lo que viene XD
   
    /*se añade todos los elementos al panel    
     */
    pPrincipal.add(logo);
    pPrincipal.add(cabezera);
    pPrincipal.add(nomempre);
   
    pPrincipal.add(dnilab);
    pPrincipal.add(dniField);
    pPrincipal.add(nomlab);
    pPrincipal.add(nomField);
    pPrincipal.add(apeplab);
    pPrincipal.add(apepField);
    pPrincipal.add(apemlab);
    pPrincipal.add(apemField);
    pPrincipal.add(telelab);
    pPrincipal.add(teleField);
    pPrincipal.add(direclab);
    pPrincipal.add(direcField);
   
    pPrincipal.add(botoningresar);
    pPrincipal.add(textoingresar);
    pPrincipal.add(botonlimpiar);
    pPrincipal.add(textolimpiar);
    pPrincipal.add(botoncancelar);
    pPrincipal.add(textocancelar);
   
    pPrincipal.add(fondo);
   
    pPrincipal.setSize(850, 600);//se da el tamaño general al panel
  
  /*se ingresa los datos de cada objeto
  */
    logo.setSize(100,50);
    logo.setLocation(20, 10);
    logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoEsSalud2.png")));
    logo.setVisible(true);
    
    cabezera.setSize(295,30);
    cabezera.setLocation(260, 20);
    cabezera.setText("Ingresando nuevo paciente");
    cabezera.setVisible(true);
    
    
    nomempre.setLocation(540, 20);
    nomempre.setSize(340, 30);
    nomempre.setFont(new java.awt.Font("Segoe UI", 1, 18));
    nomempre.setForeground(new java.awt.Color(0, 0, 255));
    nomempre.setText("Seguro Social de Salud \"EsSalud\"");
    nomempre.setVisible(true);
    
    
    
    dnilab.setSize(100,30);
    dnilab.setLocation(25, 80);
    dnilab.setText("DNI: *");
    dnilab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    dnilab.setVisible(true);
    
    dniField.setSize(100,20);
    dniField.setLocation(25, 102);
    dniField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
    dniField.setVisible(true);
    
    nomlab.setSize(100,30);
    nomlab.setLocation(150, 80);
    nomlab.setText("Nombres: *");
    nomlab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    nomlab.setVisible(true);
    
    nomField.setSize(200,20);
    nomField.setLocation(150, 102);
    nomField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
    nomField.setVisible(true);
    
    apeplab.setSize(150,30);
    apeplab.setLocation(375, 80);
    apeplab.setText("Apellido Paterno: *");
    apeplab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    apeplab.setVisible(true);
    
    apepField.setSize(200,20);
    apepField.setLocation(375, 102);
    apepField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
    apepField.setVisible(true);
    
    apemlab.setSize(150,30);
    apemlab.setLocation(600, 80);
    apemlab.setText("Apellido Materno: *");
    apemlab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    apemlab.setVisible(true);
    
    apemField.setSize(200,20);
    apemField.setLocation(600, 102);
    apemField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
    apemField.setVisible(true);
    
    telelab.setSize(200,30);
    telelab.setLocation(200, 125);
    telelab.setText("Número telefonico: (\"#\", \"+\" o \"( )\")*");
    telelab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    telelab.setVisible(true);
    
    teleField.setSize(200,20);
    teleField.setLocation(200, 147);
    teleField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
    teleField.setVisible(true);
    
    direclab.setSize(100,30);
    direclab.setLocation(425, 125);
    direclab.setText("Dirección: *");
    direclab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    direclab.setVisible(true);
    
    direcField.setSize(200,20);
    direcField.setLocation(425, 147);
    direcField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
    direcField.setVisible(true);
    
    
    
    
    botoningresar.setSize(80,80);
    botoningresar.setLocation(105, 190);
    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
    botoningresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botoningresar.setVisible(true);   
    
    textoingresar.setSize(80,30);
    textoingresar.setLocation(105, 270);
    textoingresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textoingresar.setText("Ingresar");
    textoingresar.setVisible(true);
    
    botonlimpiar.setSize(80,80);
    botonlimpiar.setLocation(385, 190);
    botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarN.png")));
    botonlimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botonlimpiar.setVisible(true);   
    
    textolimpiar.setSize(80,30);
    textolimpiar.setLocation(385, 270);
    textolimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textolimpiar.setText("Limpiar");
    textolimpiar.setVisible(true);
    
    botoncancelar.setSize(80,80);
    botoncancelar.setLocation(665, 190);
    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
    botoncancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botoncancelar.setVisible(true);   
    
    textocancelar.setSize(80,30);
    textocancelar.setLocation(665, 270);
    textocancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textocancelar.setText("Cancelar");
    textocancelar.setVisible(true);
    
    fondo.setSize(850, 340);
    fondo.setLocation(0, 0);
    fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoing.jpg")));
    fondo.setVisible(true);
    
    add(pPrincipal);
    
    setVisible(true);
}
    
    
    
    
    /*proceso usado para ingresar el paciente a la base de datos
    */
    public void IngresarPaciente(){
      try{
            Connection con = null;
            con = Conección();
            PreparedStatement ps = null;
            int dni=Integer.parseInt(dniField.getText());
            String nom = nomField.getText();
            String apeP = apepField.getText();
            String apeM = apemField.getText();
            String telef = teleField.getText();
            String direc = direcField.getText();
            String sql="call ing_pac('"+dni+"','"+nom+"','"+apeP+"','"+apeM+"','"+telef+"','"+direc+"');";
            ps= con.prepareStatement(sql);
            ps.execute();
                JOptionPane.showMessageDialog(null, "Se logro registrar al paciente existosamente ", "Ingreso exitoso", JOptionPane.INFORMATION_MESSAGE);
                antaux="";
                dniField.grabFocus();
                DescargaPacientes();
             }catch(Exception e){
                  JOptionPane.showMessageDialog(null, "No se logro ingresar al paciente", "Atención", JOptionPane.ERROR_MESSAGE);
                  System.err.println(e);
                }
  }
    
    /*proceso usado para comprobar si e han llenado todos los campos necesarios
    */
    public boolean CompCampLLenos(){
        boolean aux;
        aux = !dniField.getText().equals("") && !nomField.getText().equals("") && !apepField.getText().equals("") && !apemField.getText().equals("") && !teleField.getText().equals("") && !direcField.getText().equals("");
        if(!dniField.getText().equals(""))
        {
            int aux2=Integer.parseInt(dniField.getText());
            for(int i=0;i<cont;i++){
                if(dniregistrados[i]==aux2){
                    aux=false;
                    dniField.setText("");
                    antaux="";
                    JOptionPane.showMessageDialog(null, "DNI ya registrado, por favor llene otro", "Atención", JOptionPane.WARNING_MESSAGE);
                    
                }
            }
            int DNI=Integer.parseInt(dniField.getText());
                if(DNI<10000000 || DNI>99999999){
                    aux=false;
                }
        }
        
        return aux;
    }
    
    /*proceso usado para comprovar si todos los campos estan llenados correctamente
    */
    public boolean camposvalidos(){
        boolean aux=true;
        if(dniField.getText().equals("")){
            aux=false;
        }
        else{
            boolean aux2=true;
            String dni=dniField.getText();
            for(int i=0;i<dni.length();i++){
                if(!PrueNumString(dni.substring(i, i+1))){
                    aux=false;
                    aux2=false;
                }
            }
            if(aux2){
                int DNI=Integer.parseInt(dni);
                if(DNI<10000000 || DNI>99999999){
                    aux=false;
                }
            }
            String telefono=teleField.getText();
            for(int i=0;i<telefono.length();i++){
                if(!(PrueNumString(telefono.substring(i, i+1)) || telefono.substring(i, i+1).equals("+") || telefono.substring(i, i+1).equals("(") || telefono.substring(i, i+1).equals(")"))){
                    aux=false;
                }
            }
            if(telefono.equals("")){
                aux=false;
            }
            String direccion=direcField.getText();
            if(direccion.equals("")){
            aux=false;
            }
        }
        
        return aux;
    }
    
    
    /*proceso usado para "limpiar" todos los campos
    */
    public void limpiar(){
        dniField.setText("");
        nomField.setText("");
        apepField.setText("");
        apemField.setText("");
        teleField.setText("");
        direcField.setText("");
        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
    }
    
    /*proceso usado para descargar todos los pacientes ya registrados anteriormente
    */
    public void DescargaPacientes(){
      try{
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    String sql="SELECT * FROM Paciente;";
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
    cont=0;
    while(rs.next())
    {
            dniregistrados[cont]=rs.getInt(1);
            cont++;
    }
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se logro ingresar al paciente", "Atención", JOptionPane.ERROR_MESSAGE);
        }
  }
}
