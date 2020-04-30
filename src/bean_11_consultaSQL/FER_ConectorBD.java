package bean_11_consultaSQL;


import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Control de conexion con una base de datos. AL usarlo, para ver que se ha puesto en el jframe, usar la ventana NAVEGADOR
 * @author Fermin
 */
public class FER_ConectorBD //extends JComponent  // JComponent es para que pueda ponerse en la Jframe aunque no se vea.
          implements Serializable {
    
  private static final long serialVersionUID = 1L;// para no tener problemas de cambio de versión al ser serializable.
    
    private transient Connection conexion; // conexion con la base de datos. Este objeto representa mi base de datos.
    
    private String AA_Servidor="localhost";    
    private int AA_Puerto=3306;
    private String AA_BaseDatos="MIS_CLIENTES";
    private String AA_usuario="root";
    private String AA_password="root";
    private boolean AA_Conectada=false;
    
    // 
    public boolean hayError;
    public String mensajeError;
    
    /* 
    QUIEN SE ENCARGA DE AVISAR A LOS QUE ME SIGUEN. para indicar a otros si se ha cambiado el estado
        
    */
    
    private final PropertyChangeSupport propertySupport;
   
   
    
    
    public FER_ConectorBD() {
        
        propertySupport = new PropertyChangeSupport(this);
    }
    
   
    
   
    public Connection daconexion() { return this.conexion;}
 

    public String getAA_Servidor() {
        return AA_Servidor;
    }

    public void setAA_Servidor(String AA_Servidor) {
        this.AA_Servidor = AA_Servidor;
    }

    public int getAA_Puerto() {
        return AA_Puerto;
    }

    public void setAA_Puerto(int AA_Puerto) {
        this.AA_Puerto = AA_Puerto;
    }

    public String getAA_BaseDatos() {
        return AA_BaseDatos;
    }

    public void setAA_BaseDatos(String AA_BaseDatos) {
        this.AA_BaseDatos = AA_BaseDatos;
    }

    public String getAA_usuario() {
        return AA_usuario;
    }

    public void setAA_usuario(String AA_usuario) {
        this.AA_usuario = AA_usuario;
    }

    public String getAA_password() {
        return AA_password;
    }

    public void setAA_password(String AA_password) {
        this.AA_password = AA_password;
    }

    public boolean isAA_Conectada() {
        return AA_Conectada;
    }

    /**
     *  Cambiar de conetada a no conectada y viceversa
     * @param AA_Conectada sera true para conectar y false para desconectar 
     
    
    */
    public void setAA_Conectada(boolean AA_Conectada) throws Exception {
    
        
    synchronized (this)   
      {   
        try {
                this.hayError=false;
                if ( AA_Conectada== this.AA_Conectada) return ;  // nada que hacer
                if ( AA_Conectada)   // si se quiere conectar ==true                         
                    {   
                        
                        //this.conexion =DriverManager.getConnection ("jdbc:firebirdsql://"+this.AA_Servidor+":"+this.AA_Puerto+"" <-- VERSION PARA FIREBIRD 
                          //    +this.AA_BaseDatos+ "?user="+this.AA_usuario+"&password="+this.AA_password);    
                             Class.forName("com.mysql.jdbc.Driver"); // OJO cargar driver  
                             
                              // version para MYSQL/MARIADB 
                             this.conexion =DriverManager.getConnection ("jdbc:mysql://"+this.AA_Servidor+":"+this.AA_Puerto+"/"+this.AA_BaseDatos,this.AA_usuario,this.AA_password ); 
                    }                                      
                else if (this.conexion != null) this.conexion.close();   // Si AA_CONECTADA es false= cerramos la base de datos
            }
        catch (SQLException ex) 
             {
                    this.hayError = true;
                    this.mensajeError= ex.getMessage();
                    JOptionPane.showMessageDialog (null,"Error FER_ConectorDB: \n"+this.mensajeError); 
                    throw new RuntimeException(ex) ; // mandamos error , esto hace que no se marque como TRUE el "conectada"
                  
              }
        catch (ClassNotFoundException ex)
                {
                    this.hayError = true;
                    this.mensajeError= ex.getMessage();
                    JOptionPane.showMessageDialog (null,"DRIVER.-Error FER_ConectorDB: \n"+this.mensajeError); 
                    
                    throw new RuntimeException(ex) ; // nos vamos dando error
                   
                 
        }
         /******************************    
         // sin todo esta ok;-> decirle a los demas que esten interesados que hemos cambiado.
         * 
         */
         
         boolean estadoAnterior = this.AA_Conectada;
         this.AA_Conectada=AA_Conectada;
            /*
             aqui avisamos a todos nuestros seguidores  **********************!!!
           */
         this.propertySupport.firePropertyChange(CONSTANTES.CONEXION_BASE_DATOS, estadoAnterior,this.AA_Conectada);     
         
        }
    }  
    
    /**
     * Metodo para añadir un escuchador: un seguidor
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    /**
     * Metodo para quitar un escuchador: un seguidor
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    // por si alguien nos pide  cual es la conexion a la base de datos
    
    
    public  Connection daConexion() { return this.conexion;} 
    
}
