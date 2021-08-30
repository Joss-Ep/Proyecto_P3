
package proyectosoft;

import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static proyectosoft.Procedimientos.Conección;
import static proyectosoft.ReporteTabla.*;


public class FinalizarReporte extends JDialog {
     /*variables para saber si el muose esta dentro del label
    */
    public boolean mousedentro1;
    public boolean mousedentro2;
    public boolean mousedentro3;
    public boolean mousedentro4;
    public boolean mousedentro5;
    public boolean mousedentro6;
    
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
    public JLabel Titulo = new JLabel();
    public JLabel Especificacion = new JLabel();
    
    public static JRadioButton elegirdia = new javax.swing.JRadioButton();
    public static JDateChooser DateChosser1 = new com.toedter.calendar.JDateChooser();
    
    public static JRadioButton elegirrangodias = new javax.swing.JRadioButton();
    public static JDateChooser DateChosser2 = new com.toedter.calendar.JDateChooser();
    public static JDateChooser DateChosser3 = new com.toedter.calendar.JDateChooser();
    
    public static ButtonGroup grupoChosser = new javax.swing.ButtonGroup();
    
    
    public static JLabel Recarga = new JLabel();
       
    public static JScrollPane Scrol = new javax.swing.JScrollPane();
    public static JTable DatosTabla = new javax.swing.JTable();
    public static JLabel vacio = new JLabel();
    
   
    /*parte sub final y botones
    */
    public static JLabel botonGenReporte = new JLabel();
    public JLabel textoGenReporte = new JLabel();
    
    public JLabel botonReportarBug = new JLabel();
    public JLabel textoReportarBug = new JLabel();
    
    public JLabel botoncancelar = new JLabel();
    public JLabel textocancelar = new JLabel();
   
    /*label usado para los fondos
    */
    public JLabel fondo = new JLabel();
    public JLabel fondo2 = new JLabel();
    
    public int cont;//variable usado para contar la cantidad de pacientes
       
    public  JPanel pPrincipal = new JPanel();//panel donde estara toda la interfaz
    
    public ImageIcon icono = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo1.png"));//logo para la ventana
    
