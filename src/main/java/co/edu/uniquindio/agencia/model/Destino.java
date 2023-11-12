package co.edu.uniquindio.agencia.model;

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
    private static final long serialVersioUID = 1L;
    private String nombre;
    private String descripcion;
    private String ciudad;
    private List<File> rutasImagenes;
    private Clima clima;
    private double calificacion;
    private ArrayList<String> comentarios;

}
