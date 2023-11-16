package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;



public class ControllerActualizarCliente {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarCliente;

    @FXML
    private Button btnIrMenu;

    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private AnchorPane ventana;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    public ControllerActualizarCliente() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }
    @FXML
    void actualizarClienteEvent(ActionEvent event) {
        if (validarCampos() && validarCedula()) {
            actualizarClienteAction();
        }
    }

    private boolean validarCedula() {
        String cedulaIngresada = txtCedula.getText();
        String cedulaUsuario = SessionManager.getInstance().getCliente().getCedula();

        if (!cedulaIngresada.equals(cedulaUsuario)) {
            mostrarAlerta("La cédula ingresada no coincide con la del usuario que inició sesión.");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void actualizarClienteAction() {
        String cedula =txtCedula.getText();
        try {
            agenciaViajes.actualizarCliente(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    txtContrasenia.getText()
            );
            SessionManager.getInstance().setCliente(agenciaViajes.obtenerCliente(cedula));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha actualizado correctamente ");
            alert.show();
            limpiarTexto();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }
    }
    private void limpiarTexto() throws IOException {
        txtNombre.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtContrasenia.setText("");
        txtTelefono.setText("");

    }

    private boolean validarCampos() {
        if (!validarNumero(txtCedula.getText())) {
            mostrarAlerta("Cédula inválida. Ingrese solo números.");
            return false;
        }

        if (!validarNumero(txtTelefono.getText())) {
            mostrarAlerta("Teléfono inválido. Ingrese solo números.");
            return false;
        }

        if (txtTelefono.getText().length() != 10) {
            mostrarAlerta("El teléfono debe tener 10 dígitos.");
            return false;
        }
        if (contieneNumeros(txtNombre.getText())) {
            mostrarAlerta("El nombre no debe contener números.");
            return false;
        }


        // Puedes agregar más validaciones según tus necesidades

        return true;
    }

    private boolean validarNumero(String input) {
        return input.matches("\\d+");
    }
    private boolean contieneNumeros(String input) {
        return input.matches(".*\\d+.*");
    }


    @FXML
    void irMenuEvent(ActionEvent event) throws IOException {
        new ViewController(ventana, "/ventanas/ventanaMenu.fxml");

    }

    @FXML
    void initialize() {

    }

}
