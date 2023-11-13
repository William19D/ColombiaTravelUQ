package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Clima;
import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ControllerBuscadorPaquetes {

    @FXML
    private MenuButton menuClima;

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

    private Clima climaSeleccionado;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    public ControllerBuscadorPaquetes() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
    }

    public void initialize() throws IOException {
        configurarScrollPane();
        configurarFiltroNumerico();
        configurarPresupuestoListener();
        configurarCiudadListener();
        configurarPersonasListener();
        configurarFechasListener();
        cargarPaquetes();
        menuClima.getItems().forEach(item -> item.setOnAction(this::seleccionarClima));
    }

    private void configurarFechasListener() {
        fechaIda.valueProperty().addListener((observable, oldValue, newValue) -> cargarPaquetesPorFechas());
        fechaVuelta.valueProperty().addListener((observable, oldValue, newValue) -> cargarPaquetesPorFechas());
    }

    private void cargarPaquetesPorFechas() {
        try {
            LocalDate salida = fechaIda.getValue();
            LocalDate llegada = fechaVuelta.getValue();

            if (salida != null && llegada != null) {
                List<PaquetesTuristicos> paquetesEnFechas = agenciaViajes.getPaquetesPorFechas(salida, llegada);
                mostrarPaquetesEnScrollPane(paquetesEnFechas);
            } else {
                // Manejo si alguna de las fechas es nula
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones específicas según sea necesario
        }
    }

    private void configurarScrollPane() {
        scrollPaquetes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void configurarFiltroNumerico() {
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            return Pattern.matches("[0-9]*", newText) ? change : null;
        };

        txtPresupuesto.setTextFormatter(new TextFormatter<>(numericFilter));
    }

    private void configurarPresupuestoListener() {
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            return Pattern.matches("[0-9]*", newText) ? change : null;
        };

        txtPresupuesto.setTextFormatter(new TextFormatter<>(numericFilter));

        txtPresupuesto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarPaquetes();
            } else {
                cargarPaquetesPorPresupuesto(newValue);
            }
        });
    }


    private void configurarCiudadListener() {
        UnaryOperator<TextFormatter.Change> letterFilter = change -> {
            String newText = change.getControlNewText();
            return Pattern.matches("[a-zA-Z]*", newText) ? change : null;
        };

        txtCiudad.setTextFormatter(new TextFormatter<>(letterFilter));

        txtCiudad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarPaquetes();
            } else {
                cargarPaquetesPorCiudad(newValue);
            }
        });
    }

    private void configurarFiltroSoloLetras() {
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            return Pattern.matches("[0-9]*", newText) ? change : null;
        };

        txtPresupuesto.setTextFormatter(new TextFormatter<>(numericFilter));
    }

    private void cargarPaquetes() {
        try {
            List<PaquetesTuristicos> listaDePaquetes = agenciaViajes.getPaquetes();
            mostrarPaquetesEnScrollPane(listaDePaquetes);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones específicas según sea necesario
        }
    }

    private void cargarPaquetesPorPresupuesto(String presupuesto) {
        try {
            List<PaquetesTuristicos> paquetesFiltrados = agenciaViajes.getPaquetesPorPresupuesto(Double.parseDouble(presupuesto));
            mostrarPaquetesEnScrollPane(paquetesFiltrados);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
    }

    private void cargarPaquetesPorCiudad(String ciudad) {
        try {
            List<PaquetesTuristicos> paquetesPorCiudad = agenciaViajes.getPaquetesPorCiudad(ciudad);
            mostrarPaquetesEnScrollPane(paquetesPorCiudad);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones específicas según sea necesario
        }
    }

    private void mostrarPaquetesEnScrollPane(List<PaquetesTuristicos> paquetes) throws IOException {
        VBox vbox = new VBox();

        for (PaquetesTuristicos paquete : paquetes) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/paquete.fxml"));
            Parent root = loader.load();
            ControllerPaquete controller = loader.getController();
            controller.setPaquete(paquete);
            vbox.getChildren().add(root);
        }

        scrollAnchorPane.getChildren().clear();
        scrollAnchorPane.getChildren().add(vbox);
    }

    @FXML
    private void seleccionarClima(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String climaText = menuItem.getText();
        climaSeleccionado = Clima.valueOf(climaText.toUpperCase());
        menuClima.setText(climaText);
        filtrarPaquetes();
    }

    private void filtrarPaquetes() {
        try {
            String ciudad = txtCiudad.getText();
            if (!ciudad.isEmpty()) {
                cargarPaquetesPorCiudad(ciudad);
                return;
            }

            if (climaSeleccionado != null) {
                List<PaquetesTuristicos> paquetesFiltrados = agenciaViajes.getPaquetesPorClima(climaSeleccionado);
                mostrarPaquetesEnScrollPane(paquetesFiltrados);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de excepciones específicas según sea necesario
        }
    }
    private void configurarPersonasListener() {
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            return Pattern.matches("[0-9]*", newText) ? change : null;
        };

        txtPersonas.setTextFormatter(new TextFormatter<>(numericFilter));

        txtPersonas.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarPaquetes();
            } else {
                cargarPaquetesPorPersonas(newValue);
            }
        });
    }

    private void cargarPaquetesPorPersonas(String personas) {
        try {
            int cantidadPersonas = Integer.parseInt(personas);

            List<PaquetesTuristicos> paquetesFiltrados = agenciaViajes.getPaquetesDisponiblesPorPersonas(cantidadPersonas);
            mostrarPaquetesEnScrollPane(paquetesFiltrados);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
    }
}
