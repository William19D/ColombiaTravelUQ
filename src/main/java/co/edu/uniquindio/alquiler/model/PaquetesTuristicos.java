package co.edu.uniquindio.alquiler.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaquetesTuristicos implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    private String nombre;
    private String direccion;
    private String serviciosAdicionales;
    private float precio;
    private int cupoMax;
    private LocalDate fechaDisponible;


}
