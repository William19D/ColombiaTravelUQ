package co.edu.uniquindio.agencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerVentanaUsuario {

    @FXML
    private ImageView btnVolver;

    @FXML
    private Label lblDestinos;

    @FXML
    private Label lblDestinos1;

    @FXML
    private Label lblDestinos11;


    @FXML
    private AnchorPane ventanaUsuario;

    @FXML
    void editarEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaUsuario, "/ventanas/ventanaActualizarCliente.fxml");

    }

    @FXML
    void salirEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaUsuario, "/ventanas/inicio.fxml");

    }

    @FXML
    void vovlerMenu() throws IOException {
        new ViewController(ventanaUsuario, "/ventanas/ventanaMenu.fxml");
    }


}
