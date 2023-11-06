package co.edu.uniquindio.agencia.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MatrizHilo2 extends Thread {
    private Rectangle[][] matriz;

    public MatrizHilo2(Rectangle[][] matriz) {
        this.matriz = matriz;
    }

    public void run() {
        llenarMatriz1(matriz);
    }

    private void llenarMatriz1(Rectangle[][] m) {
        int n = m.length;

        for (int k = 0; k < 2 * n - 1; k++) {
            int iInicio = Math.max(0, k - (n - 1));
            int iFin = Math.min(n - 1, k);

            for (int i = iInicio; i <= iFin; i++) {
                int j = k - i;
                m[i][j].setFill(Color.BLUEVIOLET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
