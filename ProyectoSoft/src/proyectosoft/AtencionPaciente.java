
package proyectosoft;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import static proyectosoft.Procedimientos.*;
import static proyectosoft.principal.prueba;
import static proyectosoft.Ingreso.Condigoing;

public class AtencionPaciente extends JDialog{
    /*variables para saber si el mouse esta dentro del label
    */
    public boolean mousedentro1;
    public boolean mousedentro2;
    public boolean mousedentro3;
    public boolean mousedentro4;
    public boolean mousedentro5;
    
    /*variables auxiliares para corrobar el registro
    */
    public boolean CamObliLLeno,pacienteSeleccionado=false;
    
    /*variables que ayudan en la restriccion de ingreso de datos
    */
    public String antaux;
    public String ultpres;
    
    /*partes de la cabezera
    */
    public JLabel logo = new JLabel();
    public JLabel cabezera1 = new JLabel();
    public JLabel nomempre = new JLabel();
    
    /*elementos dentro de la primera parte
    */
    public JLabel dnilab = new JLabel();
    public JLabel dnimost = new JLabel();
    
    public JLabel nomlab = new JLabel();
    public JLabel nommost = new JLabel();
    
    public JLabel TipPruelab = new JLabel();
    public JComboBox TipPrueCombo = new javax.swing.JComboBox<>();
    
    public JLabel MetSeglab = new JLabel();
    public JComboBox MetSegCombo = new javax.swing.JComboBox<>();
    
    public JLabel NiGravlab = new JLabel();
    public JComboBox NiGravCombo = new javax.swing.JComboBox<>();
    
    public JLabel ExLablab = new JLabel();
    public JLabel ExLabMost = new JLabel();
    public JLabel ExLabElegir = new JLabel();
    public static File ExLabFile = new File("");
    public FileInputStream ExLabInput ;
    
    public JLabel ExTomolab = new JLabel();
    public JLabel ExTomoMost = new JLabel();
    public JLabel ExTomoElegir = new JLabel();
    public static File ExTomoFile = new File("");    
    public FileInputStream ExTomoInput ;
    
    /*parte sub final y botones
    */
    public JLabel botoningresar = new JLabel();
    public JLabel textoingresar = new JLabel();
    
    public JLabel botonlimpiar = new JLabel();
    public JLabel textolimpiar = new JLabel();
    
    public JLabel botoncancelar = new JLabel();
    public JLabel textocancelar = new JLabel();
      
    /*elementos dentro de la segunda parte parte
    */
    
    public JLabel cabezera2 = new JLabel();
    
    
    public JLabel IndTiplab = new JLabel();
    public JComboBox IndTipCombo = new javax.swing.JComboBox<>();
    
    public JTextField llenarFiel = new JTextField();
    
    public JScrollPane Scrol = new javax.swing.JScrollPane();
    public JTable DatosTabla = new javax.swing.JTable();
    
    public JLabel botoncomensar = new JLabel();
    public JLabel textocomensar = new JLabel();
    
    public JLabel botonvolver = new JLabel();
    public JLabel textovolver = new JLabel();
    
    
    /*label usado para los fondos
    */
    public JLabel fondo = new JLabel();
    public JLabel fondo2 = new JLabel();
    
    /*variable usado para contar la cantidad de pacientes
    */
    public int cont;
    
    
    /*usados para el ingreso de los pdfs
    */
    public int AntExLab;
    public int AntExTomo;
    
    /*usado para el ingreso del la atencion
    */
    public int AntRegistro;
            
    public  JPanel pPrincipal = new JPanel();
    
    public ImageIcon icono;
    
