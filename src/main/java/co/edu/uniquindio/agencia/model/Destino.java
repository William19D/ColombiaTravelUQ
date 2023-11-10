package co.edu.uniquindio.agencia.model;

import javafx.stage.FileChooser;
import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private double calificacion;
    private ArrayList<String> comentarios;
    private FileChooser foto;
}
