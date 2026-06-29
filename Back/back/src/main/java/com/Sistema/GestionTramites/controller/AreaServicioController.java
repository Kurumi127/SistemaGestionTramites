package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.AreaResponseDTO;
import com.Sistema.GestionTramites.dto.AreaServicioRequestDTO;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.service.AreaServicioService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaServicioController {

    private final AreaServicioService areaService;

    public AreaServicioController(AreaServicioService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public List<AreaResponseDTO> listarAreas() {
        return areaService.listarAreas()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public AreaResponseDTO obtenerArea(@PathVariable Integer id) {
        AreaServicio area = areaService.obtenerArea(id);
        return convertirAResponseDTO(area);
    }

    @PostMapping
    public AreaResponseDTO crearArea(@RequestBody AreaServicioRequestDTO dto) {
        AreaServicio area = areaService.crearArea(dto);
        return convertirAResponseDTO(area);
    }

    @PutMapping("/{id}")
    public AreaResponseDTO editarArea(
            @PathVariable Integer id,
            @RequestBody AreaServicioRequestDTO dto
    ) {
        AreaServicio area = areaService.editarArea(id, dto);
        return convertirAResponseDTO(area);
    }

    @PutMapping("/{id}/estado")
    public AreaResponseDTO cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String estado
    ) {
        AreaServicio area = areaService.cambiarEstado(id, estado);
        return convertirAResponseDTO(area);
    }

    @DeleteMapping("/{id}")
    public void eliminarArea(@PathVariable Integer id) {
        areaService.eliminarArea(id);
    }

    private AreaResponseDTO convertirAResponseDTO(AreaServicio area) {
        return new AreaResponseDTO(
                area.getIdArea(),
                area.getNombre(),
                area.getDescripcion(),
                area.getEstado().name()
        );
    }
}