package com.Sistema.GestionTramites.repository;

import com.Sistema.GestionTramites.model.OperadorServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OperadorServicioRepository extends JpaRepository<OperadorServicio, Integer> {


    List<OperadorServicio> findByUsuarioIdUsuario(Integer idUsuario);

    List<OperadorServicio> findByServicioIdServicio(Integer idServicio);

    Optional<OperadorServicio> findByUsuarioIdUsuarioAndServicioIdServicio(
            Integer idUsuario,
            Integer idServicio
    );

    boolean existsByUsuarioIdUsuarioAndServicioIdServicio(
            Integer idUsuario,
            Integer idServicio
    );

    void deleteByUsuarioIdUsuarioAndServicioIdServicio(
            Integer idUsuario,
            Integer idServicio
    );
}