package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.*;
import co.edu.uniquindio.agencia.model.Administrador;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Cliente;
import co.edu.uniquindio.agencia.model.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
public class ControllerInicioSesion {

    @FXML
    private Button btnInicioSesion;

    @FXML
    private TextField labelContrasena;

    @FXML
    private TextField labelUsuario;

    @FXML
    private AnchorPane ventana;
    @FXML
    private Button btnVolver;


    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();



    public ControllerInicioSesion() throws AtributoVacioException, ElementoNoEncontradoException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }

    public void initialize() {
        ArrayList<Cliente> clientes = agenciaViajes.getClientes();
        ArrayList<Administrador> administradores = agenciaViajes.getAdministradores();

    }

    @FXML
    void mostrarMenu(ActionEvent event) throws IOException, AtributoVacioException, ElementoNoEncontradoException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
        String cedula = labelUsuario.getText();
        String contrasena = labelContrasena.getText();

        try {
            if (agenciaViajes.verificarClienteAdministrador(cedula, contrasena)) {
                Cliente cliente = agenciaViajes.obtenerCliente(cedula);
                if (cliente != null) {
                    SessionManager.getInstance().setCliente(cliente);
                    new ViewController(ventana, "/ventanas/ventanaMenu.fxml");
                } else {
                    new ViewController(ventana, "/ventanas/ventanaMenuAdmins.fxml");
                }
            } else {

                if (agenciaViajes.obtenerCliente(cedula) == null && agenciaViajes.obtenerAdministrador(cedula) == null) {
                    mostrarAlerta("Error", "El usuario no existe");
                }

                else {
                    mostrarAlerta("Error", "Contraseña incorrecta");
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    @FXML
    void vovlerEvent(ActionEvent event) throws IOException {
        new ViewController(ventana, "/ventanas/inicio.fxml");


    }



}





