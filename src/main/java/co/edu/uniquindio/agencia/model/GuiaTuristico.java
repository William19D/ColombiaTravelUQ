package co.edu.uniquindio.agencia.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuiaTuristico {

    private String nombre;
    private String identificacion;
    private List<Idiomas> idiomas;
    private String exp;
    private double calificacion;
    private String comentarios;

}
