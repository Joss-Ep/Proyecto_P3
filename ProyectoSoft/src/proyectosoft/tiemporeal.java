
package proyectosoft;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static proyectosoft.principal.lbHora;
import static proyectosoft.Ingreso.correhora;
import static proyectosoft.Ingreso.SaludLabel;

public class tiemporeal extends Thread{
    public static String hora,minutos,segundos,ampm;//variable usada para expresar la hora
    public static int horanum;//viariable usada para poder compara con otros ints
        Calendar calendario;// variable usada para manejar el tiempo otorgado por el sistema  
        Thread h2;//hilo auxiliar
        
    @Override
    public void run() {
    
    while(h2 == h2) {
     
  calcula();//llamamos al metodo calcula
  /*Comprobamos si esta disponible el label de hora exacta o el de saludo para colocar el texto
  */
  if(correhora){
  lbHora.setText(hora + ":" + minutos + ":" + segundos + " "+ampm);//lo colocamos en el label de hora exacta la hora exacta
  }
  else{
      /*vemos que tipo de saludo debemos dar de acuerdo a la hora
      */
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
      
      SaludLabel.setText(saludo);//lo colocamos en el label de saludo el saludo
  }
  try {
   Thread.sleep(1000);// le damos una pausa de 1 segundo para que vaya pasando por segundos
  }catch(InterruptedException e) {}
  
 }
    
    
    }
    
    /*Procedimiento para poder obtener la hora del sistema y ajustarlos a las variables definidas anteriormente
    */  
    public void calcula () {        
 calendario = new GregorianCalendar();//definimos el tipo de calendario
Date fechaHoraActual = new Date(); //obtenemos la hora del sistema
calendario.setTime(fechaHoraActual);//lo definimos al calendario
ampm = calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM"; //preguntamos y colocamos en la variable ampm si es AM o PM

/*Comparamos para poder restar las 12 horas en caso de que sea tarde(15:00-> 3:00 PM)
*/
if(ampm.equals("PM")){
 int h = calendario.get(Calendar.HOUR_OF_DAY)-12;
    hora = h>9?""+h:"0"+h;
    horanum=h;
}else{
   hora = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY); 
   horanum=calendario.get(Calendar.HOUR_OF_DAY);
}
   minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
   segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND); 
   if(hora.equals("00")){
       hora="12";
   }
}
}
