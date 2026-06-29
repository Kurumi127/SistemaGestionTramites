package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.DashboardAreaDTO;
import com.Sistema.GestionTramites.dto.DashboardGeneralDTO;
import com.Sistema.GestionTramites.dto.DashboardOperadorDTO;
import com.Sistema.GestionTramites.dto.SolicitudesPorAreaDTO;
import com.Sistema.GestionTramites.service.ReporteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/dashboard-general")
    public DashboardGeneralDTO obtenerDashboardGeneral() {
        return reporteService.obtenerDashboardGeneral();
    }

    @GetMapping("/dashboard-area/{idArea}")
    public DashboardAreaDTO obtenerDashboardArea(@PathVariable Integer idArea) {
        return reporteService.obtenerDashboardArea(idArea);
    }

    @GetMapping("/solicitudes-por-area")
    public List<SolicitudesPorAreaDTO> obtenerSolicitudesPorArea() {
        return reporteService.obtenerSolicitudesPorArea();
    }

    @GetMapping("/dashboard-operador/{idUsuario}")
    public DashboardOperadorDTO obtenerDashboardOperador(@PathVariable Integer idUsuario) {
        return reporteService.obtenerDashboardOperador(idUsuario);
    }
}