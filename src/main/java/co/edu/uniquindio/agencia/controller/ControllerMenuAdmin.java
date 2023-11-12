
package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerMenuAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDestinos;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnGestion;

    @FXML
    private Button btnGuias;

    @FXML
    private Button btnPaquetes;

    @FXML
    private Button btnVentanaAdmin;

    @FXML
    private Label lblDestinos;

    @FXML
    private AnchorPane ventanaMenuAdmins;

    @FXML
    void destinosEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaMenuAdmins, "/ventanas/ventanaRegistroDestinos.fxml");

    }

    @FXML
    void estadisticasEvent(ActionEvent event) {

    }

    @FXML
    void gestionEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaMenuAdmins, "/ventanas/ventanaMenuAdmins.fxml");

    }

    @FXML
    void guiasEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaMenuAdmins, "/ventanas/ventanaRegistroGuias.fxml");

    }

    @FXML
    void mostrarVentanaAdmin(ActionEvent event) throws IOException {
        new ViewController(ventanaMenuAdmins, "/ventanas/ventanaAdmin.fxml");

    }

    @FXML
    void onBotonClick(MouseEvent event) {

    }

    @FXML
    void paquetesEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaMenuAdmins, "/ventanas/.fxml");

    }

    @FXML
    void initialize() {

    }

}
