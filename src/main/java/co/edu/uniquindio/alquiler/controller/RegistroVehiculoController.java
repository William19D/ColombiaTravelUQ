package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.enums.Marca;
import co.edu.uniquindio.alquiler.exceptions.AtributoNegativoException;
import co.edu.uniquindio.alquiler.exceptions.AtributoVacioException;
import co.edu.uniquindio.alquiler.model.AlquilaFacil;
import co.edu.uniquindio.alquiler.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistroVehiculoController implements Initializable {

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtReferencia;

    @FXML
    private ComboBox<Marca> opcionesMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtUrFoto;

    @FXML
    private TextField txtKm;

    @FXML
    private TextField txtNumPuertas;

    @FXML
    private TextField txtPrecioDia;

    @FXML
    private ComboBox<String> opcionesCaja;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opcionesCaja.setItems( FXCollections.observableArrayList( List.of("Automática", "Manual") ) );
        opcionesMarca.setItems( FXCollections.observableArrayList( List.of( Marca.values() ) ) );
    }

    public void registrarVehiculo(ActionEvent actionEvent){

        if( opcionesMarca!= null && opcionesCaja!=null ) {

            try {
                Vehiculo vehiculo = alquilaFacil.registrarVehiculo(
                        txtPlaca.getText(),
                        txtReferencia.getText(),
                        opcionesMarca.getValue(),
                        Integer.parseInt(txtModelo.getText()),
                        txtUrFoto.getText(),
                        Integer.parseInt(txtKm.getText()),
                        Float.parseFloat(txtPrecioDia.getText()),
                        opcionesCaja.getValue().equals("Automática"),
                        Integer.parseInt(txtNumPuertas.getText())
                );

                mostrarMensaje(Alert.AlertType.INFORMATION, "El vehículo con placa "+vehiculo.getPlaca()+" se ha registrado correctamente");

            } catch (AtributoNegativoException | AtributoVacioException e) {
                mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
            } catch (NumberFormatException e1) {
                mostrarMensaje(Alert.AlertType.ERROR, "Tenga en cuenta que el número de puertas, modelo, precio por día y kilometraje deben ser números enteros");
            }

        }

    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }


}
