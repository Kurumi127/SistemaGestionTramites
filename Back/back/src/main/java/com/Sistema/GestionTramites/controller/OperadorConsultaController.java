package com.Sistema.GestionTramites.controller;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.model.Servicio;
import com.Sistema.GestionTramites.model.SolicitudServicio;
import com.Sistema.GestionTramites.service.OperadorConsultaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operador")
@CrossOrigin(origins = "*")
public class OperadorConsultaController {

    private final OperadorConsultaService operadorConsultaService;

    public OperadorConsultaController(OperadorConsultaService operadorConsultaService) {
        this.operadorConsultaService = operadorConsultaService;
    }

    @GetMapping("/{idUsuario}/areas")
    public List<AreaServicio> obtenerAreasDelOperador(@PathVariable Integer idUsuario) {
        return operadorConsultaService.obtenerAreasDelOperador(idUsuario);
    }

    @GetMapping("/{idUsuario}/servicios")
    public List<Servicio> obtenerServiciosDelOperador(@PathVariable Integer idUsuario) {
        return operadorConsultaService.obtenerServiciosDelOperador(idUsuario);
    }

    @GetMapping("/{idUsuario}/solicitudes")
    public List<SolicitudServicio> obtenerSolicitudesDelOperador(@PathVariable Integer idUsuario) {
        return operadorConsultaService.obtenerSolicitudesDelOperador(idUsuario);
    }
}
