package com.Sistema.GestionTramites.repository;
import com.Sistema.GestionTramites.dto.SolicitudesPorAreaDTO;
import com.Sistema.GestionTramites.enums.EstadoSolicitud;
import com.Sistema.GestionTramites.model.SolicitudServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Integer> {
    List<SolicitudServicio> findByServicioAreaIdArea(Integer idArea);

    List<SolicitudServicio> findByServicioAreaIdAreaIn(List<Integer> idsAreas);

    long countByEstado(EstadoSolicitud estado);

    long countByServicioAreaIdArea(Integer idArea);

    long countByServicioAreaIdAreaAndEstado(Integer idArea, EstadoSolicitud estado);

    @Query("""
        SELECT new com.Sistema.GestionTramites.dto.SolicitudesPorAreaDTO(
            a.idArea,
            a.nombre,
            COUNT(s.idSolicitud)
        )
        FROM SolicitudServicio s
        JOIN s.servicio sv
        JOIN sv.area a
        GROUP BY a.idArea, a.nombre
        ORDER BY COUNT(s.idSolicitud) DESC
    """)
    List<SolicitudesPorAreaDTO> contarSolicitudesPorArea();
}
