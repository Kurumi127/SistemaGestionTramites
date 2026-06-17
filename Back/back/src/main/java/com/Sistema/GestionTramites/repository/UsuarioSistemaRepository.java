package com.Sistema.GestionTramites.repository;

import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Integer> {

    List<UsuarioSistema> findByTipoUsuario(TipoUsuario tipoUsuario);

    Optional<UsuarioSistema> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    long countByTipoUsuario(TipoUsuario tipoUsuario);
}