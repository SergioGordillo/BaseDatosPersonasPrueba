/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Profesion;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class AgregarProfesionesController implements Initializable {

    @FXML
    private Button btagregarprofesion;
    @FXML
    private Button btatras;
    @FXML
    private TextField tfprofesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void agregarProfesion(ActionEvent event) {
        try {
            String profesion=this.tfprofesion.getText(); //Cojo el texto del textfield
            Profesion pro=new Profesion(profesion); //Creo el objeto y le meto el contenido del textfield
            
            if (pro.insertarProfesion()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("La profesión se ha insertado de forma correcta");
                alert.showAndWait();
                alert.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("La profesión no se ha insertado");
                alert.showAndWait();
                alert.close();
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgregarProfesionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgregarProfesionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       

    @FXML
    private void atras(ActionEvent event) {
        try {
            // Abro la ventana

            // Cargamos la vista secundaria
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/menu.fxml"));

            // Cargo el padre
            Parent root = (Parent) fxmlLoader.load();

            // cargo la ventana secundaria
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Menú");
            stage.setScene(scene);

            // Muestro la ventana
            stage.show();
            // Código para cerrar la ventana actual cuando abra la que quiero abrir
            Stage Mystage = (Stage) this.btatras.getScene().getWindow();
            Mystage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
