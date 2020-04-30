package bean_11_consultaSQL;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author Fermin
 */

  //este control nos representa una consulta sql asociada a una base de datos
// representada por el control FER_ConectorBD asociado mediante la propiedad AA_FER_ConectorBD en el 
// editor de propiedades.


public class FER_ConsultaSQL implements Serializable {
    
    private static final long serialVersionUID = 1L;// para no tener problemas de cambio de versión al ser serializable.
    
    private FER_ConectorBD miFER_ConectorBD;
    
   transient PropertyChangeListener miEscuhadorDeConectada; // para escuchar
    
    private final PropertyChangeSupport propertySupport; // para que nos escuchen.
  
    transient private java.sql.Statement comandoSql; 
    
    
    private String[] AA_titulos=new String[0]; // titulos de las columnas... de momento ninguno
    private ArrayList<Object[]> filas; // filas de arrays de objetos = va a contener las filas que he encontrado en la base de datos : ACUMULA LOS DATOS LEIDOS.
   
    private boolean miAA_abierta; // indica si esta abierta nuestra consulta sql.
    private String consultaSELECT; // esta es nuestra consulta SELECT a ejectuar
    private int miAA_numeroDeColumnas=0; // numero de columnas que tiene nuestra consulta. --> propiedad readonly
    
    // test errores
    public boolean hayError=false;
    public String mensajeError;
    
    private boolean abiertaInicial=false;
    
    
    public FER_ConsultaSQL()
    {     
        this.filas = new ArrayList();
        this.miFER_ConectorBD=null;
        this.comandoSql=null;
        this.consultaSELECT=""; // no hay
        
        propertySupport = new PropertyChangeSupport(this); // Publica mis cambios. Gestiona mi lista de distribución.
        
        // mi escuchador..
        this.miEscuhadorDeConectada=  new PropertyChangeListener() // definimos el objeto que atiente al evento.. algo parecido a los botones, pero ahora para este tipo de objeto.
                                                  {                                                                                                                                                            
                                                    @Override
                                                     public void propertyChange(PropertyChangeEvent evt) { cambioElEstadoDeLaConexionBD(evt);  }                                               
                                                  };
                                                      
    }
     
     // **************** ASIGNAR FER_CONECTORDB**********************************************
     
    public FER_ConectorBD getAA_FER_ConectorBD() {      return miFER_ConectorBD;  }

    public void setAA_FER_ConectorBD(FER_ConectorBD AA_FER_ConectorBD) {
        
           //  si estaba conectado a otra conectrBD lo quitamos de los eventos anteriors y lo ponemos en el nuevo.
       //   JOptionPane.showMessageDialog (null,"3 asignar FER_ConectorDB dentro de FER_CONSULTASQL\nYO soy "+this.toString());  
        if ( this.miFER_ConectorBD != null)  this.miFER_ConectorBD.removePropertyChangeListener(this.miEscuhadorDeConectada);
        
        
        
        
                if (this.miFER_ConectorBD!=null) // para cuando en el editor de propiedades pone <Ninguna> nos llegaría un null
          {
            this.miFER_ConectorBD.addPropertyChangeListener(this.miEscuhadorDeConectada);         // decirle a conectorBD que yo estoy pendiente de lo que le pasa.   
            if ( this.abiertaInicial) 
            {
                        this.setAA_abierta(true);
                        this.abiertaInicial=false;

            }
                    
        } 
        
    
    }
 
       // ver que pasa cuando cambia el estado de conectada/no conectada a la base de datos el conector FER_ConectorDB 
    
   public void cambioElEstadoDeLaConexionBD(PropertyChangeEvent evt)  // ejecutara cada vez que el objeto asociado (en este caso conexion a base de datos) cambie su estado de conectada o no
    {
      synchronized (this)
       {  
        if ( CONSTANTES.CONEXION_BASE_DATOS.equals(evt.getPropertyName()) ) // Solo atiendo a la que me interesa 
          {   
          
                this.hayError=false;
                boolean abierta= (boolean) evt.getNewValue();
                
                // si tenemos la consulta abierta (se ven los datos en la rejilla de datos y nos avisan que
                // la conexión con la base de datos se ha cerrado, entonces cerramos nuestra consulta
                // 
                if ( this.miAA_abierta && !abierta)  this.setAA_abierta(abierta);
                
                // si quiremos que si se abre o se cierra la conexión de la base de datos 
                // se abra la consulta (ejecute) o se cierre , sustituir la linea anterior por la siguiente:
                
                       // this.setAA_abierta(abierta); 
           
          } 
       } 
    }




