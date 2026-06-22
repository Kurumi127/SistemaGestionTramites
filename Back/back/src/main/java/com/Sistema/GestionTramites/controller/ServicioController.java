package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.ServicioRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.model.Servicio;
import com.Sistema.GestionTramites.repository.AreaServicioRepository;
import com.Sistema.GestionTramites.repository.ServicioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioRepository servicioRepository;
    private final AreaServicioRepository areaServicioRepository;

    public ServicioController(
            ServicioRepository servicioRepository,
            AreaServicioRepository areaServicioRepository
    ) {
        this.servicioRepository = servicioRepository;
        this.areaServicioRepository = areaServicioRepository;
    }

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Servicio obtenerServicio(@PathVariable Integer id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));
    }

    @GetMapping("/area/{idArea}")
    public List<Servicio> listarServiciosPorArea(@PathVariable Integer idArea) {
        return servicioRepository.findByAreaIdArea(idArea);
    }

    @PostMapping
    public Servicio crearServicio(@RequestBody ServicioRequestDTO dto) {
        AreaServicio area = areaServicioRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));

        Servicio servicio = new Servicio();
        servicio.setNumeroServicio(dto.getNumeroServicio());
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setTiempoInternoDias(dto.getTiempoInternoDias());
        servicio.setTiempoExternoDias(dto.getTiempoExternoDias());
        servicio.setTiempoTotalDias(dto.getTiempoTotalDias());
        servicio.setArea(area);

        // Si no mandas estado desde frontend, se guarda ACTIVO por defecto
        if (dto.getEstado() == null || dto.getEstado().isBlank()) {
            servicio.setEstado(EstadoGeneral.ACTIVO);
        } else {
            servicio.setEstado(EstadoGeneral.valueOf(dto.getEstado()));
        }

        return servicioRepository.save(servicio);
    }

    @PutMapping("/{id}")
    public Servicio editarServicio(
            @PathVariable Integer id,
            @RequestBody ServicioRequestDTO dto
    ) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        AreaServicio area = areaServicioRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));

        servicio.setNumeroServicio(dto.getNumeroServicio());
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setTiempoInternoDias(dto.getTiempoInternoDias());
        servicio.setTiempoExternoDias(dto.getTiempoExternoDias());
        servicio.setTiempoTotalDias(dto.getTiempoTotalDias());
        servicio.setArea(area);
        servicio.setEstado(EstadoGeneral.valueOf(dto.getEstado()));

        return servicioRepository.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void eliminarServicio(@PathVariable Integer id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        servicioRepository.delete(servicio);
    }
}