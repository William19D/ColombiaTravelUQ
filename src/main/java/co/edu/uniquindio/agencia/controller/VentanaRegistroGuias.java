package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.agencia.app.App;
import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.GuiaTuristico;
import co.edu.uniquindio.agencia.model.Idiomas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.SneakyThrows;

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
    private CheckBox ckEspañol;

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
    private TableColumn<GuiaTuristico, ?> columnIdiomas;

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



    public VentanaRegistroGuias() throws RutaInvalidaException {
    }

    @FXML
    void actualizarEvent(ActionEvent event) {

    }

    @FXML
    void eliminarEvent(ActionEvent event) {
    }
    @SneakyThrows
    @FXML
    void registrarGuiaEvent(ActionEvent event) {
        registrarGuiaAction();

    }

    private void registrarGuiaAction() throws ElementoNoEncontradoException{

        try {
            String nombre = txtNombre.getText();
            String identificacion = txtIdentificacion.getText();
            String experiencia = txtExperiencia.getText();

            // Validar que al menos un idioma esté seleccionado
            if (!ckEspañol.isSelected() && !ckIngles.isSelected() && !ckFrances.isSelected()) {
                // Aquí puedes mostrar un mensaje de error o lanzar una excepción.
                // Por ejemplo: throw new IdiomaNoSeleccionadoException("Debes seleccionar al menos un idioma.");
                return;
            }

            // Recopilar los idiomas seleccionados
            List<Idiomas> idiomasSeleccionados = new ArrayList<>();
            if (ckEspañol.isSelected()) {
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

            // Limpia los campos después del registro
            txtNombre.clear();
            txtIdentificacion.clear();
            txtExperiencia.clear();
            ckEspañol.setSelected(false);
            ckIngles.setSelected(false);
            ckFrances.setSelected(false);

            // Actualizar la tabla de guías registrados u otra lógica necesaria
            //actualizarTablaGuias();

            // Puedes mostrar un mensaje de éxito al usuario
            throw new ElementoNoEncontradoException("Guía registrado con éxito: " + guia.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException | RutaInvalidaException e) {
            throw new ElementoNoEncontradoException("Error al registrar guía: " + e.getMessage());
        }
    }



    @FXML
    void initialize() {

    }

}
