
package proyectosoft;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static proyectosoft.Procedimientos.Conección;

//clase usada para manejar el pdf
public class PdfDAO {
    
    private String sq;//variable auxiliar de uso privado en la clase

    /*Main de la clase
    *algo query usado para cada tabla de la base de datos
    */
    public PdfDAO(String algo){
        sq=algo;
    }
     /*Metodo listar
    */
    public ArrayList<PdfVO> Listar_PdfVO() {
        ArrayList<PdfVO> list = new ArrayList<PdfVO>();
        Connection conec = Conección();
        String sql = sq;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PdfVO vo = new PdfVO();
                vo.setCodigopdf(rs.getInt(1));
                vo.setArchivopdf(rs.getBytes(2));
                list.add(vo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception ex) {
            }
        }
        return list;
    }

    /*Metodo agregar
    */
    public void Agregar_PdfVO(PdfVO vo) {
        Connection conec = Conección();
        String sql="";
        if(sq.equals("SELECT * FROM exam_lab")){ 
        sql = "call IexamLab(?,?);";
        }
        if(sq.equals("SELECT * FROM exam_tomo")){
        sql = "call IexamTomo(?,?);";
        }
        PreparedStatement ps = null;
        try {
            ps = conec.prepareStatement(sql);
            ps.setInt(1, vo.getCodigopdf());
            ps.setBytes(2, vo.getArchivopdf());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                conec.close();
            } catch (Exception ex) {
            }
        }
    }

    
    //Permite mostrar PDF contenido en la base de datos
    public void ejecutar_archivoPDF(String id, int tabla) {
        Connection con = Conección();
        PreparedStatement ps = null;
        ResultSet rs = null;
        byte[] b = null;

        try {
            String aux= "";
            String aux2="";
            String aux3="";
            if(tabla==1){
                aux = "exam_lab";
                aux2= "ID_Lab";
                aux3= "PDFlab";
            }        
            if(tabla==2){
                aux="exam_tomo";
                aux2="ID_Tomo";
                aux3= "PDFtomo";
            }
            ps = con.prepareStatement("SELECT "+aux3+" FROM "+aux+" WHERE "+aux2+" = "+id + ";");
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                b =  rs.getBytes(1);
            }
            InputStream bos = new ByteArrayInputStream(b);

            int tamanoInput = bos.available();
            byte[] datosPDF = new byte[tamanoInput];
            bos.read(datosPDF, 0, tamanoInput);

            OutputStream out = new FileOutputStream("new.pdf");
            out.write(datosPDF);
            //abrir archivo
            out.close();
            bos.close();
            ps.close();
            rs.close();
            
            
        } catch (IOException | NumberFormatException | SQLException ex) {
            System.out.println("Error al abrir archivo PDF " + ex.getMessage());
        }
    }
    
}
