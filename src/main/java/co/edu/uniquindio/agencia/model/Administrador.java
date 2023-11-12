package co.edu.uniquindio.agencia.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Administrador implements Serializable {
    private static final long serialVersioUID = 1L;
    private String usuario;
    private String contrasenia;


}
