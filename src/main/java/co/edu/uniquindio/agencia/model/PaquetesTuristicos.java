package co.edu.uniquindio.agencia.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private ArrayList<Destino> destinos;
    private String descripcion;
    private String serviciosAdicionales;
    private double precio;
    private int cupoMax;
    private LocalDate fechaDisponibleInicio;
    private LocalDate fechaDisponibleFin;
    private GuiaTuristico guia;



}
