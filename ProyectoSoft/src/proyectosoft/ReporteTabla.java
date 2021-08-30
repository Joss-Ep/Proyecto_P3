
package proyectosoft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static proyectosoft.FinalizarReporte.DatosTabla;
import static proyectosoft.FinalizarReporte.Scrol;
import static proyectosoft.Procedimientos.Conección;
import static proyectosoft.FinalizarReporte.*;

public class ReporteTabla extends Thread{ 
        Thread h2;
        byte[] archivopdf;
    @Override
    public void run() {
    tiempo=300000;
    while(h2 == h2) {
   
    Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarDes.png")));  
  ListaPacientes();
  
    Recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recargarN.png")));
  try {
   Thread.sleep(tiempo);
   
  }catch(InterruptedException e) {}
  
 }
    
    
    }
   /*proceso usado para descargar todos los pacientes ya registrados anteriormente
    */
    public void ListaPacientes(){
      try{
          
            Connection con = null;
            con = Conección();
    PreparedStatement ps = null;
    ResultSet rs=null;
    DatosTabla.setDefaultRenderer(Object.class, new imgTabla());
    DefaultTableModel modelo = new DefaultTableModel(){
                    
                    boolean[] canEdit = new boolean [] {
                        false, false, false, false, false, false, false, false, false, false, false, false
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                };
    
    
    IDlab = new String[1000];
    IDTomo= new String[1000]; 
 
    
    String sql="SELECT * FROM registro";
     Date Fecha1 = new Date();
     Date Fecha2 = new Date();
        SimpleDateFormat formato=new SimpleDateFormat("YYYY/MM/dd");
    if(elegirdia.isSelected()){
        Fecha1=DateChosser1.getDate();

        sql="call vista1('"+formato.format(Fecha1)+"');";
    }
    if(elegirrangodias.isSelected()){
        Fecha1=DateChosser2.getDate();
        Fecha2=DateChosser3.getDate();
        
        sql="call vista2('"+formato.format(Fecha1)+"', '"+formato.format(Fecha2)+"');";
    }
    ps= con.prepareStatement(sql);
    rs=ps.executeQuery();  
      
    modelo.addColumn("N° de Registro");
    modelo.addColumn("DNI");
    modelo.addColumn("Nombre");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Direccion");
    modelo.addColumn("Tipo de prueba");
    modelo.addColumn("Metodo de seguimiento");
    modelo.addColumn("Gravedad");
    modelo.addColumn("Nombre de usuario");
    modelo.addColumn("Examen de laboratorio");
    modelo.addColumn("Examen de tomografia");
    modelo.addColumn("Fecha de registro");
    
        ImageIcon icono = null;
            icono = new ImageIcon(getClass().getResource("/Imagenes/PDF2N.png"));
        
    int cont=0;
    
    while(rs.next()){
        
        Object filas[] = new Object[12];
        filas[0]=rs.getObject(1);
        filas[1]=rs.getObject(2);
        filas[2]=rs.getObject(3)+" "+rs.getObject(4)+" "+rs.getObject(5);
        filas[3]=rs.getObject(6);
        filas[4]=rs.getObject(7);
        filas[5]=rs.getObject(8);
        filas[6]=rs.getObject(9);
        filas[7]=rs.getObject(10);
        filas[8]=rs.getObject(11);
        if (!rs.getObject(12).equals("no tiene")) {
                    filas[9] = new JLabel(icono);
                    IDlab[cont]=rs.getString(12);
                } else {
                    filas[9] = rs.getObject(12);
                }
        
        if (!rs.getObject(13).equals("no tiene")) {
                    filas[10] = new JLabel(icono);
                    IDTomo[cont]=rs.getString(13);
                } else {
                    filas[10] = rs.getObject(13);
                }
        Date Fecha = new Date();
            Fecha=rs.getDate(14);
            //usado para corregir error de impresion de dia
        Date Real = new Date(Fecha.getTime()+(1000*60*60*24));
        
        SimpleDateFormat formato2=new SimpleDateFormat("dd-MM-YYYY");
        filas[11]=formato2.format(Real);
        
        
    cont++;
    
        modelo.addRow(filas);
    
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
     
     DatosTabla.setModel(modelo );
     DatosTabla.setRowHeight(40);
    }
    if(cont!=0){
        Scrol.setVisible(true);
        vacio.setVisible(false);
        botonGenReporte.setEnabled(true);
        botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteN.png")));
    }else{
        Scrol.setVisible(false);
        vacio.setVisible(true);
        botonGenReporte.setEnabled(false);
        botonGenReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/genreporteDes.png")));
    }
     }catch(Exception e){
          JOptionPane.showMessageDialog(null, "No se ha registrado ninguna atención todavia", "Atención", JOptionPane.ERROR_MESSAGE);
          System.err.println(e);
        }
     
      
  }
    
    /*variables auxiliares para el apoyo en la descarga de datos
    */
    public static String IDlab[] = new String[1000];
    public static String IDTomo[] = new String[1000]; 
    
    public static int tiempo;//usado para poder cambiar el tiempo de actualizacion
    
}
