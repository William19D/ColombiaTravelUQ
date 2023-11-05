package co.edu.uniquindio.alquiler.controller;

import co.edu.uniquindio.alquiler.model.MatrizHilo1;
import co.edu.uniquindio.alquiler.model.MatrizHilo2;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MatrizController implements Initializable {

    @FXML
    private Pane panel;
    private Rectangle[][] matriz1, matriz2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Se inicializan las dos matrices, una al lado de la otra
        matriz1 = crearMatrizBotones(4, 0);
        matriz2 = crearMatrizBotones(4, 250);
    }

    public void iniciarLlenado(){
        //Se crea el hilo y se le pasa la matriz
        MatrizHilo1 hilo1 = new MatrizHilo1(matriz1);
        MatrizHilo2 hilo2 = new MatrizHilo2(matriz2);
        hilo1.start();
        hilo2.start();
    }

    private Rectangle[][] crearMatrizBotones(int n, int xInicial){

        Rectangle[][] botones = new Rectangle[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){

                Rectangle boton = new Rectangle();
                boton.setWidth(50);
                boton.setHeight(50);
                boton.setFill( Color.WHITE );

                //En cada iteración se actualiza la posición del botón en el eje X, X cambia cuando cambia el valor de j. Ya que x representa las columnas (horizontal).
                boton.setLayoutX( xInicial + (50+3)*j );

                //En cada iteración se actualiza la posición del botón en el eje Y, Y cambia cuando cambia el valor de i. Ya que y representa las filas (vertical).
                boton.setLayoutY( (50+3)*i );

                botones[i][j] = boton;

                //El botón se añade al panel para poder visualizarlo
                panel.getChildren().add(boton);
            }
        }
        return botones;
    }
}
