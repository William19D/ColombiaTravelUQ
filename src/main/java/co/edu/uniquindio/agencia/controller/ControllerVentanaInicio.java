package co.edu.uniquindio.agencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;

public class ControllerVentanaInicio {

    @FXML
    private Label lblDestinos;

    @FXML
    private AnchorPane ventanaMenuInicio;
    public void initialize() {

    }

    @FXML
    void mostrarBuscarPaquetes(ActionEvent event) {

    }
    @FXML
    void mostrarVentanaUsuario() throws IOException {
        new ViewController(ventanaMenuInicio, "/ventanas/ventanaUsuario.fxml");

    }
}
