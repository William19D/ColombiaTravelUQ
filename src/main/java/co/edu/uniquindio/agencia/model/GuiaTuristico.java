package co.edu.uniquindio.agencia.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuiaTuristico  implements Serializable {

    private static final long serialVersioUID = 1L;
    private String nombre;
    private String identificacion;
    private List<Idiomas> idiomas;
    private String exp;
    private double calificacion;
    private String comentarios;

}
