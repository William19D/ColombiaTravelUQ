package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ControllerBuscadorPaquetes {

    @FXML
    private DatePicker fechaIda;

    @FXML
    private DatePicker fechaVuelta;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private AnchorPane scrollAnchorPaquetes;

    @FXML
    private ScrollPane scrollPaquetes;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtClima;

    @FXML
    private TextField txtPersonas;

    @FXML
    private TextField txtPresupuesto;

    @FXML
    private AnchorPane ventanaBuscadorPaquetes;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    public ControllerBuscadorPaquetes() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
    }

    public void initialize() throws IOException {
        scrollPaquetes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Configurar el filtro para aceptar solo caracteres numéricos
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            if (Pattern.matches("[0-9]*", newText)) {
                return change;
            } else {
                return null; // Rechazar el cambio si el nuevo texto contiene caracteres no numéricos
            }
        };

        // Aplicar el filtro al TextFormatter del TextField de presupuesto
        txtPresupuesto.setTextFormatter(new TextFormatter<>(numericFilter));

        // Listener para el cambio en el campo de presupuesto
        txtPresupuesto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Volver a cargar los paquetes cada vez que cambie el texto
                cargarPaquetesPorPresupuesto(newValue);
            }
        });

        // Cargar los paquetes al inicio
        cargarPaquetes();
    }

    public void cargarPaquetesPorPresupuesto(String presupuesto) {
        try {
            // Obtener la lista de paquetes que cumplen con el presupuesto
            List<PaquetesTuristicos> paquetesFiltrados = agenciaViajes.getPaquetesPorPresupuesto(Double.parseDouble(presupuesto));

            // Crear un VBox para almacenar las instancias de ControllerPaquete
            VBox vbox = new VBox();

            // Configurar cada instancia con un paquete diferente
            for (PaquetesTuristicos paquete : paquetesFiltrados) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/paquete.fxml"));
                Parent root = loader.load();
                ControllerPaquete controller = loader.getController();
                controller.setPaquete(paquete);
                vbox.getChildren().add(root);
            }

            // Limpiar el contenido actual y agregar el nuevo VBox al ScrollPane
            scrollAnchorPane.getChildren().clear();
            scrollAnchorPane.getChildren().add(vbox);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones específicas según sea necesario
        } catch (NumberFormatException ex) {
            // Manejo de la excepción si el valor ingresado no es un número válido
        }
    }

    private void cargarPaquetes() {
        try {
            // Obtener la lista de paquetes desde tu AgenciaViajes
            ArrayList<PaquetesTuristicos> listaDePaquetes = agenciaViajes.getPaquetes();

            // Crear un VBox para almacenar las instancias de ControllerPaquete
            VBox vbox = new VBox();

            // Configurar cada instancia con un paquete diferente
            for (PaquetesTuristicos paquete : listaDePaquetes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/paquete.fxml"));
                Parent root = loader.load();
                ControllerPaquete controller = loader.getController();
                controller.setPaquete(paquete);
                vbox.getChildren().add(root);
            }

            // Configurar el VBox en el ScrollPane
            scrollAnchorPane.getChildren().add(vbox);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones específicas según sea necesario
        }
    }
}