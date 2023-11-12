package co.edu.uniquindio.agencia.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.agencia.exceptions.*;
import co.edu.uniquindio.agencia.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class VentanaRegistroDestinos {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnregistrarDestino;

    @FXML
    private CheckBox ckFrio;

    @FXML
    private CheckBox ckSoleado;

    @FXML
    private CheckBox ckTemplado;

    ObservableList<Destino> listaDestino = FXCollections.observableArrayList(AgenciaViajes.getInstance().getDestinos());

    @FXML
    private TableColumn<Destino, String> columCiudad;

    @FXML
    private TableColumn<Destino, String> columClima;

    @FXML
    private TableColumn<Destino, String> columNombre;

    @FXML
    private TableView<Destino> tabDestinosRegistrados;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtImagen;

    @FXML
    private TextField txtNombre;

    @FXML
    private AnchorPane ventanaDestinos;

    List<File> imagenesSeleccionadas;


    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();



    public VentanaRegistroDestinos() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
    }

    @FXML
    void EliminarDestinoEvent(ActionEvent event) throws AtributoVacioException {
        eliminarDestinoAction();


    }

    private void eliminarDestinoAction() throws AtributoVacioException {

        Destino destinoSeleccionado = tabDestinosRegistrados.getSelectionModel().getSelectedItem();

        if (destinoSeleccionado != null) {
            try {
                // Llamar al método de eliminación en la clase principal
                AgenciaViajes.getInstance().eliminarDestino(destinoSeleccionado.getNombre());

                // Actualiza la tabla de guías
                actualizarTablaDestinos();
            } catch (ElementoNoEncontradoException | InformacionRepetidaException | DestinoRepetidoException |
                     RutaInvalidaException e) {
                // Manejar la excepción si el guía no se encuentra
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.setHeaderText(null);
                alert.show();
            }
        } else {
            // Si no se selecciona ningún guía, muestra un mensaje de error
            throw new AtributoVacioException("Selecciona un guía para eliminar.");
        }
    }

    @FXML
    void actualizarDestinosEvent(ActionEvent event) {

    }

    @FXML
    void registrarDestinoEvent(ActionEvent event) {
        registrarDestinoAction();

    }

    private void registrarDestinoAction() {
        try {
            String nombre = txtNombre.getText();
            String ciudad = txtCiudad.getText();
            String descripcion = txtDescripcion.getText();
            List<File> imagenesDestino = imagenesSeleccionadas;


            if (!ckSoleado.isSelected() && !ckTemplado.isSelected() && !ckFrio.isSelected()) {

                throw new ElementoNoEncontradoException("Debes seleccionar al menos un clima.");
            }

            // Recopilar los idiomas seleccionados
            List<Clima> climaSeleccionados = new ArrayList<>();

            if (ckSoleado.isSelected()) {
                climaSeleccionados.add(Clima.CALIDO);
            }
            if (ckTemplado.isSelected()) {
                climaSeleccionados.add(Clima.TEMPLADO);
            }
            if (ckFrio.isSelected()) {
                climaSeleccionados.add(Clima.FRIO);
            }
            if(imagenesDestino != null){
                imagenesSeleccionadas.clear();
            }

            Clima clima = null;
          //  Destino destino = agenciaViajes.registrarDestino(nombre, ciudad, descripcion,imagen,;

            //  this.agenciaViajes.getGuias().add(guia);
            this.tabDestinosRegistrados.setItems(listaDestino);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el destino de nombre  " + txtNombre.getText());
            alert.show();

            // Limpia los campos después del registro
            txtNombre.clear();
            txtCiudad.clear();
            txtImagen.clear();
            txtDescripcion.clear();
            ckSoleado.setSelected(false);
            ckTemplado.setSelected(false);
            ckFrio.setSelected(false);


            actualizarTablaDestinos();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }
    }

    private void actualizarTablaDestinos() throws RutaInvalidaException {

            listaDestino = FXCollections.observableArrayList(agenciaViajes.getDestinos());
            tabDestinosRegistrados.getItems().clear();
            tabDestinosRegistrados.setItems(listaDestino);
            tablaDestinos();
            tabDestinosRegistrados.refresh();

    }

    @FXML
    void vovlerMenuAdmins(ActionEvent event) throws IOException {
        new ViewController(ventanaDestinos, "/ventanas/ventanaMenuAdmins.fxml");
    }
    @FXML
    void seleccionarImagenEvent(ActionEvent event) throws IOException {
        imagenesSeleccionadas = agenciaViajes.seleccionarImagenes();

    }

    @FXML
    void initialize() throws RutaInvalidaException {
        tablaDestinos();
        actualizarTablaDestinos();

    }

    void tablaDestinos() throws  RutaInvalidaException{

        tabDestinosRegistrados.setItems(listaDestino);

        ckSoleado.setSelected(false);
        ckTemplado.setSelected(false);
        ckFrio.setSelected(false);

        columNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
       // columClima.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClima().toString()));

        tabDestinosRegistrados.refresh();

    }

}
