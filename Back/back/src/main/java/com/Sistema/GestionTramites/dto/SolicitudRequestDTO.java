package com.Sistema.GestionTramites.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SolicitudRequestDTO {

    private String nombreCompleto;
    private String correo;
    private String tipoSolicitante;

    private Integer idServicio;
    private Integer idUsuarioRegistra;

    private LocalDateTime fechaRecepcion;
    private String motivoSolicitud;
}