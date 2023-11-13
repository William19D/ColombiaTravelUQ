package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.*;
import co.edu.uniquindio.agencia.model.Administrador;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private AnchorPane ventanaMenuAdmins;

    @FXML
    private AnchorPane ventanaMenu;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();



    public ControllerInicioSesion() throws AtributoVacioException, ElementoNoEncontradoException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
    }

    public void initialize() {
        ArrayList<Cliente> clientes = agenciaViajes.getClientes();
        ArrayList<Administrador> administradores = agenciaViajes.getAdministradores();

    }

    @FXML
    void mostrarMenu(ActionEvent event) throws IOException, AtributoVacioException, ElementoNoEncontradoException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
        String usuario = labelUsuario.getText();
        String contrasena = labelContrasena.getText();
        boolean tipo = verificarCredenciales(usuario, contrasena);

        if (tipo) {
            AnchorPane ventana = FXMLLoader.load(getClass().getResource("/ventanas/ventanaMenuAdmins.fxml"));
            ventanaMenuAdmins.getChildren().setAll(ventana);
        } else {
            AnchorPane ventana = FXMLLoader.load(getClass().getResource("/ventanas/ventanaMenu.fxml"));
            ventanaMenu.getChildren().setAll(ventana);
        }

    }

    public boolean verificarCredenciales(String usuario, String contrasena) throws AtributoVacioException, ElementoNoEncontradoException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
        ArrayList<Cliente> clientes = agenciaViajes.getClientes();
        ArrayList<Administrador> administradores = agenciaViajes.getAdministradores();

        // Verificar si es un administrador
        for (Administrador admin : administradores) {
            if (admin.getUsuario().equals(usuario) && admin.getContrasenia().equals(contrasena)) {
                // Credenciales válidas para un administrador
                return true;
            }
        }

        // Verificar si es un cliente
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(usuario) && cliente.getContrasenia().equals(contrasena)) {
                // Credenciales válidas para un cliente
                return false;
            }
        }


        throw new ElementoNoEncontradoException("Usuario no encontrado o contraseña incorrecta.");
    }


}





