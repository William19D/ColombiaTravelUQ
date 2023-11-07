package co.edu.uniquindio.agencia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VentanaRegistroGuias {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRegistrarGuia;

    @FXML
    private CheckBox ckEspañol;

    @FXML
    private CheckBox ckFrances;

    @FXML
    private CheckBox ckIngles;

    @FXML
    private TableColumn<?, ?> columNombre;

    @FXML
    private TableColumn<?, ?> columnExperiencia;

    @FXML
    private TableColumn<?, ?> columnIdentificacion;

    @FXML
    private TableColumn<?, ?> columnIdiomas;

    @FXML
    private TableView<?> tabGuiasRegistrados;

    @FXML
    private TextField txtExperiencia;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    void actualizarEvent(ActionEvent event) {

    }

    @FXML
    void eliminarEvent(ActionEvent event) {

    }

    @FXML
    void registrarGuiaEvent(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
