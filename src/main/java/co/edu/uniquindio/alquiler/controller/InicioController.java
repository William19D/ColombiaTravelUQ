package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.utils.Idiomas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.extern.java.Log;

import java.net.URL;
import java.util.ResourceBundle;

@Log
public class InicioController implements Initializable {

    @FXML
    private AnchorPane panelFormulario;
    @FXML
    private Button btnInicioRegCli;
    @FXML
    private Button btnInicioRegVeh;
    @FXML
    private Button btnInicioRegAlq;
    @FXML
    private Button btnCambiarIdioma;
    @FXML
    public Button btnClientes;
    private final Idiomas idiomas = Idiomas.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTextos();
    }

    public void mostrarRegistroCliente(){

        try {
            Node node = FXMLLoader.load(getClass().getResource("/ventanas/registroCliente.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarRegistroVehiculo(){
        try {
            Node node = FXMLLoader.load(getClass().getResource("/ventanas/registroVehiculo.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarRegistroAlquiler(){
        try {
            Node node = FXMLLoader.load(getClass().getResource("/ventanas/registroAlquiler.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarClientes(){
        try {
            Node node = FXMLLoader.load(getClass().getResource("/ventanas/clientes.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void cambiarIdioma(){
        if( idiomas.getIdiomaActual().equals("es") ){
            idiomas.cambiarIdioma("en");
        }else{
            idiomas.cambiarIdioma("es");
        }

        cargarTextos();

    }


    public void cargarTextos(){
        String txtBtn1 = idiomas.getResourceBundle().getString("btnFormRegistroCliente");
        btnInicioRegCli.setText(txtBtn1);

        String txtBtn2 = idiomas.getResourceBundle().getString("btnFormRegistroVehiculo");
        btnInicioRegVeh.setText(txtBtn2);

        String txtBtn3 = idiomas.getResourceBundle().getString("btnFormRegistroAlquiler");
        btnInicioRegAlq.setText(txtBtn3);

        String txtBtn4 = idiomas.getResourceBundle().getString("btnClientes");
        btnClientes.setText(txtBtn4);

        String txtBtn5 = idiomas.getResourceBundle().getString("btnCambiarIdioma");
        btnCambiarIdioma.setText(txtBtn5);
    }

}
