package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPaquete {

    @FXML
    private ImageView imagenPaquete;

    @FXML
    private Label lblCupo;

    @FXML
    private Label lblDescripcionPaquete;

    @FXML
    private Label lblNombrePaquete;

    @FXML
    private Label lblPrecioPaquete;

    @FXML
    private AnchorPane paqueteScroll;
    private PaquetesTuristicos paquete;

    @FXML
    void initialize() {
        // Configurar elementos gráficos con la información del paquete
        if (paquete != null) {
            lblNombrePaquete.setText(paquete.getNombre());
            lblDescripcionPaquete.setText(paquete.getDescripcion());
            lblPrecioPaquete.setText(String.valueOf(paquete.getPrecio()));
            lblCupo.setText(String.valueOf(paquete.getCupoMax()));
            File primeraImagen = paquete.getDestinos().get(0).getRutasImagenes().get(0);

            // Configurar la imagen en el ImageView
            Image image = new Image(primeraImagen.toURI().toString());
            imagenPaquete.setImage(image);
        }
    }

    public void setPaquete(PaquetesTuristicos paquete) {
        this.paquete = paquete;
        initialize();
    }
    @FXML
    void mostrarVentaPaquete(MouseEvent event) {

    }

}
