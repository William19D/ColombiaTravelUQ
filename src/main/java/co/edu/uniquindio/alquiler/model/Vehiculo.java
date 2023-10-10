package co.edu.uniquindio.alquiler.model;

import co.edu.uniquindio.alquiler.enums.Marca;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vehiculo implements Serializable {

    @EqualsAndHashCode.Include
    private String placa;

    private String referencia;
    private Marca marca;
    private int modelo;
    private String foto;
    private int kilometraje;
    private float precioDia;
    private boolean esAutomatico;
    private int numPuertas;
}
