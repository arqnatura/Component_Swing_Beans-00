package bean_11_consultaSQL;



import java.beans.*;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fermin
 */
public class FER_DBGRID extends JTable{ // implements Serializable  { NO HACE FALTA , puesto que los componentes ya son serializables
    /*
    
     Al estender de JTable ya no hace falta los metodos addPropertyChangeListene removePropertyChangeListener puesto que ya los tiene JTable
     tampoco hace falta indicar que implementa Serializable puesto que ya lo es JTable
    
    */
    private static final long serialVersionUID = 1L;// para no tener problemas de cambio de versión al ser serializable.
       
    private final DefaultTableModel modelo;
    transient private FER_ConsultaSQL  miFER_ConsultaSQL; // consulta sql asociada
    
    
    transient PropertyChangeListener miEscuhadorDeAbiertaConsultaAsociada; // para escuchar
    
    
    public FER_DBGRID() 
    { 
        this.miFER_ConsultaSQL=null;
        
        // mi escuchador..
        this.miEscuhadorDeAbiertaConsultaAsociada=  new PropertyChangeListener() // definimos el objeto que atiente al evento.. algo parecido a los botones, pero ahora para este tipo de objeto.
                                                  {                                                                                                                                                            
                                                     @Override
                                                       public void propertyChange(PropertyChangeEvent evt) {cambioEstadoDeLaConsulta(evt) ;}                                                                                                     
                                                  };
         this.modelo = new DefaultTableModel() 
                           {                   //  EL CONTENIDO DE LA REJILLA NO SE PUEDE MODIFICAR POR EL OPERADOR
                                @Override
                                public boolean isCellEditable(int fila, int columna) 
                                    {
                                return false; //Con esto conseguimos que la tabla no se pueda editar
                               }
                             };     
         this.setModel(this.modelo); // ahora este es nuevo modelo...
    }

    public void cambioEstadoDeLaConsulta(PropertyChangeEvent evt)                                             
    {    
      
        if ( !evt.getPropertyName().equals(CONSTANTES.CONSULTA_SQL_ABIERTA)) return; // solo me interesa cuando es esta propiedad
      
        
      
       this.modelo.getDataVector().removeAllElements(); // borramos todos los elementos anteriores..
           
        boolean abierta= (boolean) evt.getNewValue();
        if (!abierta)  // nada mas que hacer.
                       {   
                            this.modelo.fireTableDataChanged(); // decimos que han cambiado los datos para que se repinte : la pintara vacia
                            return;
                       } 
       
            // hay que cargar los datos que la consulta.
      
      
        this.modelo.setColumnIdentifiers( this.miFER_ConsultaSQL.getAA_titulos() );// los nuevos titulos de las columnas..
                    
        ArrayList<Object[]> filas= this.miFER_ConsultaSQL.daFilas(); // me traigo las filas del ResultSet
        
        
        
        int n=filas.size();                
        int i;
        for ( i=0 ; i < n; i++) this.modelo.addRow(filas.get(i));
            
           
        this.modelo.fireTableDataChanged(); // decimos que han cambiado los datos para que se repinte 
      
        
    }
                                                  
    public FER_ConsultaSQL getAA_FER_ConsultaSQL() { return miFER_ConsultaSQL;
    }

    public void setAA_FER_ConsultaSQL(FER_ConsultaSQL AA_FER_ConsultaSQL) {
      
                //  si estaba conectado a otra consultaSQL lo quitamos de los eventos anteriors y lo ponemos en el nuevo.
        
        if ( this.miFER_ConsultaSQL != null)  this.miFER_ConsultaSQL.removePropertyChangeListener(this.miEscuhadorDeAbiertaConsultaAsociada);
          
        
         if (AA_FER_ConsultaSQL!=null)  {   AA_FER_ConsultaSQL.setAA_abierta(false);  } //sino toca las narices..
        
        this.miFER_ConsultaSQL=AA_FER_ConsultaSQL;
        
        if (this.miFER_ConsultaSQL!=null)
             this.miFER_ConsultaSQL.addPropertyChangeListener(this.miEscuhadorDeAbiertaConsultaAsociada);    // decirle a que yo estoy pendiente de lo que le pasa. 
        
       
    }
    
   
}
