package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.model.AlquilaFacil;
import co.edu.uniquindio.alquiler.model.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ListaClientesController implements Initializable {
    public TableView<Cliente> listaClientes;
    public TableColumn<Cliente, String> columnCedula;
    public TableColumn<Cliente, String> columnTel;
    public TableColumn<Cliente, String> columnNombre;
    public TableColumn<Cliente, String> columnEmail;
    public AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnCedula.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getCedula()) );
        columnTel.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getTelefono()) );
        columnNombre.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getNombreCompleto()) );
        columnEmail.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getEmail()) );

        listaClientes.setItems(FXCollections.observableArrayList(alquilaFacil.getClientes()));

    }
}
