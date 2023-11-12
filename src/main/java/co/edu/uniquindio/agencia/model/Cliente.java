package co.edu.uniquindio.agencia.model;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente  implements Serializable {
    private static final long serialVersioUID = 1L;
    private String cedula;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;
    private String contrasenia;

}
