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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modelo.Persona;
import modelo.Profesion;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class AgregarPersonasController implements Initializable {

    @FXML
    private TextField tfnombre;
    @FXML
    private TextField tfapellidos;
    @FXML
    private DatePicker dpfechanacimiento;
    @FXML
    private ComboBox<Profesion> cbprofesion;
    @FXML
    private Button btagregar;
    @FXML
    private Button btatras;

    private Persona persona;

    private ObservableList<Persona> obpersona;
    private ObservableList<Profesion> obprofesion;
    @FXML
    private RadioButton rbhombre;
    @FXML
    private RadioButton rbmujer;
    @FXML
    private Button btmodificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProfesion();
        

        ToggleGroup tg = new ToggleGroup(); //Esto para que no pueda clickar ambos radiobutton a la vez
        this.rbhombre.setToggleGroup(tg);
        this.rbmujer.setToggleGroup(tg);
        this.rbhombre.setSelected(true);
    }

    public void initAttribute(ObservableList<Persona> obpersona) {
        this.obpersona = obpersona;
        this.btmodificar.setVisible(false); //Oculta el botón de modificar
    }

    public void initAttribute(ObservableList<Persona> obpersona, Persona p) {
        try {
            //Sobrecargo el método para pasarle la persona que estoy modificando al método de modificar
            this.obpersona = obpersona;
            this.persona = p;
            this.btagregar.setVisible(false); //Oculta el botón de agregar

            this.tfnombre.setText(p.getNombre());
            this.tfapellidos.setText(p.getApellidos());
            this.dpfechanacimiento.setValue(p.getFechanacimiento());

            if (p.getGenero() == 'H') {
                this.rbhombre.setSelected(true);
            } else {
                this.rbmujer.setSelected(true);
            }

            Profesion profesion = new Profesion(); //Creo un objeto profesión
            obprofesion = profesion.cargarProfesion();//Guardo el observable list de la BBDD en el que acabo de crear
            this.cbprofesion.setItems(obprofesion); //Meto el observable list en el combobox

            Profesion pro = null; //Creo un objeto pro

            for (Profesion aux : obprofesion) { //Busco dentro del Observable list que el objeto aux coincida con el id profesion que me pasan
                if (aux.getIdprofesion() == p.getIdprofesion()) {
                    pro = aux;
                }
            }
            this.cbprofesion.setValue(pro); //Y, al coincidir, le doy dicho valor

            //this.cbprofesion.setValue(pro);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgregarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AgregarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void agregarPersona(ActionEvent event) {
        try {
            String nombre = this.tfnombre.getText();
            String apellidos = this.tfapellidos.getText();
            LocalDate fechanacimiento = this.dpfechanacimiento.getValue();
            Profesion profesion = this.cbprofesion.getValue();
            char genero;

            if (this.rbhombre.isSelected()) {
                genero = 'H';
            } else {
                genero = 'M';
            }

            Persona persona = new Persona(nombre, apellidos, fechanacimiento, profesion.getIdprofesion(), genero);

            if (persona.insertarDatos() == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("La persona se ha insertado de forma correcta");
                alert.showAndWait();
                alert.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("La persona no se ha insertado");
                alert.showAndWait();
                alert.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgregarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgregarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void cargarProfesion() {
        try {
            Profesion profesion = new Profesion(); //Creo objetos para el combobox
            obprofesion = profesion.cargarProfesion(); //Guardo el observable list de la BBDD en el que acabo de crear
            this.cbprofesion.setItems(obprofesion); //Meto el observable list en el combobox
            this.cbprofesion.getSelectionModel().select(0); //Con esto dejo seleccionado el primero para que no aparezca el combobox vacío

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void modificarPersonas(ActionEvent event) {

        try {
            //Cojo los textos de los textfields
            String nombre = tfnombre.getText();
            String apellidos = tfapellidos.getText();
            LocalDate fechanacimiento = dpfechanacimiento.getValue(); //No puedo convertir algo vacío, por eso no hace falta validar la edadç
            Profesion profesion = this.cbprofesion.getValue();
            String profesionnombre = profesion.getProfesion();
            char genero;
            if (this.rbhombre.isSelected()) {
                genero = 'H';
            } else {
                genero = 'M';
            }

            //Del objeto persona, le hago los setters
            this.persona.setNombre(nombre);
            this.persona.setApellidos(apellidos);
            this.persona.setFechanacimiento(fechanacimiento);
            this.persona.setIdprofesion(profesion.getIdprofesion()); // Seteo el ID profesión y cojo el id del objeto
            this.persona.setGenero(genero);

            if (this.persona.updatePersona()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION); //Informo al usuario de que se ha actualizado el cliente de forma correcta
                alert.setTitle("Información");
                alert.setContentText("Se ha actualizado la persona de forma correcta");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR); //Informo al usuario de que se ha actualizado el cliente de forma correcta
                alert.setTitle("Error");
                alert.setContentText("Ha habido un error en la modificación de la persona");
                alert.showAndWait();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgregarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AgregarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
