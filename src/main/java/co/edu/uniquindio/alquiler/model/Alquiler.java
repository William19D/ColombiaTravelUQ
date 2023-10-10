package co.edu.uniquindio.alquiler.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Alquiler implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDateTime fechaRegistro;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private float valorTotal;

}
