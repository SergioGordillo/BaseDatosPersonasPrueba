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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;
import modelo.Profesion;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class MenuController implements Initializable {

    @FXML
    private TableView<Persona> tbpersonas;
    @FXML
    private TableColumn<Persona, String> colnombre;
    @FXML
    private TableColumn<Persona, String> colapellidos;
    @FXML
    private TableColumn<Persona, LocalDate> colfechanacimiento;
    @FXML
    private TableColumn<Persona, String> colprofesion;
    @FXML
    private TableColumn<Persona, Character> colsexo;
    @FXML
    private Button btagregar;
    @FXML
    private Button btmodificar;
    @FXML
    private Button bteliminar;
    @FXML
    private Button btsalir;
    @FXML
    private TextField tffiltronombre;
    @FXML
    private Button btreset;
    @FXML
    private ComboBox<Profesion> cbprofesion;

    private ObservableList<Persona> obpersona;
    private ObservableList<Profesion> obprofesion;
    @FXML
    private Button btagregarprofesiones;
    @FXML
    private RadioButton rbhombre;
    @FXML
    private RadioButton rbmujer;
    @FXML
    private DatePicker dpfechasinicio;
    @FXML
    private DatePicker dpfechasfinal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.colnombre.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombre"));
            this.colapellidos.setCellValueFactory(new PropertyValueFactory<Persona, String>("apellidos"));
            this.colfechanacimiento.setCellValueFactory(new PropertyValueFactory<Persona, LocalDate>("fechanacimiento"));
            this.colprofesion.setCellValueFactory(new PropertyValueFactory<Persona, String>("profesionnombre"));
            this.colsexo.setCellValueFactory(new PropertyValueFactory<Persona, Character>("genero"));

            Persona p = new Persona(); //Creo un objeto para llamar al método porque no es estático
            this.obpersona = p.cargarPersona(); //Cargo el observable list con los datos recogidos de la BBDD
            this.tbpersonas.setItems(obpersona); //Con esto le digo que los datos de la tabla los cojo del Observable List de Persona

            cargarProfesion();

            ToggleGroup tg = new ToggleGroup(); //Esto para que no pueda clickar ambos radiobutton a la vez
            this.rbhombre.setToggleGroup(tg);
            this.rbmujer.setToggleGroup(tg);
            this.rbhombre.setSelected(true);

            // TODO
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void filtrarProfesiones(ActionEvent event) {
        filtro();
    }

    @FXML
    private void resetearFiltro(ActionEvent event) {
        try {
            Persona persona = new Persona();
            this.obpersona = persona.cargarPersona();
            this.tbpersonas.setItems(obpersona);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

@FXML
        private void llevarAgregarPersonas(ActionEvent event) {
        try {
            // Abro la ventana

            // Cargamos la vista secundaria
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/agregarPersonas.fxml"));

            // Cargo el padre
            Parent root = (Parent) fxmlLoader.load();

            // cojo el controlador para pasárselo al hijo y pasarle los datos de la tabla al hijo
            AgregarPersonasController controlador = fxmlLoader.getController();
            controlador.initAttribute(obpersona);

            // cargo la ventana secundaria
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Personas");
            stage.setScene(scene);

            // Muestro la ventana
            stage.showAndWait();

        

} catch (IOException ex) {
            Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
        private void llevarModificarPersonas(ActionEvent event) {

        // Abro la ventana
        Persona p = this.tbpersonas.getSelectionModel().getSelectedItem(); //Con esto cojo a una persona seleccionada de la tabla

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar una persona para modificar");
            alert.showAndWait();

        } else {

            try {
                // Abro la ventana

                // Cargamos la vista secundaria
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/agregarPersonas.fxml"));

                // Cargo el padre
                Parent root = (Parent) fxmlLoader.load();

                // cojo el controlador para pasárselo al hijo y pasarle los datos de la tabla al hijo; esto me sirve para mantener los valores cuando quiero añadir, modificar o borrar
                AgregarPersonasController controlador = fxmlLoader.getController();
                controlador.initAttribute(obpersona, p);

                // cargo la ventana secundaria
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Modificar personas");
                stage.setScene(scene);

                // Muestro la ventana
                stage.showAndWait();
            

} catch (IOException ex) {
                Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
        private void eliminarPersona(ActionEvent event){
        Persona p = this.tbpersonas.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar una persona para eliminar");
            alert.showAndWait();
        } else {
            try {

                if (p.eliminarPersona()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Eliminar personas");
                    alert.setContentText("Has eliminado a una persona");
                    alert.showAndWait();

                    obpersona.remove(p); //Para eliminar la persona del programa
                    this.tbpersonas.refresh(); //Para refrescar la tabla
                        
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Ha habido un error eliminando a la persona");
                    alert.showAndWait();

                

}

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
            

} catch (SQLException ex) {
                Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    @FXML
        private void salir(ActionEvent event
    ) {
        System.exit(0); //Método que me permite salir del programa
    }

    public void cargarProfesion() {
        try {
            Profesion profesion = new Profesion(); //Creo objetos para el combobox
            obprofesion = profesion.cargarProfesion(); //Guardo el observable list de la BBDD en el que acabo de crear
            this.cbprofesion.setItems(obprofesion); //Meto el observable list en el combobox
            this.cbprofesion.getSelectionModel().select(0); //Con esto dejo seleccionado el primero para que no aparezca el combobox vacío

        

} catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
        

} catch (SQLException ex) {
            Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
        private void agregarProfesiones(ActionEvent event) {
        try {
            // Abro la ventana

            // Cargamos la vista secundaria
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/agregarProfesiones.fxml"));

            // Cargo el padre
            Parent root = (Parent) fxmlLoader.load();

            // cargo la ventana secundaria
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Profesiones");
            stage.setScene(scene);

            // Muestro la ventana
            stage.showAndWait();

        

} catch (IOException ex) {
            Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
        private void filtroGenero(ActionEvent event) {
       filtro();
    }

    @FXML
        private void filtrarFechasInicio(ActionEvent event) {
        filtro();
    }

    @FXML
        private void filtroFechasFinal(ActionEvent event) {
        filtro(); 
    }
    
    private void filtro(){
        LocalDate fechaNacimientoInicio = this.dpfechasinicio.getValue();
        LocalDate fechaNacimientoFinal = this.dpfechasfinal.getValue();
        String nombreBusqueda=this.tffiltronombre.getText(); 
        Profesion pro=this.cbprofesion.getValue();
        char genero;
        
        if(this.rbhombre.isSelected()){
            genero='H';
        } else {
            genero='M'; 
        }
        

        Persona persona = new Persona();

        try {
            this.obpersona= persona.cargarPersona (fechaNacimientoInicio, fechaNacimientoFinal, nombreBusqueda, pro, genero);
            this.tbpersonas.setItems(obpersona);

        

} catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
        

} catch (SQLException ex) {
            Logger.getLogger(MenuController.class
.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
        private void filtrarNombre(KeyEvent event) {
        filtro();
    }
}
