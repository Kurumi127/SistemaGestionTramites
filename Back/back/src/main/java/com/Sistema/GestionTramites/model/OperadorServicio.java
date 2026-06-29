package com.Sistema.GestionTramites.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "operador_servicio")
@Getter
@Setter
public class OperadorServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operador_servicio")
    private Integer idOperadorServicio;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioSistema usuario;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;
}