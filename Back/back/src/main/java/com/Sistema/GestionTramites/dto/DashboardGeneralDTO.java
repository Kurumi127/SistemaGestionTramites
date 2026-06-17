package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardGeneralDTO {

    private Long totalOperadores;
    private Long totalAreasActivas;
    private Long totalServiciosActivos;
    private Long totalSolicitudes;
    private Long solicitudesRecibidas;
    private Long solicitudesEnProceso;
    private Long solicitudesFinalizadas;
    private Long solicitudesCanceladas;
}