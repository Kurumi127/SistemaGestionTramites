package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.OperadorAreaRequestDTO;
import com.Sistema.GestionTramites.model.OperadorArea;
import com.Sistema.GestionTramites.service.OperadorAreaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin(origins = "*")
public class OperadorAreaController {

    private final OperadorAreaService operadorAreaService;

    public OperadorAreaController(OperadorAreaService operadorAreaService) {
        this.operadorAreaService = operadorAreaService;
    }

    @GetMapping
    public List<OperadorArea> listarAsignaciones() {
        return operadorAreaService.listarAsignaciones();
    }

    @GetMapping("/{id}")
    public OperadorArea obtenerAsignacion(@PathVariable Integer id) {
        return operadorAreaService.obtenerPorId(id);
    }

    @GetMapping("/operador/{idUsuario}")
    public List<OperadorArea> listarPorOperador(@PathVariable Integer idUsuario) {
        return operadorAreaService.listarPorOperador(idUsuario);
    }

    @GetMapping("/area/{idArea}")
    public List<OperadorArea> listarPorArea(@PathVariable Integer idArea) {
        return operadorAreaService.listarPorArea(idArea);
    }

    @PostMapping
    public OperadorArea crearAsignacion(@RequestBody OperadorAreaRequestDTO dto) {
        return operadorAreaService.crearAsignacion(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarAsignacion(@PathVariable Integer id) {
        operadorAreaService.eliminarAsignacion(id);
    }

    @DeleteMapping("/operador/{idUsuario}/area/{idArea}")
    public void eliminarAsignacionPorOperadorYArea(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idArea
    ) {
        operadorAreaService.eliminarAsignacionPorOperadorYArea(idUsuario, idArea);
    }
}