    /* main de la ventana
    */
    @SuppressWarnings("unchecked")
    public AtencionPaciente(JFrame padre) {
        super(padre,true);
        icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo1.png"));
        setSize(930, 700);
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

        this.setIconImage(icono.getImage());
        
        /*proceso usados para verificar si hay es disponoble ingresar la atencion 
        */
        TipPrueCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(camposvalidos())
                    {
                            botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                        
                    }
            }
        });
        MetSegCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(camposvalidos())
                    {
                            botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                        
                    }
            }
        });
        NiGravCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(camposvalidos())
                    {
                            botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                        
                    }
            }
        });
        
        /*programacion del boton elegir archivo 1
        */
        ExLabElegir.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso usado para cambiar la imagen para ayudar en la animación (no se como explicarlo de otra manera XD)
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(ExLabElegir.isEnabled()){
                    ExLabElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfD.png")));
                
                }
            }
            /*proceso de finalizacion de la progamacion del boton, este verificara los datos ingresados y hara la consulta de ingreso a la base de datos
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(ExLabElegir.isEnabled()){

                        ExLabElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfN.png")));        
                                            
                                            seleccionar_pdflab(ExLabMost, ExLabFile);
                                        
                }
            }
        });
        
        /*programacion del boton elegir archivo 2
        */
        ExTomoElegir.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso usado para cambiar la imagen para ayudar en la animación (no se como explicarlo de otra manera XD)
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(ExTomoElegir.isEnabled()){
                    ExTomoElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfD.png")));
                
                }
            }
            
            /*proceso de finalizacion de la progamacion del boton, este verificara los datos ingresados y hara la consulta de ingreso a la base de datos
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(ExTomoElegir.isEnabled()){

                        ExTomoElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfN.png")));        
                               
                                            seleccionar_pdftomo(ExTomoMost, ExTomoFile);
                                        
                }
            }
        });
        
        
        /*programacion del boton ingresar
        */
        botoningresar.addMouseListener(new java.awt.event.MouseAdapter(){
            /*proceso usado para registrar que el mouse entro en el label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               if(botoningresar.isEnabled()){
                    if(camposvalidos())
                    {
                        mousedentro1=true;    
                        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoA.png")));
                    }
               }
                
            }
            /*proceso usado para registrar que el mouse salio en el label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(botoningresar.isEnabled()){
                    if(camposvalidos())
                    {
                        mousedentro1=false;    
                        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                    }
                }
              
            }
            /*proceso usado para cambiar la imagen para ayudar en la animación (no se como explicarlo de otra manera XD)
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(botoningresar.isEnabled()){
                    if(camposvalidos())
                    {
                        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoD.png")));
                    }
                }
            }
            /*proceso de finalizacion de la progamacion del boton, este verificara los datos ingresados y hara la consulta de ingreso a la base de datos
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(botoningresar.isEnabled()){
                    if(camposvalidos())
                    {
                        if(mousedentro1){
                            botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoA.png")));        

                                    int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea ingresar los datos?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                                            if(a==JOptionPane.YES_OPTION){
                                                if(!ExLabMost.getText().equals("")){
                                                ExamLabAnt();
                                                guardar_pdfLab(AntExLab, ExLabFile);
                                                }
                                                if(!ExTomoMost.getText().equals("")){
                                                ExamTomoAnt();
                                                guardar_pdfTomo(AntExTomo, ExTomoFile);
                                                }
                                                AnteriorRegistro();
                                                IngresarAtencion(AntExLab, AntExTomo);
                                                limpiar();
                                            } 

                                    else{
                                        JOptionPane.showMessageDialog(null, "Por favor llene correctamente los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);

                                    }
                        }
                        else{
                            botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IngresoN.png")));
                        }
                    }
                }
            }
        });
        
        /*programacion del boton limpiar
        */
        botonlimpiar.addMouseListener(new java.awt.event.MouseAdapter(){
            
            /*proceso usado para registrar que el mouse a entrado al label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(botonlimpiar.isEnabled()){
                    mousedentro2=true;    
                    botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarA.png")));
                }
            }
            
            /*proceso usado para registrar que el mouse a salido del label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(botonlimpiar.isEnabled()){
                    mousedentro2=false;    
                    botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarN.png")));
                }
            }
            
            /*proceso usado para cambiar la imagen para ayudar en la animacion
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(botonlimpiar.isEnabled()){
                    botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarD.png")));
                }
            }
            
            /*proceso de finalizacion del boton, este borrara todos los datos ingresados
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(botonlimpiar.isEnabled()){
                    if(mousedentro2){
                        botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarA.png")));        
                        limpiar();
                    }
                    else{
                        botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarN.png")));
                    }
                }
            }
        });
        
        /*programacion del boton cancelar
        */
        botoncancelar.addMouseListener(new java.awt.event.MouseAdapter(){
            
            /*proceso usado para registrar que el mouse esta entrando al label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(botoncancelar.isEnabled()){
                    mousedentro3=true;    
                    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarA.png")));
                }
            }
            
            /*proceso usado para registrar que el mouse esta saliendo del label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(botoncancelar.isEnabled()){
                    mousedentro3=false;    
                    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
                }
            }
            
            /*proceso usado para cambiar la imagen para ayudar en la animación
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(botoncancelar.isEnabled()){
                    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarD.png")));
                }
            }
            
            /*proceso de finalizacion del boton; este preguntara si desea cancelar la operación, en caso si, volvera al otro sector
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(botoncancelar.isEnabled()){
                    if(mousedentro3){
                        botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarA.png")));        
                        int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cancelar?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        if(a==JOptionPane.YES_OPTION){
                            cambioZona2();
                        }
                    }
                    else{
                        botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
                    }
                }
            }
        });
        
        /*proceso para si se selecciona algun tipo de busqueda; de ser asi, borrara lo actualmente ingresado
        */
        IndTipCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                llenarFiel.setText("");
                BuscarPacientes();
            }
        });
        
        /*Proceso para que cada vez que se llene algo, lo busque en los pacientes
        */
        llenarFiel.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BuscarPacientes();
            }
        });
        
        /*programacion del boton comensar
        */
        botoncomensar.addMouseListener(new java.awt.event.MouseAdapter(){
            
            /*proceso usado para registrar que el mouse esta entrando al label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(botoncomensar.isEnabled() && pacienteSeleccionado){
                    mousedentro4=true;    
                    botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarA.png")));
                }
            }
            
            /*proceso usado para registrar que el mouse esta saliendo del label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(botoncomensar.isEnabled() && pacienteSeleccionado){
                    mousedentro4=false;    
                    botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarN.png")));
                }
            }
            
            /*proceso usado para cambiar la imagen para ayudar en la animación
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(botoncomensar.isEnabled() && pacienteSeleccionado){
                    botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarD.png")));
                }
            }
            
            /*proceso de finalizacion del boton; este enviara los datos y activara el primer sector
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(botoncomensar.isEnabled() && pacienteSeleccionado){
                    if(mousedentro4){
                        botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarA.png")));        
                        cambioZona1();
                    }
                    else{
                        botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarN.png")));
                    }
                }
            }
        });
        
        /*programacion del boton volver 
        */
        botonvolver.addMouseListener(new java.awt.event.MouseAdapter(){
            
            /*proceso usado para registrar que el mouse esta entrando al label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(botonvolver.isEnabled()){
                    mousedentro5=true;    
                    botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarA.png")));
                }
            }
            
            /*proceso usado para registrar que el mouse esta saliendo del label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(botonvolver.isEnabled()){
                    mousedentro5=false;    
                    botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
                }
            }
            
            /*proceso usado para cambiar la imagen para ayudar en la animación
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(botonvolver.isEnabled()){
                    botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarD.png")));
                }
            }
            
            /*proceso de finalizacion del boton; este preguntara si desea volver a la principal, en caso si, cerrara la ventana y volvera
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(botonvolver.isEnabled()){
                    if(mousedentro5){
                        botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarA.png")));        
                        int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cancelar?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                        if(a==JOptionPane.YES_OPTION){
                            dispose();
                        }
                    }
                    else{
                        botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
                    }
                }
            }
        });
        
        /*Proceso que detecta si se presiono algun elemento de la tabla
        */
        DatosTabla.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso para seleccionar y habilitar el boton comenzar
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                
                if(DatosTabla.isEnabled()){
                boolean aux=false;
                int aux2=DatosTabla.getRowCount();
                        
                for(int i=0;i<aux2;i++){
                    if(DatosTabla.isRowSelected(i))
                    aux=true;
                }
                
                    if(aux){
                         botoncomensar.setEnabled(true);
                         botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarN.png")));
                         pacienteSeleccionado=true;
                    }
                    else{
                        botoncomensar.setEnabled(false);
                         botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarDes.png")));
                         pacienteSeleccionado=false;
                    }
                }
            }
        });
        
        /*Proceso auxiliar para evitar un mal ingreso de datos
        */
        llenarFiel.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso para verificar si se puede llenar
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                int aux3=IndTipCombo.getSelectedIndex();
                if(aux3 != -1){
                    llenarFiel.setEnabled(true);
                }
                
            }
        });
        
        pPrincipal.setLayout(null);//preparando el panel para lo que viene XD
   
        /*se añade todos los elementos al panel    
        */
        pPrincipal.add(logo);
        pPrincipal.add(cabezera1);
        pPrincipal.add(nomempre);
   
        pPrincipal.add(dnilab);
        pPrincipal.add(dnimost);
        pPrincipal.add(nomlab);
        pPrincipal.add(nommost);
        pPrincipal.add(TipPruelab);
        pPrincipal.add(TipPrueCombo);
        pPrincipal.add(MetSeglab);
        pPrincipal.add(MetSegCombo);
        pPrincipal.add(NiGravlab);
        pPrincipal.add(NiGravCombo);
        pPrincipal.add(ExLablab);
        pPrincipal.add(ExLabMost);
        pPrincipal.add(ExLabElegir);
        pPrincipal.add(ExTomolab);
        pPrincipal.add(ExTomoMost);
        pPrincipal.add(ExTomoElegir);
   
        pPrincipal.add(botoningresar);
        pPrincipal.add(textoingresar);
        pPrincipal.add(botonlimpiar);
        pPrincipal.add(textolimpiar);
        pPrincipal.add(botoncancelar);
        pPrincipal.add(textocancelar);
   
        pPrincipal.add(fondo);

        pPrincipal.add(cabezera2);

        pPrincipal.add(IndTiplab);
        pPrincipal.add(IndTipCombo);
        pPrincipal.add(llenarFiel);
        pPrincipal.add(Scrol);
        pPrincipal.add(DatosTabla);
        pPrincipal.add(botoncomensar);
        pPrincipal.add(textocomensar);
        pPrincipal.add(botonvolver);
        pPrincipal.add(textovolver);

        pPrincipal.add(fondo2);

        pPrincipal.setSize(930, 700);//se da el tamaño general al panel

  /*se ingresa los datos de cada objeto
  */
    logo.setSize(100,50);
    logo.setLocation(20, 10);
    logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoEsSalud2.png")));
    logo.setVisible(true);
    
    cabezera1.setSize(340,30);
    cabezera1.setLocation(260, 20);
    cabezera1.setText("Seleccione la atención que se le dio al paciente");
    cabezera1.setVisible(true);
    cabezera1.setEnabled(false);
    
    nomempre.setLocation(590, 20);
    nomempre.setSize(340, 30);
    nomempre.setFont(new java.awt.Font("Segoe UI", 1, 18));
    nomempre.setForeground(new java.awt.Color(0, 0, 255));
    nomempre.setText("Seguro Social de Salud \"EsSalud\"");
    nomempre.setVisible(true);
    nomempre.setEnabled(false);
    
    
    
    dnilab.setSize(100,20);
    dnilab.setLocation(25, 80);
    dnilab.setText("DNI:");
    dnilab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    dnilab.setVisible(true);
    dnilab.setEnabled(false);
    
    
    dnimost.setSize(100,20);
    dnimost.setLocation(105, 80);
    dnimost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    dnimost.setVisible(true);
    dnimost.setEnabled(false);
    
    
    nomlab.setSize(100,20);
    nomlab.setLocation(25, 110);
    nomlab.setText("Nombre: ");
    nomlab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    nomlab.setVisible(true);
    nomlab.setEnabled(false);
    
    
    nommost.setSize(200,20);
    nommost.setLocation(105, 110);
    nommost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    nommost.setVisible(true);
    nommost.setEnabled(false);
    
    TipPruelab.setSize(170,25);
    TipPruelab.setLocation(25, 140);
    TipPruelab.setText("Tipo de prueba: ");
    TipPruelab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    TipPruelab.setVisible(true);
    TipPruelab.setEnabled(false);
    
    TipPrueCombo.setSize(90,25);
    TipPrueCombo.setLocation(120, 140);
    TipPrueCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Isópado", "Serológica", "Molecular", "No recibio" }));
    TipPrueCombo.setSelectedIndex(-1);
    TipPrueCombo.setVisible(true);
    TipPrueCombo.setEnabled(false);
    
    
    MetSeglab.setSize(200,25);
    MetSeglab.setLocation(280, 140);
    MetSeglab.setText("Metodo de Seguimiento: ");
    MetSeglab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    MetSeglab.setVisible(true);
    MetSeglab.setEnabled(false);
    
    MetSegCombo.setSize(120,25);
    MetSegCombo.setLocation(430, 140);
    MetSegCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tele-monitoreo", "Triaje Covid", "No recibio" }));
    MetSegCombo.setSelectedIndex(-1);
    MetSegCombo.setVisible(true);
    MetSegCombo.setEnabled(false);
    
    NiGravlab.setSize(150,25);
    NiGravlab.setLocation(580, 140);
    NiGravlab.setText("Nivel de gravedad: ");
    NiGravlab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    NiGravlab.setVisible(true);
    NiGravlab.setEnabled(false);
    
    NiGravCombo.setSize(150,25);
    NiGravCombo.setLocation(720, 140);
    NiGravCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Leve", "Moderado", "Grave", "Muy grave", "sin clasificación" }));
    NiGravCombo.setSelectedIndex(-1);
    NiGravCombo.setVisible(true);
    NiGravCombo.setEnabled(false);
    
    ExLablab.setSize(160,25);
    ExLablab.setLocation(25, 170);
    ExLablab.setText("Examen de laboratorio: ");
    ExLablab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    ExLablab.setVisible(true);
    ExLablab.setEnabled(false);
    
    ExLabMost.setSize(200,25);
    ExLabMost.setLocation(170, 170);
    ExLabMost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    ExLabMost.setVisible(true);
    ExLabMost.setEnabled(false);
    
    ExLabElegir.setSize(25,25);
    ExLabElegir.setLocation(380, 170);
    ExLabElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfDes.png")));
    ExLabElegir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    ExLabElegir.setVisible(true);
    ExLabElegir.setEnabled(false);
    
    ExTomolab.setSize(150,30);
    ExTomolab.setLocation(445, 170);
    ExTomolab.setText("Examen de tomografía: ");
    ExTomolab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    ExTomolab.setVisible(true);
    ExTomolab.setEnabled(false);
    
    ExTomoMost.setSize(200,25);
    ExTomoMost.setLocation(590, 170);
    ExTomoMost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    ExTomoMost.setVisible(true);
    ExTomoMost.setEnabled(false);
    
    ExTomoElegir.setSize(25,25);
    ExTomoElegir.setLocation(800, 170);
    ExTomoElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfDes.png")));
    ExTomoElegir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    ExTomoElegir.setVisible(true);
    ExTomoElegir.setEnabled(false);
    
    
    botoningresar.setSize(80,80);
    botoningresar.setLocation(105, 220);
    botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
    botoningresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botoningresar.setVisible(true);
    botoningresar.setEnabled(false);   
    
    textoingresar.setSize(80,30);
    textoingresar.setLocation(105, 300);
    textoingresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textoingresar.setText("Ingresar");
    textoingresar.setVisible(true);
    textoingresar.setEnabled(false);
    
    botonlimpiar.setSize(80,80);
    botonlimpiar.setLocation(385, 220);
    botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarDes.png")));
    botonlimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botonlimpiar.setVisible(true);
    botonlimpiar.setEnabled(false);   
    
    textolimpiar.setSize(80,30);
    textolimpiar.setLocation(385, 300);
    textolimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textolimpiar.setText("Limpiar");
    textolimpiar.setVisible(true);
    textolimpiar.setEnabled(false);
    
    botoncancelar.setSize(80,80);
    botoncancelar.setLocation(665, 220);
    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarDes.png")));
    botoncancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botoncancelar.setVisible(true); 
    botoncancelar.setEnabled(false);
    
    textocancelar.setSize(80,30);
    textocancelar.setLocation(665, 300);
    textocancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textocancelar.setText("Cancelar");
    textocancelar.setVisible(true);
    textocancelar.setEnabled(false);
    
    IndTiplab.setSize(185,30);
    IndTiplab.setLocation(25, 340);
    IndTiplab.setText("Seleccione el identificador: ");
    IndTiplab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    IndTiplab.setVisible(true);
    IndTiplab.setEnabled(false);
    
    IndTipCombo.setSize(200,30);
    IndTipCombo.setLocation(220, 340);
    IndTipCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI","Nombre", "Apellido Paterno", "Apellido Materno"  }));
    IndTipCombo.setSelectedIndex(-1);
    IndTipCombo.setVisible(true);
    
    llenarFiel.setSize(400,30);
    llenarFiel.setLocation(25, 380);
    llenarFiel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    llenarFiel.setVisible(true);
    llenarFiel.setEnabled(false);
    
    DatosTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "DNI", "Nombre", "Apellido Paterno", "Apellido Materno"
            }
        )
                {
                    boolean[] canEdit = new boolean [] {
                        false, false, false, false
                    };

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                }
    );
        if (DatosTabla.getColumnModel().getColumnCount() > 0) {
            DatosTabla.getColumnModel().getColumn(0).setMinWidth(100);
            DatosTabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            DatosTabla.getColumnModel().getColumn(0).setMaxWidth(100);
        }
    
   DatosTabla.getTableHeader().setReorderingAllowed(false);
     
        
    Scrol.setViewportView(DatosTabla);
    
    Scrol.setSize(750,200);
    Scrol.setLocation(25, 420);
    Scrol.setVisible(true);
    
    botoncomensar.setSize(80,80);
    botoncomensar.setLocation(800, 430);
    botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarDes.png")));
    botoncomensar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botoncomensar.setVisible(true);   
    
    textocomensar.setSize(80,30);
    textocomensar.setLocation(800, 510);
    textocomensar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textocomensar.setText("Comenzar");
    textocomensar.setVisible(true);
    
    botonvolver.setSize(80,80);
    botonvolver.setLocation(800, 550);
    botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
    botonvolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botonvolver.setVisible(true);   
    
    textovolver.setSize(80,30);
    textovolver.setLocation(800, 630);
    textovolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textovolver.setText("Volver");
    textovolver.setVisible(true);
    
    
    fondo.setSize(930, 335);
    fondo.setLocation(0, 0);
    fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreg1Des.png")));
    fondo.setVisible(true);
    
    fondo2.setSize(930, 365);
    fondo2.setLocation(0, 335);
    fondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreg2N.jpg")));
    fondo2.setVisible(true);
    
    
    add(pPrincipal);
    
    setVisible(true);
}
    
    
    
   
    /*proceso usado para ingresar el paciente a la base de datos
    *idLab: id del exmanen de laboratorio
    *idTomo: id del examen de tomografia
    */
    private void IngresarAtencion(int idLab, int idTomo){
      try{
            Connection con = null;
            con = Conección();
            PreparedStatement ps = null;
            int dni=Integer.parseInt(dnimost.getText());
            int tipPrue = TipPrueCombo.getSelectedIndex();
            int MetSeg = MetSegCombo.getSelectedIndex();
            int NivGrav = NiGravCombo.getSelectedIndex();
            
            Date Fecha = new Date();
            SimpleDateFormat formato=new SimpleDateFormat("YYYY/MM/dd");
            
            String aux1 = null;
            String aux2 = null;
            
            if(idLab!=0){
                aux1="'"+idLab+"'";
            }
            if(idTomo!=0){
                aux2="'"+idTomo+"'";
            }
            String sql="call reg_aten('"+AntRegistro+"','"+dni+"','"+(tipPrue+1)+"','"+(MetSeg+1)+"','"+(NivGrav+1)+"',"+aux1+","+aux2+",'"+Condigoing+"','"+formato.format(Fecha)+"');";
            
            ps= con.prepareStatement(sql);
            ps.execute();
                JOptionPane.showMessageDialog(null, "Se logro registrar la atencion existosamente ", "Registro exitoso",JOptionPane.INFORMATION_MESSAGE);
             }catch(Exception e){
                  JOptionPane.showMessageDialog(null, "No se logro ingresar la atencion al paciente", "Atención", JOptionPane.ERROR_MESSAGE);
                  System.err.println(e);
                }
  }
    
    /*procesos usados para verificar la id del anterior examen
    */
    private void ExamLabAnt(){
        try{
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    
 
    String sql="SELECT * FROM Exam_Lab; ";
    
    
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
      
    ResultSetMetaData abc = rs.getMetaData();
     AntExLab=0;
    while(rs.next()){
        AntExLab=rs.getInt(1);
    }
    AntExLab++;
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se encontro ningun paciente", "Atención", JOptionPane.ERROR_MESSAGE);
          System.err.println(e);
        }
    }
     private void ExamTomoAnt(){
        try{
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    
 
    String sql="SELECT * FROM Exam_Tomo; ";
    
    
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
      
    ResultSetMetaData abc = rs.getMetaData();
     AntExTomo=0;
    while(rs.next()){
        AntExTomo=rs.getInt(1);
    }
    AntExTomo++;
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se encontro ningun paciente", "Atención", JOptionPane.ERROR_MESSAGE);
          System.err.println(e);
        }
    }
     
    /*proceso usado para verificar el anterior registro
    */
     private void AnteriorRegistro(){
        try{
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    String sql="SELECT * FROM Reg_Atencion; ";
    
    
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
      
    ResultSetMetaData abc = rs.getMetaData();
     AntRegistro=0;
    while(rs.next()){
        AntRegistro=rs.getInt(1);
    }
    AntRegistro++;
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se encontro ningun paciente", "Atención", JOptionPane.ERROR_MESSAGE);
          System.err.println(e);
        }
    }
 
    /*proceso usado para ingresar el examen de laboratorio a la base de datos
    *codigo: id del exmanen
    *ruta: ubicacion de donde tomara para guardarlo en la base de datos
    */
    private void guardar_pdfLab(int codigo, File ruta) {
        PdfDAO pa = new PdfDAO("SELECT * FROM exam_lab");
        PdfVO po = new PdfVO();
        po.setCodigopdf(codigo);
        try {
            byte[] pdf = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(pdf);
            po.setArchivopdf(pdf);
        } catch (IOException ex) {
            po.setArchivopdf(null);
            //System.out.println("Error al agregar archivo pdf "+ex.getMessage());
        }
        pa.Agregar_PdfVO(po);
    }
    
    /*proceso usado para ingresar el examen de tomografia a la base de datos
    *codigo: id del exmanen
    *ruta: ubicacion de donde tomara para guardarlo en la base de datos
    */
    private void guardar_pdfTomo(int codigo, File ruta) {
        PdfDAO pa = new PdfDAO("SELECT * FROM exam_tomo");
        PdfVO po = new PdfVO();
        po.setCodigopdf(codigo);
        try {
            byte[] pdf = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(pdf);
            po.setArchivopdf(pdf);
        } catch (IOException ex) {
            po.setArchivopdf(null);
            //System.out.println("Error al agregar archivo pdf "+ex.getMessage());
        }
        pa.Agregar_PdfVO(po);
    }
    
    /*proceso usado para comprovar si todos los campos estan llenados correctamente
    */
    private boolean camposvalidos(){
        boolean aux=true;
        int aux2;
        aux2=TipPrueCombo.getSelectedIndex();
        if(-1== aux2){
            aux=false;
        }
        aux2=MetSegCombo.getSelectedIndex();
        if(-1== aux2){
            aux=false;
        }
        aux2=NiGravCombo.getSelectedIndex();
        if(-1== aux2){
            aux=false;
        }
        
        return aux;
    }
    
    /*proceso usado para "limpiar" todos los campos
    */
    private void limpiar(){
        TipPrueCombo.setSelectedIndex(-1);
        MetSegCombo.setSelectedIndex(-1);
        NiGravCombo.setSelectedIndex(-1);
        AntExLab= 0;
        ExLabFile=null;
        ExLabMost.setText("");
        AntExTomo=0;
        ExTomoFile=null;
        ExTomoMost.setText("");
        AntRegistro=0;
        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
    }
    
    /*proceso usado para descargar todos los pacientes ya registrados anteriormente
    */
    private void BuscarPacientes(){
      try{
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    
    DefaultTableModel modelo = new DefaultTableModel(){
                    boolean[] canEdit = new boolean [] {
                        false, false, false, false
                    };

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                };
    
    
    
    
 
    String buscamos="DNI";
    switch(IndTipCombo.getSelectedIndex()){
        case 0:
            buscamos="DNI";break;
        case 1:
            buscamos="Nombre";break;
        case 2:
            buscamos="Ap_paterno";break;
        case 3:
            buscamos="Ap_materno";break;
    }
   
    String sql="SELECT DNI,Nombre,Ap_paterno,Ap_materno FROM Paciente WHERE "+buscamos+" LIKE '%"+llenarFiel.getText()+"%'";
    
    
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
      
    ResultSetMetaData abc = rs.getMetaData();
    int cantcolum=abc.getColumnCount();
    modelo.addColumn("DNI");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellido Paterno");
    modelo.addColumn("Apellido Materno");
    
    String dniexi[] = new String[1000];
    int cont=0;
    while(rs.next()){
        boolean exis=false;
        Object filas[] = new Object[cantcolum];
        
        for(int i=0;i<cantcolum;i++){ 
            filas[i]=rs.getObject(i+1);              
        }
    dniexi[cont]=(String) filas[0];
    cont++;
    if(!exis)
        modelo.addRow(filas);
    
     if (DatosTabla.getColumnModel().getColumnCount() > 0) {
            DatosTabla.getColumnModel().getColumn(0).setMinWidth(100);
            DatosTabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            DatosTabla.getColumnModel().getColumn(0).setMaxWidth(100);
        }
     
     DatosTabla.setModel(modelo );
    }
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se encontro ningun paciente", "Atención", JOptionPane.ERROR_MESSAGE);
          System.err.println(e);
        }
      
      
  }
    
    
    /*proceso usado para abrir el seleccionador de archivo
    */
    private void seleccionar_pdflab(JLabel texto, File Guardar) {
        
        String ruta="";
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(prueba);
        if (se == 0) {
            ExLabMost.setText("" + j.getSelectedFile().getName());
            ruta = j.getSelectedFile().getAbsolutePath();
            ExLabFile = new File(ruta);
            System.out.println("la ruta es " + Guardar);
            System.out.println("la ruta es" + ExLabFile);
        } else {
        }
    }
    private void seleccionar_pdftomo(JLabel texto, File Guardar) {
        
        String ruta="";
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(prueba);
        if (se == 0) {
            //texto.setText("" + j.getSelectedFile().getName());
            ExTomoMost.setText("" + j.getSelectedFile().getName());
            ruta = j.getSelectedFile().getAbsolutePath();
            //Guardar = new File(ruta);
            ExTomoFile = new File(ruta);
        } else {
        }
    }
    /*proceso usado para cambiar a la zona de arriba
    */
    private void cambioZona1(){
        
        
        IndTipCombo.setEnabled(false);
        llenarFiel.setEnabled(false);
        DatosTabla.setEnabled(false);
        botoncomensar.setEnabled(false);
        botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarDes.png")));
        botonvolver.setEnabled(false);
        botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarDes.png")));
        fondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreg2Des.png")));
        int aux=DatosTabla.getSelectedRow();
        dnimost.setText((String) DatosTabla.getValueAt(aux, 0));
        nommost.setText(DatosTabla.getValueAt(aux, 1)+" "+DatosTabla.getValueAt(aux, 2)+" "+DatosTabla.getValueAt(aux, 3));
        TipPrueCombo.setEnabled(true);
        TipPrueCombo.setSelectedIndex(-1);
        MetSegCombo.setEnabled(true);
        MetSegCombo.setSelectedIndex(-1);
        NiGravCombo.setEnabled(true);
        NiGravCombo.setSelectedIndex(-1);
        ExLabElegir.setEnabled(true);
        ExLabElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfN.png")));
        ExTomoElegir.setEnabled(true);
        ExTomoElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfN.png")));
        botoningresar.setEnabled(true);
        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
        botonlimpiar.setEnabled(true);
        botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarN.png")));
        botoncancelar.setEnabled(true);
        botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreg1N.jpg")));
        
    }
    
    /*proceso usado para cambiar a la zona de abajo
    */
    private void cambioZona2(){
        
        IndTipCombo.setEnabled(true);        
        IndTipCombo.setSelectedIndex(-1);
        llenarFiel.setEnabled(true);
        llenarFiel.setText("");
        DatosTabla.setEnabled(true);
        botoncomensar.setEnabled(true);
        botoncomensar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SeleccionarN.png")));
        botonvolver.setEnabled(true);
        botonvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
        fondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreg2N.jpg")));
        DatosTabla.setVisible(true);
        
        TipPrueCombo.setEnabled(false);
        MetSegCombo.setEnabled(false);
        NiGravCombo.setEnabled(false);
        ExLabElegir.setEnabled(false);
        ExLabElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfDes.png")));
        ExTomoElegir.setEnabled(false);
        ExTomoElegir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdfDes.png")));
        botoningresar.setEnabled(false);
        botoningresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ingresoDes.png")));
        botonlimpiar.setEnabled(false);
        botonlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarDes.png")));
        botoncancelar.setEnabled(false);
        botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarDes.png")));
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreg1Des.png")));
        
    }
    
    
}
