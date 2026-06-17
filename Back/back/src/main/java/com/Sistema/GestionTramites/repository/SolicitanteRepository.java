package com.Sistema.GestionTramites.repository;
import com.Sistema.GestionTramites.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitanteRepository extends JpaRepository<Solicitante, Integer> {
}