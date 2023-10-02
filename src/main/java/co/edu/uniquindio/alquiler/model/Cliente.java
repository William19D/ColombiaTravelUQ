package co.edu.uniquindio.alquiler.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    private String cedula;
    private String nombreCompleto;
    private String email;
    private String direccion;
    private String ciudad;
    private String telefono;

}
