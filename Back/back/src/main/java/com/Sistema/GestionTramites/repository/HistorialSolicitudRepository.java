package com.Sistema.GestionTramites.repository;
import com.Sistema.GestionTramites.model.HistorialSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialSolicitudRepository extends JpaRepository<HistorialSolicitud, Integer> {
    boolean existsBySolicitudIdSolicitud(Integer idSolicitud);
}