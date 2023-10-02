package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.model.AlquilaFacil;
import co.edu.uniquindio.alquiler.model.Alquiler;
import co.edu.uniquindio.alquiler.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroAlquilerController  implements Initializable {

    @FXML
    private TableView<Vehiculo> listaVehiculos;

    @FXML
    private TableColumn<Vehiculo, String> colPlaca;

    @FXML
    private TableColumn<Vehiculo, String> colReferencia;

    @FXML
    private TableColumn<Vehiculo, String> colMarca;

    @FXML
    private TableColumn<Vehiculo, Integer> colModelo;

    @FXML
    private TableColumn<Vehiculo, Float> colPrecio;

    @FXML
    private TextField txtCedula;

    @FXML
    private DatePicker txtFechaInicio;

    @FXML
    private DatePicker txtFechaFin;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioDia"));

        listaVehiculos.setItems(FXCollections.observableArrayList( alquilaFacil.getVehiculos() ));

    }

    public void registrarAlquiler(){

        Vehiculo vehiculo = listaVehiculos.getSelectionModel().getSelectedItem();

        if(vehiculo!=null) {

            try {

                Alquiler alquiler = alquilaFacil.registrarAlquiler(
                        txtCedula.getText(),
                        vehiculo.getPlaca(),
                        txtFechaInicio.getValue(),
                        txtFechaFin.getValue());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Se ha registrado correctamente el préstamo, el valor a pagar es "+alquiler.getValorTotal());
                alert.show();

            }catch (Exception e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.show();
            }

        }

    }

}
