package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.DashboardAreaDTO;
import com.Sistema.GestionTramites.dto.DashboardGeneralDTO;
import com.Sistema.GestionTramites.dto.SolicitudesPorAreaDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.enums.EstadoSolicitud;
import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.repository.AreaServicioRepository;
import com.Sistema.GestionTramites.repository.ServicioRepository;
import com.Sistema.GestionTramites.repository.SolicitudServicioRepository;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final AreaServicioRepository areaRepository;
    private final ServicioRepository servicioRepository;
    private final SolicitudServicioRepository solicitudRepository;

    public ReporteService(
            UsuarioSistemaRepository usuarioRepository,
            AreaServicioRepository areaRepository,
            ServicioRepository servicioRepository,
            SolicitudServicioRepository solicitudRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
        this.servicioRepository = servicioRepository;
        this.solicitudRepository = solicitudRepository;
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

    public List<SolicitudesPorAreaDTO> obtenerSolicitudesPorArea() {
        return solicitudRepository.contarSolicitudesPorArea();
    }
}
