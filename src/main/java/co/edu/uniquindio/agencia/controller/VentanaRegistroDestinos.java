package co.edu.uniquindio.agencia.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private ScrollPane scrollPaneImagenes;

    @FXML
    private AnchorPane anchorPaneImagenes;

    private boolean primeraVezSeleccionImagenes = true;



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
            Clima climaSeleccionado = null;

            if (ckSoleado.isSelected()) {
                climaSeleccionado = (Clima.CALIDO);
            }
            if (ckTemplado.isSelected()) {
                climaSeleccionado = (Clima.TEMPLADO);
            }
            if (ckFrio.isSelected()) {
                climaSeleccionado = (Clima.FRIO);
            }
            if(imagenesDestino != null){
                imagenesSeleccionadas.clear();
            }


            agenciaViajes.registrarDestino(nombre, ciudad, descripcion,imagenesDestino,climaSeleccionado);
            this.tabDestinosRegistrados.setItems(listaDestino);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el destino de nombre  " + txtNombre.getText());
            alert.show();

            // Limpia los campos después del registro
            txtNombre.clear();
            txtCiudad.clear();
            txtDescripcion.clear();
            ckSoleado.setSelected(false);
            ckTemplado.setSelected(false);
            ckFrio.setSelected(false);
            imagenesSeleccionadas.clear();

            actualizarTablaDestinos();

        } catch (AtributoVacioException e) {
            throw new RuntimeException(e);
        } catch (ElementoNoEncontradoException e) {
            throw new RuntimeException(e);
        } catch (DestinoRepetidoException e) {
            throw new RuntimeException(e);
        } catch (RutaInvalidaException e) {
            throw new RuntimeException(e);
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
    void volverMenuAdmins(ActionEvent event) throws IOException {
        new ViewController(ventanaDestinos, "/ventanas/ventanaMenuAdmins.fxml");
    }
    @FXML
    void seleccionarImagenEvent(ActionEvent event) throws IOException {
        imagenesSeleccionadas = agenciaViajes.seleccionarImagenes();
        mostrarImagenesSeleccionadas();  // Agrega esta línea
    }

    private void mostrarImagenesSeleccionadas() {
        anchorPaneImagenes.getChildren().clear(); // Limpiar las imágenes anteriores

        if (imagenesSeleccionadas != null && !imagenesSeleccionadas.isEmpty()) {
            double xOffset = 10; // Ajusta este valor según tus necesidades
            double yOffset = 10; // Ajusta este valor según tus necesidades

            for (int i = 0; i < imagenesSeleccionadas.size(); i++) {
                File imagenFile = imagenesSeleccionadas.get(i);
                Image image = new Image(imagenFile.toURI().toString());

                // Crea un ImageView con la imagen y ajusta el tamaño al tamaño específico
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(288); // Ajusta el ancho al tamaño específico
                imageView.setFitHeight(269); // Ajusta la altura al tamaño específico

                // Calcula la posición de la imagen
                double x = xOffset;
                double y = yOffset + i * (269 + 10); // Ajusta el espacio vertical entre las imágenes

                // Establece la posición de la imagen en el AnchorPane
                AnchorPane.setLeftAnchor(imageView, x);
                AnchorPane.setTopAnchor(imageView, y);

                anchorPaneImagenes.getChildren().add(imageView);
            }
        }
    }





    @FXML
    void initialize() throws RutaInvalidaException {
        tablaDestinos();
        actualizarTablaDestinos();
        mostrarImagenesSeleccionadas();

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