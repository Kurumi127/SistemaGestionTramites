package com.Sistema.GestionTramites.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSistemaRequestDTO {

    private String nombre;
    private String correo;
    private String contrasena;
    private String tipoUsuario;
    private String estado;
}