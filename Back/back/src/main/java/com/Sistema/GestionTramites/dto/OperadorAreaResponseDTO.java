package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperadorAreaResponseDTO {

    private Integer idOperadorArea;

    private Integer idUsuario;
    private String nombreOperador;
    private String correoOperador;

    private Integer idArea;
    private String nombreArea;
}