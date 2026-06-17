package com.Sistema.GestionTramites.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String tipoUsuario;
    private String estado;
}
