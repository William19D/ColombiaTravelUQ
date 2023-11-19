package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Cliente;
import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import co.edu.uniquindio.agencia.model.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ControllerReservasUsuario {

    @FXML
    private AnchorPane scrollAnchorPaneProximo;

    @FXML
    private AnchorPane scrollAnchorPaneAnterior;

    @FXML
    private ScrollPane scrollPaquetesAnteriores;

    @FXML
    private ScrollPane scrollPaquetesProximos;

    @FXML
    private AnchorPane ventanaBuscadorPaquetes;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    Cliente cliente = SessionManager.getInstance().getCliente();

    public ControllerReservasUsuario() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }


    @FXML
    void calificarViaje(ActionEvent event) {

    }

    @FXML
    void cancelarViaje(ActionEvent event) {

    }

    private void mostrarPaquetesEnScrollPaneAnteriores(List<PaquetesTuristicos> paquetes) throws IOException {
        VBox vbox = new VBox();

        for (PaquetesTuristicos paquete : paquetes) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/paquete.fxml"));
            Parent root = loader.load();
            ControllerPaquete controller = loader.getController();
            controller.setPaquete(paquete);
            vbox.getChildren().add(root);
        }

        scrollAnchorPaneAnterior.getChildren().clear();
        scrollAnchorPaneAnterior.getChildren().add(vbox);
    }
    private void mostrarPaquetesEnScrollPaneFuturos(List<PaquetesTuristicos> paquetes) throws IOException {
        VBox vbox = new VBox();

        for (PaquetesTuristicos paquete : paquetes) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/paquete.fxml"));
            Parent root = loader.load();
            ControllerPaquete controller = loader.getController();
            controller.setPaquete(paquete);
            vbox.getChildren().add(root);
        }

        scrollAnchorPaneProximo.getChildren().clear();
        scrollAnchorPaneProximo.getChildren().add(vbox);
    }

    private void cargarPaquetesAnteriores() {
        try {
            List<PaquetesTuristicos> listaDePaquetes = agenciaViajes.obtenerPaquetesAnteriores(cliente);
            mostrarPaquetesEnScrollPaneAnteriores(listaDePaquetes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cargarPaquetesAFuturos() {
        try {
            List<PaquetesTuristicos> listaDePaquetes = agenciaViajes.obtenerPaquetesFuturos(cliente);
            mostrarPaquetesEnScrollPaneFuturos(listaDePaquetes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws RutaInvalidaException {
        cargarPaquetesAnteriores();
        cargarPaquetesAFuturos();

    }

}
