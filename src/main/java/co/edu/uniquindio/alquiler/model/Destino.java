package co.edu.uniquindio.alquiler.model;

import javafx.stage.FileChooser;
import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destino implements Serializable {

    private String nombre;
    private String descripcion;
    private ArrayList<File> rutasImagenes;
    private Clima clima;
}
