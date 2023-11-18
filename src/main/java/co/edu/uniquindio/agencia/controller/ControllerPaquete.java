package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Destino;
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

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    public ControllerPaquete() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }


    @FXML
    void initialize() {

        if (paquete != null) {
            lblNombrePaquete.setText(paquete.getNombre());
            lblDescripcionPaquete.setText(paquete.getDescripcion());
            lblPrecioPaquete.setText(String.valueOf(paquete.getPrecio()));
            lblCupo.setText(String.valueOf(agenciaViajes.calcularCuposRestantes(paquete.getCupoMax(),paquete.getCupoActual())));
            Destino primerDestino = paquete.getDestinos().get(0);

            if (primerDestino != null && !primerDestino.getRutasImagenes().isEmpty()) {
                File primeraImagen = primerDestino.getRutasImagenes().get(0);

                Image image = new Image(primeraImagen.toURI().toString());
                imagenPaquete.setImage(image);
            }
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
