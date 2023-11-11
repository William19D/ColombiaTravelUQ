package co.edu.uniquindio.agencia.controller;

import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerBuscadorPaquetes {

    @FXML
    private DatePicker fechaIda;

    @FXML
    private DatePicker fechaVuelta;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private AnchorPane scrollAnchorPaquetes;

    @FXML
    private ScrollPane scrollPaquetes;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtClima;

    @FXML
    private TextField txtPersonas;

    @FXML
    private TextField txtPresupuesto;

    @FXML
    private AnchorPane ventanaBuscadorPaquetes;

    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    public ControllerBuscadorPaquetes() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException {
    }


    public void initialize() throws IOException {
        scrollPaquetes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        new ViewController(scrollAnchorPaquetes, "/ventanas/paquete.fxml");


    }


    }
