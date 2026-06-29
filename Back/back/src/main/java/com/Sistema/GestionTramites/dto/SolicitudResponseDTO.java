package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SolicitudResponseDTO {

    private Integer idSolicitud;
    private String folio;
    private LocalDateTime fechaRecepcion;
    private String motivoSolicitud;
    private String estado;

    private Integer idSolicitante;
    private String nombreSolicitante;
    private String correoSolicitante;
    private String tipoSolicitante;

    private Integer idServicio;
    private String numeroServicio;
    private String nombreServicio;

    private Integer idArea;
    private String nombreArea;

    private Integer idUsuarioRegistra;
    private String nombreUsuarioRegistra;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}