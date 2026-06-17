package com.Sistema.GestionTramites.repository;

import com.Sistema.GestionTramites.model.AreaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Sistema.GestionTramites.enums.EstadoGeneral;

public interface AreaServicioRepository extends JpaRepository<AreaServicio, Integer> {
    boolean existsByNombre(String nombre);
    long countByEstado(EstadoGeneral estado);
}