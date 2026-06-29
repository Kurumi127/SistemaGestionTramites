package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AreaResponseDTO {

    private Integer idArea;
    private String nombre;
    private String descripcion;
    private String estado;
}