package com.Sistema.GestionTramites.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "solicitante")
@Getter
@Setter
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitante")
    private Integer idSolicitante;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "tipo_solicitante", nullable = false, length = 80)
    private String tipoSolicitante;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
}