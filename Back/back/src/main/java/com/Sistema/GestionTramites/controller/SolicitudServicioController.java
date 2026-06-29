package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.ActualizarEstadoSolicitudDTO;
import com.Sistema.GestionTramites.dto.SolicitudRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoSolicitud;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.SolicitudServicio;
import com.Sistema.GestionTramites.repository.SolicitudServicioRepository;
import com.Sistema.GestionTramites.service.SolicitudServicioService;
import org.springframework.web.bind.annotation.*;
import com.Sistema.GestionTramites.dto.SolicitudResponseDTO;
import com.Sistema.GestionTramites.model.SolicitudServicio;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudServicioController {

    private final SolicitudServicioRepository solicitudRepository;
    private final SolicitudServicioService solicitudServicioService;

    public SolicitudServicioController(
            SolicitudServicioRepository solicitudRepository,
            SolicitudServicioService solicitudServicioService
    ) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudServicioService = solicitudServicioService;
    }

    @GetMapping
    public List<SolicitudResponseDTO> listarSolicitudes() {
        return solicitudServicioService.listarSolicitudes()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public SolicitudResponseDTO obtenerSolicitud(@PathVariable Integer id) {
        SolicitudServicio solicitud = solicitudServicioService.obtenerSolicitud(id);
        return convertirAResponseDTO(solicitud);
    }

    @GetMapping("/area/{idArea}")
    public List<SolicitudResponseDTO> listarSolicitudesPorArea(@PathVariable Integer idArea) {
        return solicitudServicioService.listarSolicitudesPorArea(idArea)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @PostMapping
    public SolicitudResponseDTO crearSolicitud(@RequestBody SolicitudRequestDTO dto) {
        SolicitudServicio solicitud = solicitudServicioService.crearSolicitud(dto);
        return convertirAResponseDTO(solicitud);
    }

    @PutMapping("/{id}/estado")
    public SolicitudResponseDTO actualizarEstado(
            @PathVariable Integer id,
            @RequestBody ActualizarEstadoSolicitudDTO dto
    ) {
        SolicitudServicio solicitud = solicitudServicioService.actualizarEstado(id, dto);
        return convertirAResponseDTO(solicitud);
    }

    @PutMapping("/{id}/cancelar")
    public SolicitudServicio cancelarSolicitud(@PathVariable Integer id) {
        SolicitudServicio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.CANCELADA);
        solicitud.setFechaActualizacion(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }

    private SolicitudResponseDTO convertirAResponseDTO(SolicitudServicio solicitud) {
        return new SolicitudResponseDTO(
                solicitud.getIdSolicitud(),
                solicitud.getFolio(),
                solicitud.getFechaRecepcion(),
                solicitud.getMotivoSolicitud(),
                solicitud.getEstado().name(),

                solicitud.getSolicitante().getIdSolicitante(),
                solicitud.getSolicitante().getNombreCompleto(),
                solicitud.getSolicitante().getCorreo(),
                solicitud.getSolicitante().getTipoSolicitante(),

                solicitud.getServicio().getIdServicio(),
                solicitud.getServicio().getNumeroServicio(),
                solicitud.getServicio().getNombre(),

                solicitud.getServicio().getArea().getIdArea(),
                solicitud.getServicio().getArea().getNombre(),

                solicitud.getUsuarioRegistra().getIdUsuario(),
                solicitud.getUsuarioRegistra().getNombre(),

                solicitud.getFechaCreacion(),
                solicitud.getFechaActualizacion()
        );
    }
}