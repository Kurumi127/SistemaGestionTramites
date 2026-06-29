package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.OperadorServicioResponseDTO;
import com.Sistema.GestionTramites.service.OperadorServicioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operador-servicios")
@CrossOrigin(origins = "*")
public class OperadorServicioController {

    private final OperadorServicioService operadorServicioService;

    public OperadorServicioController(OperadorServicioService operadorServicioService) {
        this.operadorServicioService = operadorServicioService;
    }

    @GetMapping("/operador/{idUsuario}")
    public List<OperadorServicioResponseDTO> listarServiciosPropios(
            @PathVariable Integer idUsuario
    ) {
        return operadorServicioService.listarServiciosPropios(idUsuario);
    }

    @PostMapping("/operador/{idUsuario}/servicio/{idServicio}")
    public OperadorServicioResponseDTO marcarServicioComoPropio(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idServicio
    ) {
        return operadorServicioService.marcarServicioComoPropio(idUsuario, idServicio);
    }

    @DeleteMapping("/operador/{idUsuario}/servicio/{idServicio}")
    public void quitarServicioPropio(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idServicio
    ) {
        operadorServicioService.quitarServicioPropio(idUsuario, idServicio);
    }
}
