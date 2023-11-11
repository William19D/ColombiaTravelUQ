package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerVentanaAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCerrar;

    @FXML
    private ImageView btnVolver;

    @FXML
    private Button btnVolverMenuAdmins;

    @FXML
    private Label lblDestinos;

    @FXML
    private Label lblDestinos1;

    @FXML
    private Label lblDestinos11;

    @FXML
    private AnchorPane ventanaUsuario;

    @FXML
    void cerrarSecionEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaUsuario, "/ventanas/inicio.fxml");

    }

    @FXML
    void vovlerMenuAdmins(ActionEvent event) throws IOException {
        new ViewController(ventanaUsuario, "/ventanas/ventanaMenuAdmins.fxml");

    }

    @FXML
    void initialize() {

    }

}
