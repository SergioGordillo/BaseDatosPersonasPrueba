/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ConexionController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sergio
 */
public class Persona {

    private String nombre;
    private String apellidos;
    private LocalDate fechanacimiento;
    private int id;
    private int idprofesion;
    private char genero;
    private String profesionnombre;

    public Persona() {
    }

    public Persona(String nombre, String apellidos, LocalDate fechanacimiento, int id, int idprofesion, char genero) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechanacimiento = fechanacimiento;
        this.id = id;
        this.idprofesion = idprofesion;
        this.genero = genero;
    }
    

    public Persona(String nombre, String apellidos, LocalDate fechanacimiento, int idprofesion, char genero) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechanacimiento = fechanacimiento;
        this.idprofesion = idprofesion;
        this.genero = genero;
    }

    public Persona(String nombre, String apellidos, LocalDate fechanacimiento, int id, int idprofesion, char genero, String profesionnombre) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechanacimiento = fechanacimiento;
        this.id = id;
        this.idprofesion = idprofesion;
        this.genero = genero;
        this.profesionnombre = profesionnombre;
    }
    
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public int getIdprofesion() {
        return idprofesion;
    }

    public void setIdprofesion(int idprofesion) {
        this.idprofesion = idprofesion;
    }

    public int getId() {
        return id;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getProfesionnombre() {
        return profesionnombre;
    }

    public void setProfesionnombre(String profesionnombre) {
        this.profesionnombre = profesionnombre;
    }
    
    
    

    public ObservableList<Persona> cargarPersona() throws ClassNotFoundException, SQLException { //Con este método devuelvo un ObservableList, que voy a necesitar 
        //para meter los datos en el Combobox correspondiente

        //Primero cojo los datos
        ConexionController c = ConexionController.getInstancia();

        ObservableList<Persona> obpersona = FXCollections.observableArrayList();//Crear ArrayList

        String SQL = "select per.id, per.nombre, per.apellidos, per.fechanacimiento, pro.idprofesion, pro.profesion, per.genero FROM personasprueba per, profesionesprueba pro WHERE per.idprofesion=pro.idprofesion ";

        ResultSet rs = c.ejecutarConsulta(SQL); //Almaceno los datos en el ResultSet
        System.out.println(SQL);

        while (rs.next()) { //Cojo los datos
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
            LocalDate fechanacimiento = rs.getDate("fechanacimiento").toLocalDate();
            int id = rs.getInt("id");
            int idprofesion = rs.getInt("idprofesion");
            char genero = rs.getString("genero").charAt(0);
            String profesion=rs.getString ("profesion"); 
            

            Persona persona = new Persona(nombre, apellidos, fechanacimiento, id, idprofesion, genero, profesion); //Creo un objeto para meterle los datos
            obpersona.add(persona); //Ahora ese objeto lo meto en el ObservableList
        }
        return obpersona; //Devuelvo el ObservableList

    }
    
    public ObservableList<Persona> cargarPersona(LocalDate fechaNacimientoInicio, LocalDate fechaNacimientoFinal, String nombreBusqueda, Profesion pro, char sexoFiltro) throws ClassNotFoundException, SQLException {
        ConexionController c = ConexionController.getInstancia();
        ObservableList<Persona> obpersonas = FXCollections.observableArrayList();//Crear ArrayList
         //Este método carga las personas aplicando el filtro de fechas
        String SQL ="select per.id as idpersona, per.nombre, per.apellidos, per.fechanacimiento, pro.idprofesion, pro.profesion, per.Genero "
                + "FROM personasprueba per, profesionesprueba pro "
                + "WHERE per.idprofesion=pro.idprofesion AND Genero='" + sexoFiltro +"' ";
       
        if(fechaNacimientoInicio!=null || fechaNacimientoFinal!=null){
            if(fechaNacimientoInicio!=null){
                SQL+="AND fechanacimiento>='"+fechaNacimientoInicio.toString()+"' ";
                
            }
            
            if(fechaNacimientoFinal!=null){
                SQL+="AND fechanacimiento<='"+fechaNacimientoFinal.toString()+"' ";
            }
        }
        
        if (!nombreBusqueda.isEmpty()){
            SQL+="AND nombre LIKE '%"+nombreBusqueda+"%' "; 
        }
        
        if (pro!=null){
            SQL+="AND pro.idprofesion=" + pro.getIdprofesion(); //Con esto le digo dónde idprofesion=idprofesion
        }
        
        

        ResultSet rs = c.ejecutarConsulta(SQL); //Almaceno los datos en el ResultSet

        while (rs.next()) { //Cojo los datos
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
            LocalDate fechanacimiento = rs.getDate("fechanacimiento").toLocalDate();
            int id = rs.getInt("idpersona");
            int idprofesion=rs.getInt("idprofesion");  
            char genero=rs.getString("Genero").charAt(0);
            String profesion=rs.getString("profesion");

            Persona persona = new Persona (nombre, apellidos, fechanacimiento, id, idprofesion, genero, profesion);
            obpersonas.add(persona);
        }
        return obpersonas;

    }
    
    public boolean insertarDatos() throws SQLException, ClassNotFoundException{
        ConexionController c = ConexionController.getInstancia();
        String SQL = "INSERT INTO personasprueba (nombre, apellidos, fechanacimiento, idprofesion, genero) values ('" + nombre + "', '" + apellidos + "', '" + fechanacimiento + "', '" + idprofesion + "', '" + genero + "')";
        System.out.println(SQL);
        int filas=c.ejecutarInstruccion(SQL);
        
        if(filas==0){
            return false;
        } else return true; 
    }
    
    public boolean updatePersona() throws ClassNotFoundException, SQLException {
        ConexionController c = ConexionController.getInstancia();

        String SQL = "UPDATE personasprueba SET Nombre='" + nombre + "', "
                + "Apellidos='" + apellidos + "', "
                + "fechanacimiento='" + fechanacimiento.toString() + "',"
                + " idprofesion="+idprofesion+" ,"
                + " Genero='"+genero+"' "
                
                + "WHERE id=" + this.getId();
        System.out.println(SQL);
        int filas = c.ejecutarInstruccion(SQL); //Con esta instrucción puedo actualizar los datos en la BBDD

        if (filas == 1) {
            return true;
        } else {
            return false;
        }

    }
    
        public boolean eliminarPersona() throws ClassNotFoundException, SQLException {
        ConexionController c = ConexionController.getInstancia();

        String SQL = "delete FROM personasprueba WHERE id=" + this.getId(); //Con el getId sé que persona en concreto deseo eliminar

        int filas = c.ejecutarInstruccion(SQL); //ejecutarInstrucción te devuelve un número, las filas a las que afecta

        if (filas == 1) {
            return true;
        } else {
            return false;
        }

    }

}
