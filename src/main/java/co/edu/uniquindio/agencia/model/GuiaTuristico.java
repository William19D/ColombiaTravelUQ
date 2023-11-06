package co.edu.uniquindio.agencia.model;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuiaTuristico {

    private String nombre;
    private String identificacion;
    private Idiomas idiomas;
    private String exp;
    private double calificacion;
    private String comentarios;
}
