package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String tipoUsuario;
    private String estado;
}