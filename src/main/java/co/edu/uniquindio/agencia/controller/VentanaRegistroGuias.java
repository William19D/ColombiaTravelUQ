package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
import javafx.collections.ObservableList;

public class VentanaRegistroGuias {

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
        actualizarTablaGuias();
    }

    @FXML
    void eliminarEvent(ActionEvent event) throws AtributoVacioException, RutaInvalidaException {
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
            // Muestra un cuadro de diálogo de confirmación al usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que quieres eliminar al guía seleccionado?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Elimina el guía seleccionado de la lista de guías
                agenciaViajes.getGuias().remove(guiaSeleccionado);

                // Actualiza la tabla de guías
                actualizarTablaGuias();
            }
        } else {
            // Si no se selecciona ningún guía, muestra un mensaje de error
            throw new AtributoVacioException("Selecciona un guía para eliminar.");
        }
    }

    @FXML
    void initialize() throws RutaInvalidaException {
        tablaguias();
        actualizarTablaGuias();
    }

    void tablaguias() throws  RutaInvalidaException{

        tabGuiasRegistrados.setItems(listaGuias);

        ckEspanol.setSelected(false);
        ckIngles.setSelected(false);
        ckFrances.setSelected(false);

        columNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        columnExperiencia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExp()));
        //columnIdiomas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdiomas().toString()));
        tabGuiasRegistrados.refresh();

    }
}