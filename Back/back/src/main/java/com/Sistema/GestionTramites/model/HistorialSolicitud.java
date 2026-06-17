package com.Sistema.GestionTramites.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_solicitud_finalizada")
@Getter
@Setter
public class HistorialSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @OneToOne
    @JoinColumn(name = "id_solicitud", nullable = false, unique = true)
    private SolicitudServicio solicitud;

    @Column(name = "folio", nullable = false, length = 50)
    private String folio;

    @Column(name = "fecha_recepcion", nullable = false)
    private LocalDateTime fechaRecepcion;

    @Column(name = "nombre_solicitante", nullable = false, length = 150)
    private String nombreSolicitante;

    @Column(name = "tipo_solicitante", nullable = false, length = 80)
    private String tipoSolicitante;

    @Column(name = "nombre_servicio", nullable = false, length = 200)
    private String nombreServicio;

    @Column(name = "nombre_area", nullable = false, length = 150)
    private String nombreArea;

    @Column(name = "nombre_usuario_registra", nullable = false, length = 150)
    private String nombreUsuarioRegistra;

    @Column(name = "fecha_creacion_solicitud", nullable = false)
    private LocalDateTime fechaCreacionSolicitud;

    @Column(name = "fecha_finalizacion", nullable = false)
    private LocalDateTime fechaFinalizacion;

    @Column(name = "observacion_final")
    private String observacionFinal;
}
