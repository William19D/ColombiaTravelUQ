package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.model.AlquilaFacil;
import co.edu.uniquindio.alquiler.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientesController implements Initializable {
    public TableView<Cliente> listaClientes;
    public TableColumn<Cliente, String> columnCedula;
    public TableColumn<Cliente, String> columnTel;
    public TableColumn<Cliente, String> columnNombre;
    public TableColumn<Cliente, String> columnEmail;
    public AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnCedula.setCellValueFactory( new PropertyValueFactory<>("cedula"));
        columnTel.setCellValueFactory( new PropertyValueFactory<>("telefono"));
        columnNombre.setCellValueFactory( new PropertyValueFactory<>("nombreCompleto"));
        columnEmail.setCellValueFactory( new PropertyValueFactory<>("email"));

        listaClientes.setItems(FXCollections.observableArrayList(alquilaFacil.getClientes()));

    }
}
