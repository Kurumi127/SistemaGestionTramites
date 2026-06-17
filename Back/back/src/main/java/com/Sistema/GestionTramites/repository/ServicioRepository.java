package com.Sistema.GestionTramites.repository;

import com.Sistema.GestionTramites.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Sistema.GestionTramites.enums.EstadoGeneral;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findByAreaIdArea(Integer idArea);

    List<Servicio> findByAreaIdAreaIn(List<Integer> idsAreas);
    
    long countByEstado(EstadoGeneral estado);

    long countByAreaIdAreaAndEstado(Integer idArea, EstadoGeneral estado);
}