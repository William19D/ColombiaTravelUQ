package co.edu.uniquindio.alquiler.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {

    private String cedula;
    private String nombreCompleto;
    private String email;
    private String direccion;
    private String ciudad;
    private String telefono;

}
