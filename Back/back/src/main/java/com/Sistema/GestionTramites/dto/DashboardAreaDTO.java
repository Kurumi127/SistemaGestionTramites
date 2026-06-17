package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardAreaDTO {

    private Integer idArea;
    private String nombreArea;
    private Long serviciosActivos;
    private Long solicitudesRecibidas;
    private Long solicitudesEnProceso;
    private Long solicitudesFinalizadas;
    private Long solicitudesCanceladas;
    private Long totalSolicitudes;
}