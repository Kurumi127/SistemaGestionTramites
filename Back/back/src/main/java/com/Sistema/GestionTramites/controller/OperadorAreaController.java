package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.OperadorAreaRequestDTO;
import com.Sistema.GestionTramites.dto.OperadorAreaResponseDTO;
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
    public List<OperadorAreaResponseDTO> listarAsignaciones() {
        return operadorAreaService.listarAsignaciones()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public OperadorAreaResponseDTO obtenerAsignacion(@PathVariable Integer id) {
        OperadorArea operadorArea = operadorAreaService.obtenerPorId(id);
        return convertirAResponseDTO(operadorArea);
    }

    @GetMapping("/operador/{idUsuario}")
    public List<OperadorAreaResponseDTO> listarPorOperador(@PathVariable Integer idUsuario) {
        return operadorAreaService.listarPorOperador(idUsuario)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/area/{idArea}")
    public List<OperadorAreaResponseDTO> listarPorArea(@PathVariable Integer idArea) {
        return operadorAreaService.listarPorArea(idArea)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @PostMapping
    public OperadorAreaResponseDTO crearAsignacion(@RequestBody OperadorAreaRequestDTO dto) {
        OperadorArea operadorArea = operadorAreaService.crearAsignacion(dto);
        return convertirAResponseDTO(operadorArea);
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

    private OperadorAreaResponseDTO convertirAResponseDTO(OperadorArea operadorArea) {
        return new OperadorAreaResponseDTO(
                operadorArea.getIdOperadorArea(),

                operadorArea.getUsuario().getIdUsuario(),
                operadorArea.getUsuario().getNombre(),
                operadorArea.getUsuario().getCorreo(),

                operadorArea.getArea().getIdArea(),
                operadorArea.getArea().getNombre()
        );
    }
}