   // ************************** la parte de comunicar que hemos cambiado algo. ***********************************************
    
    
    public void addPropertyChangeListener(PropertyChangeListener listener) { propertySupport.addPropertyChangeListener(listener);   }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {  propertySupport.removePropertyChangeListener(listener);   }
   
    
    // ************************** GET Y SET  de las propiedades  
    
     public String getConsultaSELECT()  { return consultaSELECT; }

     public void setConsultaSELECT(String consultaSELECT) {  this.consultaSELECT = consultaSELECT;  }
    
    
   

    public boolean isAA_abierta() {return miAA_abierta;  }

    // ************ que hacer al abrir o cerrar la consulta ..
    private boolean puedoAbrirla()
    {
          if ( this.miFER_ConectorBD == null ) // ver que tenemos conector asociado
                        {
                            this.hayError = true;
                            this.mensajeError= "*Error FER_ConsultaSQL: \nNo tiene asignado un objeto AA_FER_ConectorBD";
                            JOptionPane.showMessageDialog (null,this.mensajeError);
                            this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_SQL_ABIERTA, this.miAA_abierta,false); // avisar que la propiedad miAA_abierta ha cambiado.
                            this.miAA_abierta=false;
                            this.abiertaInicial=true;
                            return false;
                        }
         if ( !this.miFER_ConectorBD.isAA_Conectada()) // que la base de datos este conectada
                        {
                            this.hayError = true;
                            this.mensajeError= "Error FER_ConsultaSQL: \nAA_FER_ConectorBD con conexión cerrada";
                            JOptionPane.showMessageDialog (null,this.mensajeError);
                            this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_SQL_ABIERTA, this.miAA_abierta,false); // avisar que la propiedad miAA_abierta ha cambiado.
                             this.miAA_abierta=false;  
                            return false;
                        }
          if ( this.consultaSELECT.equals("")) // no hay consulta
                        {
                            this.hayError = true;
                            this.mensajeError= "Error FER_ConsultaSQL: \nNo tiene definida consulta SELECT";
                        //    JOptionPane.showMessageDialog (null,this.mensajeError);
                            this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_SQL_ABIERTA, this.miAA_abierta,false); // avisar que la propiedad miAA_abierta ha cambiado.
                            this.miAA_abierta=false;  
                            return false;
                        }
       return true;                         
    }
    
    //
    private void abrirConsulta() throws SQLException
    {   
         this.comandoSql=this.miFER_ConectorBD.daConexion().createStatement(); // creamos el objeto para dar ordendes SQL a la base de datos.                        
         ResultSet filasTabla; // filas de la base de datos entradas. PROPIEDAD solo lectura mediante un get
         filasTabla= this.comandoSql.executeQuery(this.consultaSELECT);  // hacemos la consulta
         
         
         // ahora cargamos los titulos de las columnas
         
          ResultSetMetaData informacionDatosEncontrados;  // ESTE objeto contiene la definicion de las columnas que 
                                                // que hay en una consulta de datos determinada.
                      
          informacionDatosEncontrados = filasTabla.getMetaData(); // obtenemos la informacion de como y cuantas son las columnas obtenidas de la consulta sql
          
           int old= this.miAA_numeroDeColumnas ;      
           this.miAA_numeroDeColumnas = informacionDatosEncontrados.getColumnCount(); //numero de columnas que tenemos.
           
           
           this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_NUMERO_COLUMNAS, old,this.miAA_numeroDeColumnas); // avisar que la propiedad ha cambiado.                      
           
           this.AA_titulos = new String[this.miAA_numeroDeColumnas]; // array para poner los titulos de las columnas
         
           
           // AHORA CARGAMOS LAS FILAS EN NUESTRO ARRAYLIST 
           
           int i;
                                 // cargamos los titulos de las columnas
           for (i=0 ; i < this.miAA_numeroDeColumnas; i++) 
                this.AA_titulos[i] = informacionDatosEncontrados.getColumnLabel(i+1) ; // getColumLabel es de 1 en adelante
          
           this.filas.clear(); // limpiamos las filas anteriors.
           while(filasTabla.next() ) // mientras consiga una nueva fila (se puede mover a la siguiente ) = obtener siguiente fila.
            {
                // cargamos la fila en el arraylist.
                Object[] filaNueva = new Object[this.miAA_numeroDeColumnas]; // creamos un array de objetos del tamaño de las columnas encontradas
                for (i=0; i < this.miAA_numeroDeColumnas; i++)  filaNueva[i]= filasTabla.getString(i+1); // por posicion (el +1 porque getString empieza a contar de 1;                               
                this.filas.add(filaNueva);
            }           
           
           
           this.miAA_abierta=true;
           this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_SQL_ABIERTA, false,true); // avisar que la propiedad miAA_abierta ha cambiado.
           
           
           this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_NUMERO_FILAS, 0, this.filas.size()); // avisar que la propiedad numero de filas ha cambiado   
           
           
                      
           
      }               
    
    
    private void cerrarConsulta() throws SQLException
    {   
           this.comandoSql.close(); // cerrar 
           int oldFilas=this.filas.size();
           int old= this.miAA_numeroDeColumnas ;      
           this.miAA_numeroDeColumnas = 0; //numero de columnas que tenemos seran cero puesto que no hay nada
           
           this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_NUMERO_COLUMNAS, old,this.miAA_numeroDeColumnas); // avisar que la propiedad ha cambiado.          
           this.AA_titulos= new String[0]; // no hay columnas..
           
           this.comandoSql=null;           // liquidamos este objeto de consultas
           this.miAA_abierta=false;  // indicamos que esta cerrado
           
           this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_SQL_ABIERTA, true,false); // avisar que la propiedad miAA_abierta ha cambiado.
           
           // no hay filas
          this.filas.clear();// no filas..
                  
          this.propertySupport.firePropertyChange(CONSTANTES.CONSULTA_NUMERO_FILAS, oldFilas,0) ; // avisar que la propiedad numero de filas ha cambiado   
           
           
    }
    
    
    public void setAA_abierta(boolean quieroAbrirla)
    {
        this.hayError=false;
        if ( this.miAA_abierta== quieroAbrirla) return ; //nada que hacer..
        
        try 
           {
             if ( quieroAbrirla)
                
                {        
                   if ( !this.puedoAbrirla() ) return;                                                                          
                   this.abrirConsulta();
                   return;
                }  
               
             // quiero cerrarla
          
             if ( this.comandoSql!=null) this.cerrarConsulta();
                                                          
           }
                
        catch (SQLException ex) 
            {
               this.hayError = true;
               this.mensajeError= ex.getMessage();
               JOptionPane.showMessageDialog (null,"Error FER_SQLconsulta: \n"+this.mensajeError); 
               throw new RuntimeException(ex) ; // nos vamos dando error
            }
    }

    
    //************** Es una propiedad indexada , por eso el lio de los subindices...
    public String[] getAA_titulos() { return this.AA_titulos;  }

   

    public String getAA_titulos(int indice) {  //TENDRE : AA_QueDigo en la ventana de propiedades.
        if ( (indice >= 0) && (indice <= this.AA_titulos.length))  return this.AA_titulos[indice];
        else return "";
    }
   

   
   
    public int getAA_numeroDeColumnas() {  return miAA_numeroDeColumnas;   } // para tener el numero de columnas de nuestra consulta

    public ArrayList<Object[]> daFilas() {  return filas;   } // da las filas encontradas en la consulta cuando esta abierta
    
    // en el destructor nos aseguramos de cerrar la consulta y de desconectarnos de la lista de seguimiento
    
    @Override
    protected void finalize() throws SQLException, Throwable
    {
        this.setAA_abierta(false);
        if ( this.miFER_ConectorBD != null)  this.miFER_ConectorBD.removePropertyChangeListener(this.miEscuhadorDeConectada);
        super.finalize();
    }
    
    public int getAA_numeroFilas() { return this.filas.size(); } //saber el numero de filas que he obtenido con la consulta
}
