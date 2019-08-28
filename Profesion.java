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
public class Profesion {

    private String profesion;
    private int idprofesion;

    public Profesion() {
    }

    public Profesion(String profesion) {
        this.profesion = profesion;
    }

    public Profesion(String profesion, int idprofesion) {
        this.profesion = profesion;
        this.idprofesion = idprofesion;
    }

    public int getIdprofesion() {
        return idprofesion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public ObservableList<Profesion> cargarProfesion() throws ClassNotFoundException, SQLException { //Con este método devuelvo un ObservableList, que voy a necesitar 
        //para meter los datos en el Combobox correspondiente

        //Primero cojo los datos
        ConexionController c = ConexionController.getInstancia();

        ObservableList<Profesion> obprofesion = FXCollections.observableArrayList();//Crear ArrayList

        String SQL = "select * " //Hago la consulta
                + "FROM profesionesprueba ";

        ResultSet rs = c.ejecutarConsulta(SQL); //Almaceno los datos en el ResultSet

        while (rs.next()) { //Cojo los datos
            String profesion = rs.getString("profesion");
            int idprofesion = rs.getInt("idprofesion");

            Profesion pro = new Profesion(profesion, idprofesion);//Creo un objeto para meterle los datos
            obprofesion.add(pro);//Ahora ese objeto lo meto en el ObservableList
        }
        return obprofesion; //Devuelvo el ObservableList

    }
    
    
    public boolean insertarProfesion() throws SQLException, ClassNotFoundException{
        ConexionController c = ConexionController.getInstancia();
        String SQL = "INSERT INTO profesionesprueba (profesion) values ('" + profesion+ "')";
        System.out.println(SQL);
        int filas=c.ejecutarInstruccion(SQL);
        
        if(filas==0){
            return false;
        } else return true; 
    }

    @Override
    public String toString() {
        return profesion;
    }
    
    
    
    
    
    
}
