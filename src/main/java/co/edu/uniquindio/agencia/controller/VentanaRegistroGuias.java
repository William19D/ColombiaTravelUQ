package co.edu.uniquindio.agencia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<?, ?> columNombre;

    @FXML
    private TableColumn<?, ?> columnExperiencia;

    @FXML
    private TableColumn<?, ?> columnIdentificacion;

    @FXML
    private TableColumn<?, ?> columnIdiomas;

    @FXML
    private TableView<?> tabGuiasRegistrados;

    @FXML
    private TextField txtExperiencia;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

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
    void registrarGuiaEvent(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
