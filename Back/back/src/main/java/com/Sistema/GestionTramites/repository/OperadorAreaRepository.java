package com.Sistema.GestionTramites.repository;

import com.Sistema.GestionTramites.model.OperadorArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperadorAreaRepository extends JpaRepository<OperadorArea, Integer> {

    List<OperadorArea> findByUsuarioIdUsuario(Integer idUsuario);

    List<OperadorArea> findByAreaIdArea(Integer idArea);

    Optional<OperadorArea> findByUsuarioIdUsuarioAndAreaIdArea(Integer idUsuario, Integer idArea);

    boolean existsByUsuarioIdUsuarioAndAreaIdArea(Integer idUsuario, Integer idArea);
}