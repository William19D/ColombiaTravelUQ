package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Destino;
import co.edu.uniquindio.agencia.model.PaquetesTuristicos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class VentanaRegistroPaquetes {

    @FXML
    private Button agregarEleccion;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRegistrarPaquete;

    @FXML
    private ImageView btnVolver;

    @FXML
    private Button btnVolverMenuAdmins;

    @FXML
    private TableColumn<Destino, String> columNombreAgregado;

    @FXML
    private TableColumn<Destino, String> columNombreDisp;

    @FXML
    private TableColumn<PaquetesTuristicos, String> columNombrePaquete;

    @FXML
    private TableColumn<Destino, String> columnCiudadAgregado;

    @FXML
    private TableColumn<Destino, String> columnCiudadDisp;

    @FXML
    private TableColumn<Destino, String> columnClimaAgregado;

    @FXML
    private TableColumn<Destino, String> columnClimaDisp;

    @FXML
    private TableColumn<PaquetesTuristicos, String> columnCupoPaquete;

    @FXML
    private TableColumn<PaquetesTuristicos, String> columnPrecioPaquete;

    @FXML
    private DatePicker dateFinal;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private Button eliminarEleccion;

    @FXML
    private TableView<Destino> tableDestinosAgregados;

    @FXML
    private TableView<Destino> tableDestinosDisponibles;

    @FXML
    private TableView<PaquetesTuristicos> tablePaquetesRegistrados;

    @FXML
    private TextField txtCupoMax;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtServicios;

    @FXML
    private AnchorPane ventanaPaquetes;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    ObservableList<Destino> listaDisponibles = FXCollections.observableArrayList(AgenciaViajes.getInstance().getDestinos());
    private ObservableList<Destino> destinosAgregados = FXCollections.observableArrayList();


    @FXML
    void initialize() throws RutaInvalidaException {
        tablaDestinos();

    }

    public VentanaRegistroPaquetes() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }

    void tablaDestinos() throws RutaInvalidaException {

        tableDestinosDisponibles.setItems(listaDisponibles);

        columNombreDisp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnCiudadDisp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
        columnClimaDisp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClima().toString()));

    }

    @FXML
    void agregarEleccion(ActionEvent event) {
        // Obtener el destino seleccionado en la tabla de destinos disponibles
        Destino destinoSeleccionado = tableDestinosDisponibles.getSelectionModel().getSelectedItem();

        if (destinoSeleccionado != null) {
            // Agregar el destino a la lista de destinos agregados
            destinosAgregados.add(destinoSeleccionado);

            // Eliminar el destino de la tabla de destinos disponibles
            tableDestinosDisponibles.getItems().remove(destinoSeleccionado);

            // Actualizar la tabla de destinos agregados
            tableDestinosAgregados.setItems(destinosAgregados);
            columNombreAgregado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            columnCiudadAgregado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
            columnClimaAgregado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClima().toString()));

        }
    }

    @FXML
    void eliminarEleccion(ActionEvent event) {
        // Obtén el destino seleccionado en la tabla de destinos agregados
        Destino destinoSeleccionado = tableDestinosAgregados.getSelectionModel().getSelectedItem();

        if (destinoSeleccionado != null) {
            // Muestra un diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de que desea eliminar este destino? ");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remueve el destino de la tabla de destinos agregados
                tableDestinosAgregados.getItems().remove(destinoSeleccionado);

                // Agrega el destino de nuevo a la lista de destinos disponibles
                listaDisponibles.add(destinoSeleccionado);
            }
        }
    }

    @FXML
    void actualizarEvent(ActionEvent event) {

    }

    @FXML
    void eliminarEvent(ActionEvent event) {

    }

    @FXML
    void registrarPaqueteEvent(ActionEvent event) {
        try {
            // Obtener los datos necesarios del formulario
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            String serviciosAdicionales = txtServicios.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int cupoMax = Integer.parseInt(txtCupoMax.getText());
            LocalDate fechaDisponibleInicio = dateInicio.getValue();
            LocalDate fechaDisponibleFin = dateFinal.getValue();

            // Validar y obtener la lista de destinos seleccionados
            ObservableList<Destino> destinosSeleccionados = tableDestinosAgregados.getItems();
            if (destinosSeleccionados.isEmpty()) {
                mostrarError("Debe seleccionar al menos un destino para el paquete.");
                return;
            }

            // Realizar otras validaciones según tus requisitos

            // Llamar al método de la instancia de AgenciaViajes para registrar el paquete
            PaquetesTuristicos paqueteRegistrado = agenciaViajes.registrarPaquetes(
                    nombre,
                    new ArrayList<>(destinosSeleccionados),
                    descripcion,
                    serviciosAdicionales,
                    precio,
                    cupoMax,
                    fechaDisponibleInicio,
                    fechaDisponibleFin,
                    null // Aquí debes pasar el guía correspondiente si lo tienes en tu interfaz de usuario
            );

            // Limpiar los campos después de registrar el paquete
            limpiarCampos();

            // Notificar al usuario
            mostrarInformacion("Paquete registrado exitosamente: " + paqueteRegistrado.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException | RutaInvalidaException e) {
            mostrarError(e.getMessage());
        } catch (NumberFormatException e) {
            mostrarError("Por favor, ingrese un valor numérico válido para el precio y el cupo máximo.");
        }
    }

    // Método para limpiar los campos del formulario después de registrar un paquete
    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        txtServicios.clear();
        txtPrecio.clear();
        txtCupoMax.clear();
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        destinosAgregados.clear();
        tableDestinosAgregados.setItems(destinosAgregados);
    }


    @FXML
    void volverMenuAdmins(ActionEvent event) throws IOException {
        new ViewController(ventanaPaquetes, "/ventanas/ventanaMenuAdmins.fxml");

    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje de información al usuario
    private void mostrarInformacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
