package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.agencia.app.App;
import co.edu.uniquindio.agencia.exceptions.*;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.GuiaTuristico;
import co.edu.uniquindio.agencia.model.Idiomas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
import javafx.collections.ObservableList;

public class VentanaRegistroGuias {

    GuiaTuristico guiaTuristicoSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRegistrarGuia;

    @FXML
    private CheckBox ckEspanol;

    @FXML
    private CheckBox ckFrances;

    @FXML
    private CheckBox ckIngles;

    @FXML
    private TableColumn<GuiaTuristico, String> columNombre;

    @FXML
    private TableColumn<GuiaTuristico, String> columnExperiencia;

    @FXML
    private TableColumn<GuiaTuristico, String> columnIdentificacion;

    @FXML
    private TableColumn<GuiaTuristico, Idiomas> columnIdiomas;

    @FXML
    private TableView<GuiaTuristico> tabGuiasRegistrados;

    @FXML
    private TextField txtExperiencia;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private AnchorPane ventanaGuias;

    private App main;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();


    private Idiomas idiomas;

    ObservableList<GuiaTuristico> listaGuias = FXCollections.observableArrayList(AgenciaViajes.getInstance().getGuias());


    public VentanaRegistroGuias() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
    }

    @FXML
    void actualizarEvent(ActionEvent event) throws RutaInvalidaException {
        actualizarAction();

    }

    private void actualizarAction() throws RutaInvalidaException {

        try {
            String nombre = txtNombre.getText();
            String identificacion = txtIdentificacion.getText();
            String experiencia = txtExperiencia.getText();

            if (!ckEspanol.isSelected() && !ckIngles.isSelected() && !ckFrances.isSelected()) {

                throw new ElementoNoEncontradoException("Debes seleccionar al menos un idioma.");
            }
            List<Idiomas> idiomasSeleccionados = new ArrayList<>();

            if (ckEspanol.isSelected()) {
                idiomasSeleccionados.add(Idiomas.ESPANOL);
            }
            if (ckIngles.isSelected()) {
                idiomasSeleccionados.add(Idiomas.INGLES);
            }
            if (ckFrances.isSelected()) {
                idiomasSeleccionados.add(Idiomas.FRANCES);
            }
            //agenciaViajes.actualizarGuia(nombre,identificacion,idiomasSeleccionados,experiencia);
            //Persistencia.guardarRecursoBinario(agenciaViajes);

            // Limpia los campos después del registro
            txtNombre.clear();
            txtIdentificacion.clear();
            txtExperiencia.clear();
            ckEspanol.setSelected(false);
            ckIngles.setSelected(false);
            ckFrances.setSelected(false);

            this.tabGuiasRegistrados.setItems(listaGuias);
            actualizarTablaGuias();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha actualizado correctamente el guia con la cedula  " + txtIdentificacion.getText());
            alert.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }

    }

    @FXML
    void eliminarEvent(ActionEvent event) throws AtributoVacioException, RutaInvalidaException, DestinoRepetidoException, ElementoNoEncontradoException, InformacionRepetidaException {
        eliminarAction();
    }

    @SneakyThrows
    @FXML
    void registrarGuiaEvent(ActionEvent event) {
        registrarGuiaAction();

    }

    private void registrarGuiaAction() throws ElementoNoEncontradoException {

        try {
            String nombre = txtNombre.getText();
            String identificacion = txtIdentificacion.getText();
            String experiencia = txtExperiencia.getText();


            if (!ckEspanol.isSelected() && !ckIngles.isSelected() && !ckFrances.isSelected()) {

                throw new ElementoNoEncontradoException("Debes seleccionar al menos un idioma.");
            }

            // Recopilar los idiomas seleccionados
            List<Idiomas> idiomasSeleccionados = new ArrayList<>();

            if (ckEspanol.isSelected()) {
                idiomasSeleccionados.add(Idiomas.ESPANOL);
            }
            if (ckIngles.isSelected()) {
                idiomasSeleccionados.add(Idiomas.INGLES);
            }
            if (ckFrances.isSelected()) {
                idiomasSeleccionados.add(Idiomas.FRANCES);
            }

            // Llamar al método de registro en la clase principal
            GuiaTuristico guia = agenciaViajes.registrarGuias(nombre, identificacion, idiomasSeleccionados, experiencia);
            //Persistencia.guardarRecursoBinario(agenciaViajes);
          //  this.agenciaViajes.getGuias().add(guia);
            this.tabGuiasRegistrados.setItems(listaGuias);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el guia con la cedula  " + txtIdentificacion.getText());
            alert.show();

            // Limpia los campos después del registro
            txtNombre.clear();
            txtIdentificacion.clear();
            txtExperiencia.clear();
            ckEspanol.setSelected(false);
            ckIngles.setSelected(false);
            ckFrances.setSelected(false);

            // Actualizar la tabla de guías registrados u otra lógica necesaria
            actualizarTablaGuias();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }
    }

    public void actualizarTablaGuias() throws RutaInvalidaException {
        listaGuias = FXCollections.observableArrayList(agenciaViajes.getGuias());
        tabGuiasRegistrados.getItems().clear();
        tabGuiasRegistrados.setItems(listaGuias);
        tablaguias();
        tabGuiasRegistrados.refresh();
    }

    private void eliminarAction() throws AtributoVacioException, RutaInvalidaException {
        GuiaTuristico guiaSeleccionado = tabGuiasRegistrados.getSelectionModel().getSelectedItem();

        if (guiaSeleccionado != null) {
            try {
                // Llamar al método de eliminación en la clase principal
                AgenciaViajes.getInstance().eliminarGuia(guiaSeleccionado.getIdentificacion());
                //Persistencia.guardarRecursoBinario(agenciaViajes);
                // Actualiza la tabla de guías
                actualizarTablaGuias();
            } catch (ElementoNoEncontradoException | InformacionRepetidaException | DestinoRepetidoException e) {
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
    void vovlerMenuAdmins(ActionEvent event) throws IOException {
        new ViewController(ventanaGuias, "/ventanas/ventanaMenuAdmins.fxml");
    }

    @FXML
    void initialize() throws RutaInvalidaException {
        tablaguias();
        actualizarTablaGuias();
    }

    void tablaguias() throws  RutaInvalidaException {

        tabGuiasRegistrados.setItems(listaGuias);

        ckEspanol.setSelected(false);
        ckIngles.setSelected(false);
        ckFrances.setSelected(false);

        columNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        columnExperiencia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExp()));
       /* columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        columnExperiencia.setCellValueFactory(new PropertyValueFactory<>("exp"));*/
        tabGuiasRegistrados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
            guiaTuristicoSeleccionado= newSelection;
            mostrarInformacion();
        });
        TableColumn<GuiaTuristico, String> columnIdiomas = new TableColumn<>("Idiomas");
        columnIdiomas.setCellValueFactory(cellData -> {
            GuiaTuristico guia = cellData.getValue();
            List<String> idiomas = new ArrayList<>();

            // Agregar idiomas seleccionados
            if (ckEspanol.isSelected()) {
                idiomas.add("Español");
            }
            if (ckIngles.isSelected()) {
                idiomas.add("Inglés");
            }
            if (ckFrances.isSelected()) {
                idiomas.add("Francés");
            }

            return new SimpleStringProperty(String.join(", ", idiomas));
        });

        // Actualizar la tabla

        tabGuiasRegistrados.refresh();

    }

    private void mostrarInformacion() {
        txtNombre.setText(guiaTuristicoSeleccionado.getNombre());
        txtIdentificacion.setText(guiaTuristicoSeleccionado.getIdentificacion());
        txtExperiencia.setText(guiaTuristicoSeleccionado.getExp());
    }
}