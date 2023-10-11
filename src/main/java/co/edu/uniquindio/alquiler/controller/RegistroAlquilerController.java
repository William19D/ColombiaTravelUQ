package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.model.AlquilaFacil;
import co.edu.uniquindio.alquiler.model.Alquiler;
import co.edu.uniquindio.alquiler.model.Vehiculo;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Vehiculo, String> colModelo;

    @FXML
    private TableColumn<Vehiculo, String> colPrecio;

    @FXML
    private TextField txtCedula;

    @FXML
    private DatePicker txtFechaInicio;

    @FXML
    private DatePicker txtFechaFin;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colPlaca.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getPlaca()) );
        colReferencia.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getReferencia()) );
        colMarca.setCellValueFactory( cellData -> new SimpleStringProperty( String.valueOf(cellData.getValue().getMarca()) ) );
        colModelo.setCellValueFactory( cellData -> new SimpleStringProperty( String.valueOf(cellData.getValue().getModelo()) ) );
        colPrecio.setCellValueFactory( cellData -> new SimpleStringProperty( String.valueOf(cellData.getValue().getPrecioDia()) ) );

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

                mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha registrado correctamente el préstamo, el valor a pagar es "+alquiler.getValorTotal());

            }catch (Exception e){
                mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
            }

        }else{
            mostrarMensaje(Alert.AlertType.ERROR, "Debe seleccionar el vehículo de la tabla");
        }

    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}
