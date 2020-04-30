/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean_11_consultaSQL;

/**
 *
 * @author fer
 */
public class CONSTANTES {
   public static final String CONEXION_BASE_DATOS="AA_Conectada"; // conexión a base de datos estara abierta o cerrada
   public static final String CONSULTA_SQL_ABIERTA="AA_abierta"; // la consulta se ha ejecutado y tiene datos o se ha cerrado.
   public static final String CONSULTA_NUMERO_COLUMNAS="AA_numeroDeColumnas";  // cuantas columnas devuelve la consulta SQL
                                                                        // 0 si la consulta esta cerrada
   public static final String CONSULTA_NUMERO_FILAS="AA_numeroFilas";  // cuantas columnas devuelve la consulta SQL
                                                                        // 0 si la consulta esta cerrada
   public static final String CONSULTA_TITULOS="AA_titulo";     // Los títulos de las columnas han cambiado
}
