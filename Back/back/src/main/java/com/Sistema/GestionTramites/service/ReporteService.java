package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.DashboardAreaDTO;
import com.Sistema.GestionTramites.dto.DashboardGeneralDTO;
import com.Sistema.GestionTramites.dto.DashboardOperadorDTO;
import com.Sistema.GestionTramites.dto.SolicitudesPorAreaDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.enums.EstadoSolicitud;
import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.model.SolicitudServicio;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final AreaServicioRepository areaRepository;
    private final ServicioRepository servicioRepository;
    private final SolicitudServicioRepository solicitudRepository;
    private final OperadorServicioRepository operadorServicioRepository;

    public ReporteService(
            UsuarioSistemaRepository usuarioRepository,
            AreaServicioRepository areaRepository,
            ServicioRepository servicioRepository,
            SolicitudServicioRepository solicitudRepository,
            OperadorServicioRepository operadorServicioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
        this.servicioRepository = servicioRepository;
        this.solicitudRepository = solicitudRepository;
        this.operadorServicioRepository = operadorServicioRepository;
    }

    public DashboardGeneralDTO obtenerDashboardGeneral() {
        return new DashboardGeneralDTO(
                usuarioRepository.countByTipoUsuario(TipoUsuario.OPERADOR),
                areaRepository.countByEstado(EstadoGeneral.ACTIVO),
                servicioRepository.countByEstado(EstadoGeneral.ACTIVO),
                solicitudRepository.count(),
                solicitudRepository.countByEstado(EstadoSolicitud.RECIBIDA),
                solicitudRepository.countByEstado(EstadoSolicitud.EN_PROCESO),
                solicitudRepository.countByEstado(EstadoSolicitud.FINALIZADA),
                solicitudRepository.countByEstado(EstadoSolicitud.CANCELADA)
        );
    }

    public DashboardAreaDTO obtenerDashboardArea(Integer idArea) {
        AreaServicio area = areaRepository.findById(idArea)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));

        Long serviciosActivos = servicioRepository.countByAreaIdAreaAndEstado(idArea, EstadoGeneral.ACTIVO);
        Long recibidas = solicitudRepository.countByServicioAreaIdAreaAndEstado(idArea, EstadoSolicitud.RECIBIDA);
        Long enProceso = solicitudRepository.countByServicioAreaIdAreaAndEstado(idArea, EstadoSolicitud.EN_PROCESO);
        Long finalizadas = solicitudRepository.countByServicioAreaIdAreaAndEstado(idArea, EstadoSolicitud.FINALIZADA);
        Long canceladas = solicitudRepository.countByServicioAreaIdAreaAndEstado(idArea, EstadoSolicitud.CANCELADA);
        Long total = solicitudRepository.countByServicioAreaIdArea(idArea);

        return new DashboardAreaDTO(
                area.getIdArea(),
                area.getNombre(),
                serviciosActivos,
                recibidas,
                enProceso,
                finalizadas,
                canceladas,
                total
        );
    }

    public DashboardOperadorDTO obtenerDashboardOperador(Integer idUsuario) {
        UsuarioSistema operador = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (operador.getTipoUsuario() != TipoUsuario.OPERADOR) {
            throw new BadRequestException("El usuario no es operador");
        }

        List<Integer> idsServiciosPropios = operadorServicioRepository
                .findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(operadorServicio -> operadorServicio.getServicio().getIdServicio())
                .toList();

        if (idsServiciosPropios.isEmpty()) {
            return new DashboardOperadorDTO(
                    operador.getIdUsuario(),
                    operador.getNombre(),
                    0L,
                    0L,
                    0L,
                    0L,
                    0L,
                    0L
            );
        }

        List<SolicitudServicio> solicitudes = solicitudRepository
                .findByServicioIdServicioIn(idsServiciosPropios);

        long recibidas = solicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitud.RECIBIDA)
                .count();

        long enProceso = solicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitud.EN_PROCESO)
                .count();

        long finalizadas = solicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitud.FINALIZADA)
                .count();

        long canceladas = solicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitud.CANCELADA)
                .count();

        return new DashboardOperadorDTO(
                operador.getIdUsuario(),
                operador.getNombre(),
                (long) idsServiciosPropios.size(),
                (long) solicitudes.size(),
                recibidas,
                enProceso,
                finalizadas,
                canceladas
        );
    }

    public List<SolicitudesPorAreaDTO> obtenerSolicitudesPorArea() {
        return solicitudRepository.contarSolicitudesPorArea();
    }
}
