package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.*;
import com.Sistema.GestionTramites.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorConsultaService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final OperadorAreaRepository operadorAreaRepository;
    private final ServicioRepository servicioRepository;
    private final SolicitudServicioRepository solicitudesRepository;
    private final OperadorServicioRepository operadorServicioRepository;

    public OperadorConsultaService(
            UsuarioSistemaRepository usuarioRepository,
            OperadorAreaRepository operadorAreaRepository,
            ServicioRepository servicioRepository,
            SolicitudServicioRepository solicitudesRepository,
            OperadorServicioRepository operadorServicioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.operadorAreaRepository = operadorAreaRepository;
        this.servicioRepository = servicioRepository;
        this.solicitudesRepository = solicitudesRepository;
        this.operadorServicioRepository = operadorServicioRepository;
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

        List<Integer> idsServiciosPropios = operadorServicioRepository
                .findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(operadorServicio -> operadorServicio.getServicio().getIdServicio())
                .toList();

        if (idsServiciosPropios.isEmpty()) {
            return List.of();
        }

        return solicitudesRepository.findByServicioIdServicioIn(idsServiciosPropios);
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (usuario.getTipoUsuario() != TipoUsuario.OPERADOR) {
            throw new BadRequestException("El usuario no es operador");
        }

        return usuario;
    }
}