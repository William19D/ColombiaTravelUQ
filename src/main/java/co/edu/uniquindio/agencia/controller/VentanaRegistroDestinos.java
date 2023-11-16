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

    Destino destinoSeleccionado;

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

    @FXML
    private Button btnEditar;



    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();



    @FXML
    void initialize() throws RutaInvalidaException {
        tablaDestinos();
        actualizarTablaDestinos();
        mostrarImagenesSeleccionadas();
        configurarRestriccionesCamposTexto();
        configurarSeleccionUnicaClimas();


    }
    private void configurarSeleccionUnicaClimas() {
        // Configurar la selección única para el clima frío
        ckFrio.setOnAction(event -> {
            if (ckFrio.isSelected()) {
                ckSoleado.setSelected(false);
                ckTemplado.setSelected(false);
            }
        });

        // Configurar la selección única para el clima soleado
        ckSoleado.setOnAction(event -> {
            if (ckSoleado.isSelected()) {
                ckFrio.setSelected(false);
                ckTemplado.setSelected(false);
            }
        });

        // Configurar la selección única para el clima templado
        ckTemplado.setOnAction(event -> {
            if (ckTemplado.isSelected()) {
                ckFrio.setSelected(false);
                ckSoleado.setSelected(false);
            }
        });
    }

    public VentanaRegistroDestinos() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
    }

    @FXML
    void borrarDestinoEvent(ActionEvent event) {
        try {
            borrarDestinoAction();
        } catch (AtributoVacioException e) {
            mostrarAlerta("Error", "No se ha seleccionado ningún destino para borrar.");
        }
    }

    private void borrarDestinoAction() throws AtributoVacioException {
        Destino destinoSeleccionado = tabDestinosRegistrados.getSelectionModel().getSelectedItem();

        if (destinoSeleccionado != null) {
            try {
                // Llamar al método de eliminación en la clase principal
                agenciaViajes.eliminarDestino(destinoSeleccionado.getNombre(), destinoSeleccionado.getCiudad());

                // Actualizar la tabla de destinos
                actualizarTablaDestinos();
            } catch (ElementoNoEncontradoException | RutaInvalidaException e) {
                // Manejar la excepción si el destino no se encuentra
                mostrarAlerta(null, e.getMessage());
            }
        } else {
            // Si no se selecciona ningún destino, muestra un mensaje de error
            throw new AtributoVacioException("Selecciona un destino para borrar.");
        }
    }


    @FXML
    void actualizarEvent(ActionEvent event) {
        actualizarDestinosAction();

    }

    private void actualizarDestinosAction() {
        try {
            String nombre = txtNombre.getText();
            String ciudad = txtCiudad.getText();
            String descripcion = txtDescripcion.getText();
            List<File> imagenesDestino = imagenesSeleccionadas;


            if (!ckSoleado.isSelected() && !ckTemplado.isSelected() && !ckFrio.isSelected()) {
                throw new ElementoNoEncontradoException("Debes seleccionar un clima.");
            }
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


            agenciaViajes.actualizarDestino(nombre, ciudad, descripcion,imagenesDestino,climaSeleccionado);
            this.tabDestinosRegistrados.setItems(listaDestino);
            mostrarAlertaInfo(null,"Se ha actualizado correctamente el destino de nombre " + nombre);

            txtNombre.clear();
            txtCiudad.clear();
            txtDescripcion.clear();
            ckSoleado.setSelected(false);
            ckTemplado.setSelected(false);
            ckFrio.setSelected(false);
            imagenesSeleccionadas.clear();

            anchorPaneImagenes.getChildren().clear();

            this.tabDestinosRegistrados.setItems(listaDestino);
            actualizarTablaDestinos();

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }

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
                throw new ElementoNoEncontradoException("Debes seleccionar un clima.");
            }
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
            if(imagenesDestino == null){
                mostrarAlerta("Error", "Solo se permiten letras en el campo de ciudad.");
                throw new AtributoVacioException("No se selecciono ninguna imagen");
            }

            agenciaViajes.registrarDestino(nombre, ciudad, descripcion,imagenesDestino,climaSeleccionado);
            this.tabDestinosRegistrados.setItems(listaDestino);
            mostrarAlertaInfo(null,"Se ha registrado correctamente el destino de nombre" +   txtNombre.getText());

            txtNombre.clear();
            txtCiudad.clear();
            txtDescripcion.clear();
            ckSoleado.setSelected(false);
            ckTemplado.setSelected(false);
            ckFrio.setSelected(false);
            imagenesSeleccionadas.clear();

            anchorPaneImagenes.getChildren().clear();

            actualizarTablaDestinos();

        } catch (AtributoVacioException | RutaInvalidaException | ElementoNoEncontradoException |
                 DestinoRepetidoException e) {
            throw new RuntimeException(e);
        }
    }

    private void actualizarTablaDestinos() throws RutaInvalidaException {
        scrollPaneImagenes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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
    void seleccionarImagenEvent(ActionEvent event) throws AtributoVacioException{
        try {
            List<File> nuevasImagenes = agenciaViajes.seleccionarImagenes();

            if (primeraVezSeleccionImagenes) {
                imagenesSeleccionadas = new ArrayList<>(nuevasImagenes);
                primeraVezSeleccionImagenes = false;
            } else {
                List<File> imagenesModificable = new ArrayList<>(imagenesSeleccionadas);
                imagenesModificable.addAll(nuevasImagenes);
                imagenesSeleccionadas = imagenesModificable;
            }

            mostrarImagenesSeleccionadas();
        } catch (AtributoVacioException e) {
            // Manejar la excepción si no se selecciona ninguna imagen
            mostrarAlerta("Error", "No se ha seleccionado ninguna imagen.");
        }
    }



    private void mostrarImagenesSeleccionadas() {
        anchorPaneImagenes.getChildren().clear();

        if (imagenesSeleccionadas != null && !imagenesSeleccionadas.isEmpty()) {
            double xOffset = 10;
            double yOffset = 10;

            for (int i = 0; i < imagenesSeleccionadas.size(); i++) {
                File imagenFile = imagenesSeleccionadas.get(i);
                Image image = new Image(imagenFile.toURI().toString());

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(288);
                imageView.setFitHeight(269);

                double x = xOffset;
                double y = yOffset + i * (269 + 10);

                AnchorPane.setLeftAnchor(imageView, x);
                AnchorPane.setTopAnchor(imageView, y);

                anchorPaneImagenes.getChildren().add(imageView);
            }
        }
    }
    private void configurarRestriccionesCamposTexto() {
        // Configurar restricciones para el campo de ciudad
        txtCiudad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]*$")) {
                // Si el nuevo valor no contiene solo letras, espacios y tildes, mostrar una alerta y revertir el cambio
                mostrarAlerta("Error", "Solo se permiten letras, espacios y tildes en el campo de ciudad.");
                txtCiudad.setText(oldValue);
            }
        });

        // Configurar restricciones para el campo de nombre
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]*$")) {
                // Si el nuevo valor no contiene solo letras, espacios y tildes, mostrar una alerta y revertir el cambio
                mostrarAlerta("Error", "Solo se permiten letras, espacios y tildes en el campo de nombre.");
                txtNombre.setText(oldValue);
            }
        });
    }


    private void mostrarAlerta(String titulo, String contenido) {
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

    void tablaDestinos() throws  RutaInvalidaException{

        tabDestinosRegistrados.setItems(listaDestino);

        ckSoleado.setSelected(false);
        ckTemplado.setSelected(false);
        ckFrio.setSelected(false);

        columNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
        columClima.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClima().toString()));

        tabDestinosRegistrados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            destinoSeleccionado= newSelection;
            mostrarInformacion();
        });
        tabDestinosRegistrados.refresh();

    }

    @FXML
    void editarDestinoEvent(ActionEvent event) {
        try {
            editarDestinoAction();
        } catch (AtributoVacioException e) {
            mostrarAlerta("Error", "No se ha seleccionado ningún destino para editar.");
        }
    }
    private void mostrarInformacion() {
        if(destinoSeleccionado!=null){
            txtNombre.setText(destinoSeleccionado.getNombre());
            txtCiudad.setText(destinoSeleccionado.getCiudad());
            txtDescripcion.setText(destinoSeleccionado.getDescripcion());


        }
    }

    private void editarDestinoAction() throws AtributoVacioException {
        Destino destinoSeleccionado = tabDestinosRegistrados.getSelectionModel().getSelectedItem();

        if (destinoSeleccionado != null) {
            // Deshabilitar otros controles si es necesario
            txtNombre.setDisable(true);
            // Deshabilitar otros campos que no deben ser editados durante la operación de edición

            // Cargar los datos del destino en los campos correspondientes
            txtNombre.setText(destinoSeleccionado.getNombre());
            txtCiudad.setText(destinoSeleccionado.getCiudad());
            // Otros campos...

            // Cargar las imágenes
            imagenesSeleccionadas = new ArrayList<>(destinoSeleccionado.getRutasImagenes());
            mostrarImagenesSeleccionadas();
        } else {
            // Si no se selecciona ningún destino, muestra un mensaje de error
            throw new AtributoVacioException("Selecciona un destino para editar.");
        }
    }

}