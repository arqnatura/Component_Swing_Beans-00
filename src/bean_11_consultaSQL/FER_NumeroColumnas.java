package bean_11_consultaSQL;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 * /este control sirve para comprobar que el texto que muestra se cambia segun se ponga el valor true o false a un nuevo objeto que he creado, que no es un camponente
 * @author Fermin
 */




public class FER_NumeroColumnas extends JLabel implements Serializable {
    
   
    
    private FER_ConsultaSQL objeto=null;
    
    /**
     *  Mi escuhador de que una propiedad ha cambiado.
     */
    PropertyChangeListener MiEscuhador; 
    
    public FER_NumeroColumnas()
    {
        this.setText("Número de columnas:_ ? ");
       
        
        
        
        this.MiEscuhador=  new PropertyChangeListener() // definimos el objeto que atiente al evento.. algo parecido a los botones, pero ahora para este tipo de objeto.
                                                  {                                                                                                                                                            
                                                    @Override
                                                     public void propertyChange(PropertyChangeEvent evt) {  cambioElEstado(evt);  }                                               
                                                  };
                                                      
    }

   /**
    * Se ejecutara cada vez que el objeto asociado  cambie su estado de SI/NO 
   */
     
   public void cambioElEstado(PropertyChangeEvent evt) 
    {
       
       synchronized (this)  
        { if ( CONSTANTES.CONSULTA_NUMERO_COLUMNAS.equals(evt.getPropertyName()) ) // Solo atiendo a la que me interesa        
               {
                    int columnas= (int) evt.getNewValue();
                    this.setText("Número de columnas:;  "+columnas);
                  
                }    
        } 
    }
     
    public FER_ConsultaSQL getAA_ConsultaSQL() { return this.objeto; }
    
/**
 * A partir de ahora seré seguidor de este FER_NO_SOY_UN_COMPONENT
 * @param nuevoObjetoSiNo  para asignar un objeto  al que voy a seguir
 * 
 */
    public void setAA_ConsultaSQL( FER_ConsultaSQL nuevoObjetoSiNo) {
        
           //  si estaba conectado a otra lista de distribución,  lo quitamos de los eventos anteriors y lo ponemos en el nuevo.
           
        if ( this.objeto != null)  this.objeto.removePropertyChangeListener(this.MiEscuhador);
        
        
        this.objeto=nuevoObjetoSiNo; 
        
           // le decimos que me apunte a su lista de seguidores.
           
        if (this.objeto!=null)  this.objeto.addPropertyChangeListener(this.MiEscuhador);       
    }
    
    
    
    
    
}
