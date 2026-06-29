package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServicioResponseDTO {

    private Integer idServicio;
    private String numeroServicio;
    private String nombre;
    private String descripcion;
    private Integer tiempoInternoDias;
    private Integer tiempoExternoDias;
    private Integer tiempoTotalDias;

    private Integer idArea;
    private String nombreArea;

    private String estado;
}