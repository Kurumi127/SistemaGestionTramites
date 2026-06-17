package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.ActualizarEstadoSolicitudDTO;
import com.Sistema.GestionTramites.dto.SolicitudRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoSolicitud;
import com.Sistema.GestionTramites.model.SolicitudServicio;
import com.Sistema.GestionTramites.repository.SolicitudServicioRepository;
import com.Sistema.GestionTramites.service.SolicitudServicioService;
import org.springframework.web.bind.annotation.*;

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
    public List<SolicitudServicio> listarSolicitudes() {
        return solicitudRepository.findAll();
    }

    @GetMapping("/{id}")
    public SolicitudServicio obtenerSolicitud(@PathVariable Integer id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    @GetMapping("/area/{idArea}")
    public List<SolicitudServicio> listarSolicitudesPorArea(@PathVariable Integer idArea) {
        return solicitudRepository.findByServicioAreaIdArea(idArea);
    }

    @PostMapping
    public SolicitudServicio crearSolicitud(@RequestBody SolicitudRequestDTO dto) {
        return solicitudServicioService.crearSolicitud(dto);
    }

    @PutMapping("/{id}/estado")
    public SolicitudServicio actualizarEstado(
            @PathVariable Integer id,
            @RequestBody ActualizarEstadoSolicitudDTO dto
    ) {
        return solicitudServicioService.actualizarEstado(id, dto);
    }

    @PutMapping("/{id}/cancelar")
    public SolicitudServicio cancelarSolicitud(@PathVariable Integer id) {
        SolicitudServicio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.CANCELADA);
        solicitud.setFechaActualizacion(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }
}