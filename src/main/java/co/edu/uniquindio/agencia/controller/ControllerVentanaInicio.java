package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Destino;
import co.edu.uniquindio.agencia.model.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerVentanaInicio {

    @FXML
    private Button btnBuscarDestinos;

    @FXML
    private Button btnBuscarPaquetes;

    @FXML
    private AnchorPane ventanaMenu;

    @FXML
    private Label lblDestinos;

    @FXML
    private AnchorPane ventanaMenuInicio;

    @FXML
    private ImageView imagen1;

    @FXML
    private ImageView imagen2;

    @FXML
    private ImageView imagen3;

    @FXML
    private ImageView imagen4;

    @FXML
    private Button misViajes;

    @FXML
    private Button nombreUsuario;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();


    private Button botonSeleccionado;

    public ControllerVentanaInicio() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }

    public void initialize() {
        nombreUsuario.setText(SessionManager.getInstance().getCliente().getNombre());
        botonSeleccionado = btnBuscarDestinos;
        ArrayList<Destino> destinos = agenciaViajes.getDestinos();

        // Baraja la lista de destinos para obtener un orden aleatorio
        Collections.shuffle(destinos);

        // Establece las imágenes en los ImageView correspondientes
        setRandomImage(imagen1, destinos.get(0));
        setRandomImage(imagen2, destinos.get(1));
        setRandomImage(imagen3, destinos.get(2));
        setRandomImage(imagen4, destinos.get(3));

    }
    private void setRandomImage(ImageView imageView, Destino destino) {
        // Obtén las rutas de imágenes del destino
        List<File> rutasImagenes = destino.getRutasImagenes();

        // Baraja las rutas de imágenes para obtener una al azar
        Collections.shuffle(rutasImagenes);

        // Carga la imagen desde la ruta al azar y la establece en el ImageView
        if (!rutasImagenes.isEmpty()) {
            Image image = new Image(rutasImagenes.get(0).toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void mostrarBuscarPaquetes(ActionEvent event) throws IOException {
        AnchorPane ventana = FXMLLoader.load(getClass().getResource("/ventanas/ventanaPaquetes.fxml"));
        ventanaMenu.getChildren().setAll(ventana);
    }


    @FXML
    void buscarDestinosEvent(ActionEvent event) throws IOException {
        new ViewController(ventanaMenuInicio, "/ventanas/ventanaMenu.fxml");

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
    @FXML
    void mostrarReservas(ActionEvent event) throws IOException {
        AnchorPane ventana = FXMLLoader.load(getClass().getResource("/ventanas/ventanaViajes.fxml"));
        ventanaMenu.getChildren().removeAll();
        ventanaMenu.getChildren().setAll(ventana);

    }
}
