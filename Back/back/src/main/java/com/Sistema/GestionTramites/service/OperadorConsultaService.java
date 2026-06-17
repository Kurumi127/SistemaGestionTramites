package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.model.*;
import com.Sistema.GestionTramites.repository.OperadorAreaRepository;
import com.Sistema.GestionTramites.repository.ServicioRepository;
import com.Sistema.GestionTramites.repository.SolicitudServicioRepository;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorConsultaService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final OperadorAreaRepository operadorAreaRepository;
    private final ServicioRepository servicioRepository;
    private final SolicitudServicioRepository solicitudesRepository;

    public OperadorConsultaService(
            UsuarioSistemaRepository usuarioRepository,
            OperadorAreaRepository operadorAreaRepository,
            ServicioRepository servicioRepository,
            SolicitudServicioRepository solicitudesRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.operadorAreaRepository = operadorAreaRepository;
        this.servicioRepository = servicioRepository;
        this.solicitudesRepository = solicitudesRepository;
    }

    public List<AreaServicio> obtenerAreasDelOperador(Integer idUsuario) {
        UsuarioSistema usuario = validarOperador(idUsuario);

        return operadorAreaRepository.findByUsuarioIdUsuario(usuario.getIdUsuario())
                .stream()
                .map(OperadorArea::getArea)
                .toList();
    }

    public List<Servicio> obtenerServiciosDelOperador(Integer idUsuario) {
        List<Integer> idsAreas = obtenerIdsAreasDelOperador(idUsuario);

        if (idsAreas.isEmpty()) {
            return List.of();
        }

        return servicioRepository.findByAreaIdAreaIn(idsAreas);
    }

    public List<SolicitudServicio> obtenerSolicitudesDelOperador(Integer idUsuario) {
        List<Integer> idsAreas = obtenerIdsAreasDelOperador(idUsuario);

        if (idsAreas.isEmpty()) {
            return List.of();
        }

        return solicitudesRepository.findByServicioAreaIdAreaIn(idsAreas);
    }

    private List<Integer> obtenerIdsAreasDelOperador(Integer idUsuario) {
        UsuarioSistema usuario = validarOperador(idUsuario);

        return operadorAreaRepository.findByUsuarioIdUsuario(usuario.getIdUsuario())
                .stream()
                .map(operadorArea -> operadorArea.getArea().getIdArea())
                .toList();
    }

    private UsuarioSistema validarOperador(Integer idUsuario) {
        UsuarioSistema usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getTipoUsuario() != TipoUsuario.OPERADOR) {
            throw new RuntimeException("El usuario no es operador");
        }

        return usuario;
    }
}