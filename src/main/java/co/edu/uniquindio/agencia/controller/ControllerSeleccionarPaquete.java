package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.*;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import co.edu.uniquindio.agencia.model.Reserva;
import co.edu.uniquindio.agencia.model.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField texPersonas;

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
        try {
            int cantidadPersonas = Integer.parseInt(texPersonas.getText());

            // Realizar validaciones adicionales según tus requerimientos
            if (cantidadPersonas <= 0) {
                mostrarAlertaError("Error", "Las personas no pueden ser negativas o 0");
            }

            System.out.println(paquete.toString());
            System.out.println(cantidadPersonas);
            System.out.println(SessionManager.getInstance().getCliente());
            Reserva reserva = agenciaViajes.registrarReserva(paquete, cantidadPersonas, SessionManager.getInstance().getCliente());
            if (reserva != null) {
                mostrarAlertaInfo("Registro exitoso", "Se ha reservado correcctamente la reserva del paquete: " + paquete.getNombre() + " \n para el cliente: " + SessionManager.getInstance().getCliente().getNombre());
            }

            // Ocultar la ventana de detalles del paquete
            anchorPaquete.setVisible(false);
            anchorPaquete.setDisable(true);

        } catch (NumberFormatException | AtributoVacioException | RutaInvalidaException | InformacionRepetidaException |
                 DestinoRepetidoException e) {
            e.printStackTrace();
        } catch (AtributoNegativoException e) {
            throw new RuntimeException(e);
        }
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

    private void mostrarAlertaError(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.setHeaderText(null);
        alert.showAndWait();

    }
    private void mostrarAlertaInfo(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