    /* main de la interfaz
    */
    public FinalizarReporte(JFrame padre) {
        super(padre,true);
        setSize(1520, 700);
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
        
        
        ReporteTabla tabla= new ReporteTabla();
        
        this.setIconImage (icono.getImage());
        
        
        
        /*programacion del boton Regargar
        */
        Recarga.addMouseListener(new java.awt.event.MouseAdapter(){
            /*proceso usado para registrar que el mouse entro en el label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               if(Recarga.isEnabled()){
                        mousedentro6=true;    
                        Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarA.png")));
                    
               }
                
            }
            /*proceso usado para registrar que el mouse salio en el label
            */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(Recarga.isEnabled()){
                    
                        mousedentro6=false;    
                        Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarN.png")));
                    
                }
              
            }
            /*proceso usado para cambiar la imagen para ayudar en la animación (no se como explicarlo de otra manera XD)
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(Recarga.isEnabled()){
                    
                        Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarD.png")));
                    
                }
            }
            /*proceso de finalizacion de la progamacion del boton, este verificara los datos ingresados y hara la consulta de ingreso a la base de datos
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(Recarga.isEnabled()){
                    
                        if(mousedentro6){
                            Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarA.png")));        

                                    reportTabla(tabla);
                        }
                        else{
                            Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarN.png")));
                        }
                    
                }
            }
        });
        
        /*programacion del boton Generar Reporte
        */
        botonGenReporte.addMouseListener(new java.awt.event.MouseAdapter(){
            /*proceso usado para registrar que el mouse entro en el label
            */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               if(botonGenReporte.isEnabled()){
                        mousedentro1=true;    
                        botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteA.png")));
                    
               }
                
            }
            /*proceso usado para registrar que el mouse salio en el label
            */
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(botonGenReporte.isEnabled()){
                    
                        mousedentro1=false;    
                        botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteN.png")));
                    
                }
              
            }
            /*proceso usado para cambiar la imagen para ayudar en la animación (no se como explicarlo de otra manera XD)
            */
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if(botonGenReporte.isEnabled()){
                    
                        botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteD.png")));
                    
                }
            }
            /*proceso de finalizacion de la progamacion del boton, este verificara los datos ingresados y hara la consulta de ingreso a la base de datos
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if(botonGenReporte.isEnabled()){
                    
                        if(mousedentro1){
                            botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteA.png")));        

                                    int a=JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea generar el reporte?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                                    if(a==JOptionPane.YES_OPTION){
                                    try {
                                        sacarReporte();
                                    } catch (JRException ex) {
                                                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                            }
                        }
                        else{
                            botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteN.png")));
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
            @Override
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
                            dispose();
                        }
                    }
                    else{
                        botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
                    }
                }
            }
        });
        
        /*Programacion de abrir pdfs en la tabla
        */
        DatosTabla.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso de finalizacion de pdfs; vera si se pulso en un pdf, en caso si, lo abrira automaticamente
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                int id=-1;
                int column = DatosTabla.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / DatosTabla.getRowHeight();
                if (row < DatosTabla.getRowCount() && row >= 0 && column < DatosTabla.getColumnCount() && column >= 0) {
                    id = (int) DatosTabla.getValueAt(row, 0);
                    Object value = DatosTabla.getValueAt(row, column);
                    if (value instanceof JLabel) {
                         ((JLabel) value).doLayout();
                         JLabel boton = (JLabel) value;
                        
                                if (boton.getText()!=null) {
                                 JOptionPane.showMessageDialog(null, "No hay archivo");
                                 } else {

                                       if(column==9){
                                           PdfDAO nose = new PdfDAO("SELECT * FROM exam_lab");
                                          nose.ejecutar_archivoPDF(IDlab[row],1);
                                       }
                                       if(column==10){
                                           PdfDAO nose = new PdfDAO("SELECT * FROM exam_tomo");
                                           nose.ejecutar_archivoPDF(IDTomo[row],2);
                                       }
                                       try {
                                           Desktop.getDesktop().open(new File("new.pdf"));
                                       } catch (Exception ex) {
                                           System.err.println(ex);
                                        }
                               }
                         
                    } else {
                        String name = "" + DatosTabla.getValueAt(row, 1);
                    }
                }
            }
        });
                
        /*Programacion para el cambio de rango a dia
        */
        elegirdia.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso para elegir diario
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Date fecha = new Date();
                DateChosser2.setEnabled(false);
                DateChosser2.setDate(null);
                DateChosser3.setEnabled(false);
                DateChosser3.setDate(null);
                DateChosser1.setEnabled(true);
                DateChosser1.setDate(fecha);
                reportTabla(tabla);
            }
        });
        
        /*Programacion para el cambio de dia a rango
        */
        elegirrangodias.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso para elegir diario
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Date fecha = new Date();
                DateChosser2.setEnabled(true);
                DateChosser2.setDate(fecha);
                DateChosser3.setEnabled(true);
                DateChosser3.setDate(fecha);
                DateChosser1.setEnabled(false);
                DateChosser1.setDate(null);
                reportTabla(tabla);
            }
        });
        
        /*Programacion en los calendarios para que actualize al elegir una fecha
        */
        DateChosser1.addMouseListener(new java.awt.event.MouseAdapter(){
            
            
            /*proceso para elegir diario
            */
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                reportTabla(tabla);
            }
        });
        
        
        pPrincipal.setLayout(null);//preparando el panel para lo que viene XD

        /*se añade todos los elementos al panel    
         */
        pPrincipal.add(logo);
        pPrincipal.add(cabezera1);
        pPrincipal.add(nomempre);

        pPrincipal.add(Titulo);
        pPrincipal.add(Especificacion);
        pPrincipal.add(elegirdia);
        pPrincipal.add(DateChosser1);
        pPrincipal.add(elegirrangodias);
        pPrincipal.add(DateChosser2);
        pPrincipal.add(DateChosser3);
        pPrincipal.add(Recarga);
        pPrincipal.add(Scrol);
        pPrincipal.add(DatosTabla);
        pPrincipal.add(vacio);

        pPrincipal.add(botonGenReporte);
        pPrincipal.add(textoGenReporte);
        pPrincipal.add(botonReportarBug);
        pPrincipal.add(textoReportarBug);
        pPrincipal.add(botoncancelar);
        pPrincipal.add(textocancelar);

        pPrincipal.add(fondo);

        pPrincipal.setSize(1520, 700);//se da el tamaño general al panel
  
  /*se ingresa los datos de cada objeto
  */
    logo.setSize(100,50);
    logo.setLocation(20, 10);
    //logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoEsSalud2.png")));
    logo.setVisible(true);
    
    cabezera1.setSize(590,30);
    cabezera1.setLocation(610, 20);
    cabezera1.setText("Generar Reporte");
    cabezera1.setVisible(true);
    
    nomempre.setLocation(1190, 20);
    nomempre.setSize(340, 30);
    nomempre.setFont(new java.awt.Font("Segoe UI", 1, 18));
    nomempre.setForeground(new java.awt.Color(0, 0, 255));
    nomempre.setText("Seguro Social de Salud \"EsSalud\"");
    nomempre.setVisible(true);
    
    
    
    Titulo.setSize(1200,40);
    Titulo.setLocation(325, 80);
    Titulo.setText("REPORTE DE LOS PACIENTES ATENDIDOS ");
    Titulo.setFont(new java.awt.Font("Dialog", 1, 36));
    Titulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    Titulo.setVisible(true);
    
    
    Especificacion.setSize(250,25);
    Especificacion.setLocation(25, 130);
    Especificacion.setText("Vista previa del reporte final del registro");
    Especificacion.setVisible(true);
    
    elegirdia.setSize(100,30);
    elegirdia.setLocation(300, 125);
    elegirdia.setText("por día");
    elegirdia.setVisible(true);
    elegirdia.setSelected(true);
    
    DateChosser1.setSize(130,30);
    DateChosser1.setLocation(400,125);
    Date Fecha = new Date();
    DateChosser1.setDate(Fecha);
    DateChosser1.setVisible(true);
    
    elegirrangodias.setSize(100,30);
    elegirrangodias.setLocation(600,125);
    elegirrangodias.setText("por rango");
    elegirrangodias.setVisible(true);
    
    DateChosser2.setSize(130, 30);
    DateChosser2.setLocation(700,125);
    DateChosser2.setVisible(true);
    DateChosser2.setEnabled(false);
    
    DateChosser3.setSize(130, 30);
    DateChosser3.setLocation(850, 125);
    DateChosser3.setVisible(true);
    DateChosser3.setEnabled(false);
    
    grupoChosser.add(elegirdia);
    grupoChosser.add(elegirrangodias);
    
    
    Recarga.setSize(35,35);
    Recarga.setLocation(1450, 125);
    Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarDes.png")));
    Recarga.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    Recarga.setVisible(true);
    DatosTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null,null, null, null, null, null, null, null, null},
                {null, null, null, null,null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° de Registro","DNI", "Nombre", "Telefono", "Direccion", "Tipo de prueba",
                "Metodo de seguimiento", "Gravedad", "Nombre de usuario", "Examen de Laboratorio", "Examen de Tomografía", "Fecha de registro"
            }
        )
                {
                    boolean[] canEdit = new boolean [] {
                        false, false, false, false, false, false, false, false, false, false, false, false
                    };

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                }
    );
        /* Damos tamaño a cada columna
        */
        darTamañoColum(DatosTabla, 0, 90);
        darTamañoColum(DatosTabla, 1, 80);
        darTamañoColum(DatosTabla, 2, 190);
        darTamañoColum(DatosTabla, 3, 90);
        darTamañoColum(DatosTabla, 4, 190);
        darTamañoColum(DatosTabla, 5, 90);
        darTamañoColum(DatosTabla, 6, 140);
        darTamañoColum(DatosTabla, 7, 65);
        darTamañoColum(DatosTabla, 8, 110);
        darTamañoColum(DatosTabla, 9, 145);
        darTamañoColum(DatosTabla, 10, 145);
        darTamañoColum(DatosTabla, 11, 125);
        
    
   DatosTabla.getTableHeader().setReorderingAllowed(false);
    
        
    Scrol.setViewportView(DatosTabla);
    Scrol.setSize(1460,240);
    Scrol.setLocation(25, 160);
    Scrol.setVisible(true);
        
    vacio.setSize(1460,120);
    vacio.setLocation(25, 250);
    vacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    vacio.setText("Por el momento, no hay ninguna atencion registrada, espere que se reciba alguna atención y se actualize el sistema");
    vacio.setVisible(true);
        
    
    
    botonGenReporte.setSize(80,80);
    botonGenReporte.setLocation(65, 430);
    botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteDes.png")));
    botonGenReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botonGenReporte.setVisible(true);
    
    textoGenReporte.setSize(100,30);
    textoGenReporte.setLocation(55, 510);
    textoGenReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textoGenReporte.setForeground(new java.awt.Color(225, 225, 255));
    textoGenReporte.setText("Generar Reporte");
    textoGenReporte.setVisible(true);
    
    botonReportarBug.setSize(80,80);
    botonReportarBug.setLocation(655, 430);
    botonReportarBug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reporbugN.png")));
    botonReportarBug.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botonReportarBug.setVisible(false);
    botonReportarBug.setEnabled(false);
    
    textoReportarBug.setSize(100,30);
    textoReportarBug.setLocation(645, 510);
    textoReportarBug.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textoReportarBug.setForeground(new java.awt.Color(225, 225, 255));
    textoReportarBug.setText("Reportar error");
    textoReportarBug.setVisible(false);
    
    botoncancelar.setSize(80,80);
    botoncancelar.setLocation(1315, 430);
    botoncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelarN.png")));
    botoncancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    botoncancelar.setVisible(true);
    
    textocancelar.setSize(80,30);
    textocancelar.setLocation(1315, 510);
    textocancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    textocancelar.setForeground(new java.awt.Color(225, 225, 255));
    textocancelar.setText("Cancelar");
    textocancelar.setVisible(true);
    
    
    
    fondo.setSize(1520, 700);
    fondo.setLocation(0, 0);
    fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoreporte2.jpg")));
    fondo.setVisible(true);
    
    //Thread h1 = new Thread((Runnable) padre);
        tabla.start();
        
    add(pPrincipal);
    
    setVisible(true);
}
    
    
    /*Proceso usado para generar el reporte
    */
    @SuppressWarnings("unchecked")
    public void sacarReporte() throws JRException{
        Connection con = null;
            con = Conección();
            SimpleDateFormat formato=new SimpleDateFormat("YYYY/MM/dd");
        JasperReport reporte = null;
        
        
        if(elegirdia.isSelected()){
            String ruta="src\\Reportes\\ReporteFinal2.jasper";     

        reporte= (JasperReport) JRLoader.loadObjectFromFile(ruta);
       
       //reporte = JasperCompileManager.compileReport("src/Reportes/ReporteFinal2.jrxml");
            Map parametro = new HashMap();
            String fec=formato.format(DateChosser1.getDate());
            parametro.put("Fecha", fec);
            
        JasperPrint imprimir = JasperFillManager.fillReport(ruta,parametro,con);
        
        JasperViewer view = new JasperViewer(imprimir,false);
        
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        view.setVisible(true);
       dispose(); 
        view.toFront();
        }
        if(elegirrangodias.isSelected()){
         String ruta="src\\Reportes\\ReporteFinal3.jasper";
           
        reporte= (JasperReport) JRLoader.loadObjectFromFile(ruta);
        
        //reporte = JasperCompileManager.compileReport("src/Reportes/ReporteFinal3.jrxml");
             Map parametro = new HashMap();
            String fec=formato.format(DateChosser2.getDate());
            parametro.put("Fecha1", fec);
            String fec2=formato.format(DateChosser3.getDate());            
            parametro.put("Fecha2", fec2);
        JasperPrint imprimir = JasperFillManager.fillReport(ruta,parametro,con);
        
        JasperViewer view = new JasperViewer(imprimir,false);
        
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setVisible(true);
       dispose(); 
        view.toFront();
        }
        
        
        
    }
    
    /*Proceso usado para dar tamaño unico a la columan de una tabla
    */
    public static void darTamañoColum(JTable tabla, int columna, int tamaño){
        tabla.getColumnModel().getColumn(columna).setMinWidth(tamaño);
        tabla.getColumnModel().getColumn(columna).setPreferredWidth(tamaño);
        tabla.getColumnModel().getColumn(columna).setMaxWidth(tamaño);
        
        
    }
    
    /*Proceso auxiliar para la recarga
    */
    @SuppressWarnings("deprecation")
    public void reportTabla(ReporteTabla ant){
    try{
        ReporteTabla nuevo = new ReporteTabla();
    ant=nuevo;
    ant.start();
    }
    catch(Exception e){
            System.err.println(e);
            }
    }
}
