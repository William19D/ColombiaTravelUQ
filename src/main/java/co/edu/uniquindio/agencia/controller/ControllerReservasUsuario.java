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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
        ControllerPaquete paqueteSeleccionado = obtenerPaqueteSeleccionado(scrollAnchorPaneProximo);
        if (paqueteSeleccionado != null) {
            agenciaViajes.eliminarViaje(paqueteSeleccionado.getPaquete(), cliente);
            cargarPaquetesAFuturos();
            mostrarAlertaInfo("Viaje cancelado","El paquete: " + paqueteSeleccionado.getPaquete() + " ha salido eliminado de tus reservas");
        }
    }

    private ControllerPaquete obtenerPaqueteSeleccionado(AnchorPane scrollAnchorPane) {
        for (Node node : scrollAnchorPane.getChildren()) {
            if (node instanceof VBox) {
                for (Node childNode : ((VBox) node).getChildren()) {
                    if (childNode instanceof Parent && childNode.getUserData() instanceof ControllerPaquete) {
                        ControllerPaquete controller = (ControllerPaquete) childNode.getUserData();
                        if (controller.isSeleccionado()) {
                            return controller;
                        }
                    }
                }
            }
        }

        return null;
    }

    private void mostrarPaquetesEnScrollPaneAnteriores(List<PaquetesTuristicos> paquetes) throws IOException {
        VBox vbox = new VBox();

        for (PaquetesTuristicos paquete : paquetes) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/paquete.fxml"));
            Parent root = loader.load();
            ControllerPaquete controller = loader.getController();
            controller.setPaquete(paquete);

            // Establece el userData del nodo con el controlador
            root.setUserData(controller);

            // Asigna el evento de clic al paquete
            controller.deseleccionarPaquete();
            controller.getPaqueteScroll().setOnMouseClicked(event -> {
                // Deselecciona los demás paquetes antes de seleccionar el actual
                vbox.getChildren().forEach(node -> ((ControllerPaquete) node.getUserData()).deseleccionarPaquete());
                controller.seleccionarPaquete();
            });

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

            // Establece el userData del nodo con el controlador
            root.setUserData(controller);

            // Asigna el evento de clic al paquete
            controller.deseleccionarPaquete();
            controller.getPaqueteScroll().setOnMouseClicked(event -> {
                // Deselecciona los demás paquetes antes de seleccionar el actual
                vbox.getChildren().forEach(node -> ((ControllerPaquete) node.getUserData()).deseleccionarPaquete());
                controller.seleccionarPaquete();
            });

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
    private void mostrarAlertaInfo(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
