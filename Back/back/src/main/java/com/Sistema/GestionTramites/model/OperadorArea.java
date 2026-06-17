package com.Sistema.GestionTramites.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "operador_area")
@Getter
@Setter
public class OperadorArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operador_area")
    private Integer idOperadorArea;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioSistema usuario;

    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private AreaServicio area;
}
