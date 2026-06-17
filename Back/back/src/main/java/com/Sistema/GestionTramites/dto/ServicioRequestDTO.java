package com.Sistema.GestionTramites.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioRequestDTO {

    private String numeroServicio;
    private String nombre;
    private String descripcion;
    private Integer tiempoInternoDias;
    private Integer tiempoExternoDias;
    private Integer tiempoTotalDias;
    private Integer idArea;
    private String estado;
}
