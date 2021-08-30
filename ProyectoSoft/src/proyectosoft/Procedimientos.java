
package proyectosoft;


import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Procedimientos {
    
    
    /*Procedimiento usado para conectar a la base de datos
    */
    public static Connection Conección(){
        Connection   con=null;
	String url  = "jdbc:mysql://localhost:3306/bdcascov?useTimezone=true&serverTimezone=UTC";//coneccion al MYSQL server
		String user = "root";//usuario del servidor
		String pass = "admin123";//contraseña del servidor
		try {
			con = DriverManager.getConnection(url, user, pass); //coneccion del driver java con MYSQL server
		} catch (SQLException e) {
                    /*en caso de que falle la coneccion se imprimira en el sistema
                    */
			                 System.err.println("Hubo un error al conectarse con la base de datos");
                                         System.err.println(e);
		}
        return con;
    }
    
    /* procedimiento usado para codificar a md5
    *input es la cadena de caracteres que se convertira a MD5
     */
    public static String getMD5(String input) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);

        while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
        }
        return hashtext;
    }
    catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
 }
    
    /*proceso usado para saber si el caracter es un numero
    *a es el caracter a comparar
    */
    public static boolean PrueNumString(String a){
        boolean aux=false;//booleano auxiliar
        
        if(a.equals("0") || a.equals("1") || a.equals("2") || a.equals("3") || a.equals("4") || a.equals("5") || a.equals("6") || a.equals("7") || a.equals("8") || a.equals("9")){
            aux=true;
        }
            
        return aux;
    }
    
    
    /*proceso usado para comprobar si la tecla tecleada es un numero
    *evt es el evento de tecla presionada
    */
    public static boolean pruebaNum(java.awt.event.KeyEvent evt){
        boolean aux=false;
        if(evt.getKeyChar()==KeyEvent.VK_0 || evt.getKeyChar()==KeyEvent.VK_1 || evt.getKeyChar()==KeyEvent.VK_2 || evt.getKeyChar()==KeyEvent.VK_3 || evt.getKeyChar()==KeyEvent.VK_4 || evt.getKeyChar()==KeyEvent.VK_5 || evt.getKeyChar()==KeyEvent.VK_6 || evt.getKeyChar()==KeyEvent.VK_7 || evt.getKeyChar()==KeyEvent.VK_8 || evt.getKeyChar()==KeyEvent.VK_9 )
        {
            aux=true;
        }
        return aux;
    }
    
}
