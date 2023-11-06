package co.edu.uniquindio.alquiler.model;

import java.time.LocalDate;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Reservas {

    private LocalDate fechaSolicitud;
    private LocalDate fechaPlanificada;
    private Cliente cliente;
    private int cantidadPersonas;
    private PaquetesTuristicos paquete;
    private boolean guia;


}
