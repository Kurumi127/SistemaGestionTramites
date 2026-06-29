package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperadorServicioResponseDTO {

    private Integer idOperadorServicio;

    private Integer idUsuario;
    private String nombreOperador;

    private Integer idServicio;
    private String nombreServicio;
    private String numeroServicio;

    private Integer idArea;
    private String nombreArea;
}