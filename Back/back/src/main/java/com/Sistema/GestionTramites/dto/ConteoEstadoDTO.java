package com.Sistema.GestionTramites.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConteoEstadoDTO {

    private String estado;
    private Long total;
}