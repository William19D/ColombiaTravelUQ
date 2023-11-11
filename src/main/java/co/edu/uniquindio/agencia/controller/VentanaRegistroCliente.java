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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class VentanaRegistroCliente {

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


    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private App main;

    public VentanaRegistroCliente() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
    }

    @FXML
    void cancelarEvent(ActionEvent event) {

    }

    @FXML
    void registrarClienteEvent(ActionEvent event) {
        registrarClienteAction();
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
    void initialize() {

    }

    public void setApplication(App main) {
        this.main=main;
    }

}
