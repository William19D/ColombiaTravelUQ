package co.edu.uniquindio.alquiler.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class InicioController {

    @FXML
    private AnchorPane panelFormulario;

    public void mostrarRegistroCliente(){

        try {
            Node node = FXMLLoader.load(getClass().getResource("/registroCliente.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarRegistroVehiculo(){
        try {
            Node node = FXMLLoader.load(getClass().getResource("/registroVehiculo.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarRegistroAlquiler(){
        try {
            Node node = FXMLLoader.load(getClass().getResource("/registroAlquiler.fxml"));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
