package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.ActualizarEstadoSolicitudDTO;
import com.Sistema.GestionTramites.dto.SolicitudRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoSolicitud;
import com.Sistema.GestionTramites.model.*;
import com.Sistema.GestionTramites.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SolicitudServicioService {

    private final SolicitudServicioRepository solicitudRepository;
    private final SolicitanteRepository solicitanteRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioSistemaRepository usuarioRepository;
    private final HistorialSolicitudRepository historialRepository;

    public SolicitudServicioService(
            SolicitudServicioRepository solicitudRepository,
            SolicitanteRepository solicitanteRepository,
            ServicioRepository servicioRepository,
            UsuarioSistemaRepository usuarioRepository,
            HistorialSolicitudRepository historialRepository
    ) {
        this.solicitudRepository = solicitudRepository;
        this.solicitanteRepository = solicitanteRepository;
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.historialRepository = historialRepository;
    }

    public SolicitudServicio crearSolicitud(SolicitudRequestDTO dto) {
        Servicio servicio = servicioRepository.findById(dto.getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        UsuarioSistema usuario = usuarioRepository.findById(dto.getIdUsuarioRegistra())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Solicitante solicitante = new Solicitante();
        solicitante.setNombreCompleto(dto.getNombreCompleto());
        solicitante.setCorreo(dto.getCorreo());
        solicitante.setTipoSolicitante(dto.getTipoSolicitante());
        solicitante.setFechaRegistro(LocalDate.now());

        Solicitante solicitanteGuardado = solicitanteRepository.save(solicitante);

        SolicitudServicio solicitud = new SolicitudServicio();
        solicitud.setFolio(generarFolio(servicio));
        solicitud.setFechaRecepcion(
                dto.getFechaRecepcion() != null ? dto.getFechaRecepcion() : LocalDateTime.now()
        );
        solicitud.setMotivoSolicitud(dto.getMotivoSolicitud());
        solicitud.setEstado(EstadoSolicitud.RECIBIDA);
        solicitud.setSolicitante(solicitanteGuardado);
        solicitud.setServicio(servicio);
        solicitud.setUsuarioRegistra(usuario);
        solicitud.setFechaCreacion(LocalDateTime.now());
        solicitud.setFechaActualizacion(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }

    public SolicitudServicio actualizarEstado(Integer idSolicitud, ActualizarEstadoSolicitudDTO dto) {
        SolicitudServicio solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        EstadoSolicitud nuevoEstado = EstadoSolicitud.valueOf(dto.getEstado());

        solicitud.setEstado(nuevoEstado);
        solicitud.setFechaActualizacion(LocalDateTime.now());

        SolicitudServicio solicitudActualizada = solicitudRepository.save(solicitud);

        if (nuevoEstado == EstadoSolicitud.FINALIZADA) {
            guardarHistorialFinalizada(solicitudActualizada, dto.getObservacionFinal());
        }

        return solicitudActualizada;
    }

    private void guardarHistorialFinalizada(SolicitudServicio solicitud, String observacionFinal) {
        boolean yaExisteHistorial = historialRepository.existsBySolicitudIdSolicitud(solicitud.getIdSolicitud());

        if (yaExisteHistorial) {
            return;
        }

        HistorialSolicitud historial = new HistorialSolicitud();
        historial.setSolicitud(solicitud);
        historial.setFolio(solicitud.getFolio());
        historial.setFechaRecepcion(solicitud.getFechaRecepcion());
        historial.setNombreSolicitante(solicitud.getSolicitante().getNombreCompleto());
        historial.setTipoSolicitante(solicitud.getSolicitante().getTipoSolicitante());
        historial.setNombreServicio(solicitud.getServicio().getNombre());
        historial.setNombreArea(solicitud.getServicio().getArea().getNombre());
        historial.setNombreUsuarioRegistra(solicitud.getUsuarioRegistra().getNombre());
        historial.setFechaCreacionSolicitud(solicitud.getFechaCreacion());
        historial.setFechaFinalizacion(LocalDateTime.now());
        historial.setObservacionFinal(observacionFinal);

        historialRepository.save(historial);
    }

    private String generarFolio(Servicio servicio) {
        String prefijoArea = servicio.getArea().getNombre()
                .replace("Prestaciones y servicios", "PS")
                .replace("Movimientos de personal", "MP")
                .replace("Constancias", "CS");

        if (prefijoArea.length() > 3) {
            prefijoArea = "SOL";
        }

        long totalSolicitudes = solicitudRepository.count() + 1;
        int anio = LocalDate.now().getYear();

        return String.format("%s-%d-%04d", prefijoArea, anio, totalSolicitudes);
    }
}