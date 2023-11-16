package co.edu.uniquindio.agencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class VentanaRegistroPaquetes {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRegistrarPaquete;

    @FXML
    private ImageView btnVolver;

    @FXML
    private Button btnVolverMenuAdmins;

    @FXML
    private TableColumn<?, ?> columNombre;

    @FXML
    private TableColumn<?, ?> columnExperiencia;

    @FXML
    private TableColumn<?, ?> columnIdentificacion;

    @FXML
    private TableColumn<?, ?> columnIdiomas;

    @FXML
    private DatePicker dateFinal;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private TableView<?> tabPaquetesRegistrados;

    @FXML
    private TextField txtCupoMax;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtServicios;

    @FXML
    private AnchorPane ventanaPaquetes;

    @FXML
    void actualizarEvent(ActionEvent event) {

    }

    @FXML
    void eliminarEvent(ActionEvent event) {

    }

    @FXML
    void registrarPaqueteEvent(ActionEvent event) {

    }

    @FXML
    void volverMenuAdmins(ActionEvent event) throws IOException {
        new ViewController(ventanaPaquetes, "/ventanas/ventanaMenuAdmins.fxml");

    }

}
