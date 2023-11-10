package co.edu.uniquindio.agencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;

public class ControllerVentanaInicio {

    @FXML
    private Button btnBuscarDestinos;

    @FXML
    private Button btnBuscarPaquetes;

    @FXML
    private AnchorPane ventanaPaquetes;

    @FXML
    private Label lblDestinos;

    @FXML
    private AnchorPane ventanaMenuInicio;

    private Button botonSeleccionado;

    public void initialize() {
        botonSeleccionado = btnBuscarDestinos;

    }

    @FXML
    void mostrarBuscarPaquetes(ActionEvent event) throws IOException {
        AnchorPane ventana = FXMLLoader.load(getClass().getResource("/ventanas/ventanaPaquetes.fxml"));
        ventanaPaquetes.getChildren().setAll(ventana);

    }
    @FXML
    void mostrarVentanaUsuario() throws IOException {
        new ViewController(ventanaMenuInicio, "/ventanas/ventanaUsuario.fxml");

    }
    @FXML
    void onBotonClick(MouseEvent event) {
        Button nuevoBoton = (Button) event.getSource();

        if (botonSeleccionado != nuevoBoton) {
            botonSeleccionado.getStyleClass().remove("botonSeleccionado");
            nuevoBoton.getStyleClass().add("botonSeleccionado");
            botonSeleccionado = nuevoBoton;
        }else{
            nuevoBoton.getStyleClass().add("botonSeleccionado");
        }
    }
}
