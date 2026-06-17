package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SolicitudesPorAreaDTO {

    private Integer idArea;
    private String nombreArea;
    private Long totalSolicitudes;
}