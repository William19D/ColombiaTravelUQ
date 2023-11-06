package co.edu.uniquindio.agencia.model;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    private String cedula;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;
    private String contrasenia;

}
