package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class ControllerSeleccionarPaquete {

    private PaquetesTuristicos paquete;

    @FXML
    private Button comprarPaqueteBoton;

    @FXML
    private Label cupos;

    @FXML
    private Label destinosPaquete;

    @FXML
    private Label fechaLlegada;

    @FXML
    private Label fechaSalida;

    @FXML
    private ImageView imagenDestinos;

    @FXML
    private Label nombrePaquete;

    @FXML
    private Label precio;

    @FXML
    private Button salirBoton;

    @FXML
    private Label serviciosAdicionales;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    @FXML
    private AnchorPane anchorPaquete;



    public ControllerSeleccionarPaquete() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }

    @FXML
    void initialize() throws IOException {
        if (paquete != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ventanaPaquetes.fxml"));
            Parent root = loader.load();
            // Acceder al controlador después de cargar el FXML
            ControllerBuscadorPaquetes controllerPaquetes = loader.getController();
            controllerPaquetes.getScrollPaquetes().setDisable(true);
            nombrePaquete.setText(paquete.getNombre());
            serviciosAdicionales.setText(paquete.getServiciosAdicionales());
            precio.setText(String.valueOf(paquete.getPrecio()));
            cupos.setText(String.valueOf(agenciaViajes.calcularCuposRestantes(paquete.getCupoMax(),paquete.getCupoActual())));
            fechaSalida.setText(String.valueOf(paquete.getFechaDisponibleInicio()));
            fechaLlegada.setText(String.valueOf(paquete.getFechaDisponibleFin()));
            destinosPaquete.setText(paquete.obtenerDestinosSeparadosPorComa());
        }

    }
    @FXML
    void comprarPaqueteAction() {

    }

    @FXML
    void salirAction() {
        anchorPaquete.setVisible(false);
        anchorPaquete.setDisable(true);

    }


    public void setPaquete(PaquetesTuristicos paquete) throws IOException {
        this.paquete = paquete;
        initialize();
    }

}
