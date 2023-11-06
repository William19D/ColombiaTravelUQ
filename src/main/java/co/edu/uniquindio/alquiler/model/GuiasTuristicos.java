package co.edu.uniquindio.alquiler.model;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuiasTuristicos {

    private String nombre;
    private String identificacion;
    private Idiomas idiomas;
    private String exp;

}
