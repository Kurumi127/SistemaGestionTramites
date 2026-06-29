package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.AreaServicioRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.repository.AreaServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServicioService {

    private final AreaServicioRepository areaRepository;

    public AreaServicioService(AreaServicioRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaServicio> listarAreas() {
        return areaRepository.findAll();
    }

    public AreaServicio obtenerPorId(Integer id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));
    }

    public AreaServicio crearArea(AreaServicioRequestDTO dto) {
        if (areaRepository.existsByNombre(dto.getNombre())) {
            throw new BadRequestException("Ya existe un área con ese nombre");
        }

        AreaServicio area = new AreaServicio();
        area.setNombre(dto.getNombre());
        area.setDescripcion(dto.getDescripcion());

        if (dto.getEstado() == null || dto.getEstado().isBlank()) {
            area.setEstado(EstadoGeneral.ACTIVO);
        } else {
            area.setEstado(EstadoGeneral.valueOf(dto.getEstado()));
        }

        return areaRepository.save(area);
    }

    public AreaServicio editarArea(Integer id, AreaServicioRequestDTO dto) {
        AreaServicio area = obtenerPorId(id);

        area.setNombre(dto.getNombre());
        area.setDescripcion(dto.getDescripcion());
        area.setEstado(EstadoGeneral.valueOf(dto.getEstado()));

        return areaRepository.save(area);
    }

    public AreaServicio cambiarEstado(Integer id, String estado) {
        AreaServicio area = obtenerPorId(id);
        area.setEstado(EstadoGeneral.valueOf(estado));
        return areaRepository.save(area);
    }

    public AreaServicio obtenerArea(Integer id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));
    }

    public void eliminarArea(Integer id) {
        AreaServicio area = obtenerPorId(id);
        areaRepository.delete(area);
    }
}