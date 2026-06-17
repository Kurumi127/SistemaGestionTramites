package com.Sistema.GestionTramites.model;

import com.Sistema.GestionTramites.enums.EstadoGeneral;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "servicio")
@Getter
@Setter
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(name = "numero_servicio", nullable = false, unique = true, length = 50)
    private String numeroServicio;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tiempo_interno_dias", nullable = false)
    private Integer tiempoInternoDias;

    @Column(name = "tiempo_externo_dias")
    private Integer tiempoExternoDias;

    @Column(name = "tiempo_total_dias", nullable = false)
    private Integer tiempoTotalDias;

    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private AreaServicio area;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoGeneral estado;
}