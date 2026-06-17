package com.Sistema.GestionTramites.controller;
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
    public List<AreaServicio> listarAreas() {
        return areaService.listarAreas();
    }

    @GetMapping("/{id}")
    public AreaServicio obtenerArea(@PathVariable Integer id) {
        return areaService.obtenerPorId(id);
    }

    @PostMapping
    public AreaServicio crearArea(@RequestBody AreaServicioRequestDTO dto) {
        return areaService.crearArea(dto);
    }

    @PutMapping("/{id}")
    public AreaServicio editarArea(
            @PathVariable Integer id,
            @RequestBody AreaServicioRequestDTO dto
    ) {
        return areaService.editarArea(id, dto);
    }

    @PutMapping("/{id}/estado")
    public AreaServicio cambiarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body
    ) {
        return areaService.cambiarEstado(id, body.get("estado"));
    }

    @DeleteMapping("/{id}")
    public void eliminarArea(@PathVariable Integer id) {
        areaService.eliminarArea(id);
    }
}