package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardOperadorDTO {

    private Integer idUsuario;
    private String nombreOperador;

    private Long totalServiciosPropios;
    private Long totalSolicitudes;

    private Long solicitudesRecibidas;
    private Long solicitudesEnProceso;
    private Long solicitudesFinalizadas;
    private Long solicitudesCanceladas;
}