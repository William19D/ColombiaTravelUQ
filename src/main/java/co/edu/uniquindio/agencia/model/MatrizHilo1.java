package co.edu.uniquindio.agencia.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MatrizHilo1 extends Thread {
    private Rectangle[][] matriz;

    public MatrizHilo1(Rectangle[][] matriz) {
        this.matriz = matriz;
    }

    public void run() {
        llenarMatriz1(matriz);
    }

    private void llenarMatriz1(Rectangle[][] m) {
        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < m.length; j++) {
                m[i][j].setFill( Color.RED );
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
