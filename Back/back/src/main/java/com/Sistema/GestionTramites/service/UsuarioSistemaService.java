package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.UsuarioSistemaRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioRepository;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioSistema> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioSistema> listarOperadores() {
        return usuarioRepository.findByTipoUsuario(TipoUsuario.OPERADOR);
    }

    public UsuarioSistema obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public UsuarioSistema crearUsuario(UsuarioSistemaRequestDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        TipoUsuario tipoUsuario = TipoUsuario.valueOf(dto.getTipoUsuario());

        if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
            boolean yaExisteAdministrador = usuarioRepository.findByTipoUsuario(TipoUsuario.ADMINISTRADOR)
                    .stream()
                    .findAny()
                    .isPresent();

            if (yaExisteAdministrador) {
                throw new RuntimeException("Solo puede existir un administrador en el sistema");
            }
        }

        UsuarioSistema usuario = new UsuarioSistema();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setTipoUsuario(tipoUsuario);

        if (dto.getEstado() == null || dto.getEstado().isBlank()) {
            usuario.setEstado(

                    EstadoGeneral.ACTIVO);
        } else {
            usuario.setEstado(EstadoGeneral.valueOf(dto.getEstado()));
        }

        return usuarioRepository.save(usuario);
    }

    public UsuarioSistema editarUsuario(Integer id, UsuarioSistemaRequestDTO dto) {
        UsuarioSistema usuario = obtenerPorId(id);

        usuarioRepository.findByCorreo(dto.getCorreo()).ifPresent(usuarioExistente -> {
            if (!usuarioExistente.getIdUsuario().equals(id)) {
                throw new RuntimeException("Ya existe otro usuario con ese correo");
            }
        });

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());

        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            usuario.setContrasena(dto.getContrasena());
        }

        usuario.setTipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario()));
        usuario.setEstado(EstadoGeneral.valueOf(dto.getEstado()));

        return usuarioRepository.save(usuario);
    }

    public UsuarioSistema cambiarEstado(Integer id, String estado) {
        UsuarioSistema usuario = obtenerPorId(id);
        usuario.setEstado(EstadoGeneral.valueOf(estado));
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Integer id) {
        UsuarioSistema usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }
}
