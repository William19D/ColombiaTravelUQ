package co.edu.uniquindio.agencia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.agencia.app.App;
import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.persistencia.Persistencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class VentanaRegistroCliente{

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnIrMenu;


    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private App main;

    @FXML
    private AnchorPane ventana;



    public VentanaRegistroCliente() throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }


    @FXML
    void cancelarEvent(ActionEvent event) {

    }

    @FXML
    void registrarClienteEvent(ActionEvent event) {
        if (validarCampos()) {
            registrarClienteAction();
        }
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

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }


    private void registrarClienteAction() {


        try {
            agenciaViajes.registrarCliente(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    txtContrasenia.getText()

            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el cliente con la cedula  "+txtCedula.getText());
            alert.show();
            limpiarTexto();

            btnIrMenu.setDisable(false);

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

    @FXML
    void irMenuEvent(ActionEvent event) throws IOException {
        // Verificar si el botón btnIrMenu está habilitado
        if (!btnIrMenu.isDisabled()) {
            new ViewController(ventana, "/ventanas/inicioSesion.fxml");
        }
    }

    @FXML
    void vovlerEvent(ActionEvent event) throws IOException {
        new ViewController(ventana, "/ventanas/inicio.fxml");

    }

    @FXML
    void initialize() {
        btnIrMenu.setDisable(true);

    }

    public void setApplication(App main) {
        this.main=main;
    }

}
