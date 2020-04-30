package bean_11_consultaSQL;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author angel
 */
    

public class JAreaEscucha extends JTextArea implements Serializable   {
 
        private JTextArea consultaQueSIgo=null; // a quien estoy escuchando!!!
        //private JCheckBox checkBoxAlQueSIgo=null; // a quien estoy escuchando!!!
        private String propiedadAcomprobarSiCambia="";
            
        private boolean verCambiosEnElContenedor=false; // para ver cambios del contenedor el objeto al que estoy escuchando
        
    
       /**
        *  Mi escuhador de que una propiedad ha cambiado. <i>"sere un seguidOR de otro objeto, como en FACEBOOK"</i>
        */
        PropertyChangeListener MiEscuchador=null; 
     
        
    public JAreaEscucha ()  // 2º  debe tener un constructor sin paremetros = por defecto
        {
          this.setText("Estoy escuchando");
          this.setBounds(1,1,400,100);
       
          this.MiEscuchador=  new PropertyChangeListener() // definimos el objeto que atiente al evento.. algo parecido a los botones, pero ahora para este tipo de objeto.
                                                  {                                                                                                                                                            
                                                    @Override
                                                     public void propertyChange(PropertyChangeEvent evt) {  cambioDeUnaPropiedad(evt);  }                                               
           
                                                  };
       
       }
            
   /////////////SET AA_CheckBoxAsociado 
    
    public void setAA_JAreaAsociada ( JTextArea b)// se llama automaticamente cuando definimos en el diseñador a que checkbox nos asociamos.
                                        // si estamos quitando el asociado (a ninguno) nos llega un nulo.
        {
  
          //  si estaba escuchando a otro checkbox , me quito de la lista de distribución de ese checkbox.
           
          if ( this.consultaQueSIgo != null)  this.consultaQueSIgo.removePropertyChangeListener(this.MiEscuchador);
                
           this.consultaQueSIgo=b;
        
       
         // le decimos que me apunte a su lista de seguidores. Si es que no es nulo.
         
          if ( this.consultaQueSIgo != null)    this.consultaQueSIgo.addPropertyChangeListener(this.MiEscuchador);     
                     
                   
              
        }
        
     /////// GET AA_CheckBoxAsociado 
        
    public JTextArea getAA_JAreaAsociada () { return this.consultaQueSIgo;}
        
        
    // MÉTODO que se llamada desde "mi escuchador" cuando el objeto que "sigo" cambia una propiedad y publica su cambio 
        
    public void cambioDeUnaPropiedad(PropertyChangeEvent evt) 
    {
       
        synchronized (this) // para que solo se ejecute una vez este código. Solo se ejecuta en un hilo a la vez.
              /*
                Para saber más sobre synchronized:
            
                  http://tutorials.jenkov.com/java-concurrency/synchronized.html#synchronized-instance-methods
                  http://tutorials.jenkov.com/java-concurrency/race-conditions-and-critical-sections.html
            
            */
        {      
           // si no hemos definido una propiedad en particular a comprobar si cambia o si es la que estamos buscando ....
             
              
              if ( this.propiedadAcomprobarSiCambia.equals("") ||  this.propiedadAcomprobarSiCambia.equals( evt.getPropertyName()))
              { 
                
                JComponent e=  (JComponent) evt.getSource(); // quien emitio el mensaje de que se ha cambiado.
              //  if (e==null) { this.setText("NULO");return;}
                
                 // si es un cambio en el contenedor y no estoy interesado (verVerCamiosEnElContenedor == TRUE) . me voy
     
                if  ( !this.verCambiosEnElContenedor && evt.getPropertyName().equals("ancestor"))  return;            
                
                // añadimos la información en al mi texto.
                this.append("\nCambio en objeto "+e.getClass().getName()+
                             "\n\nObjeto que genera el evento es :\n   Un: "+e.getClass().getName()+"\nCon Nombre: "+e.getName()
                              + "\n\nPropiedad cambiada: "+ evt.getPropertyName()+"\nValor nuevo: '"+daValor(evt)
                
                );
               
                
         } //sync..
      }
        
    } 
   
    private String daValor( PropertyChangeEvent evt) // ponerlo bonito!! y ojo con los nulos!!
    { 
        Object o=evt.getNewValue();
        if ( o ==null) return "** nulo **";
     
     try
           { String s=o.toString();
             return  s.replace(",",",\n");
           }
     catch (  Exception e)  {  return e.getMessage();
                     
              }
    
        
    }
  
 /**
     * @return the propiedadAcomprobarSiCambia
     */
    public String setAA_PropiedadAcomprobarSiCambia() {
        return propiedadAcomprobarSiCambia;
    }

    /**
     * @param propiedadAcomprobarSiCambia the propiedadAcomprobarSiCambia to set
     */
    public void setAA_PropiedadAcomprobarSiCambia(String propiedadAcomprobarSiCambia) {
        this.propiedadAcomprobarSiCambia = propiedadAcomprobarSiCambia;
    }   
  
    
    public void setAA_borraTexto(boolean a) // esto es solo para tener la opción de borrar el texto desde el editor de propiedades
    {
        this.setText("");
    }

    /**
     * @return the verCambiosEnElContenedor
     */
    public boolean isAA_VerCambiosEnElContenedor() {
        return verCambiosEnElContenedor;
    }

    /**
     * @param verCambiosEnElContenedor the verCambiosEnElContenedor to set
     */
    public void setAA_VerCambiosEnElContenedor(boolean verCambiosEnElContenedor) {
        this.verCambiosEnElContenedor = verCambiosEnElContenedor;
    }
}// class
