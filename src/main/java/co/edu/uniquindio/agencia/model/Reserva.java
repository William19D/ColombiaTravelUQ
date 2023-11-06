package co.edu.uniquindio.agencia.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Reserva implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private LocalDate fechaSolicitud;
    private LocalDate fechaPlanificada;
    private Cliente cliente;
    private int cantidadPersonas;
    private PaquetesTuristicos paquete;
    private boolean guia;


}
