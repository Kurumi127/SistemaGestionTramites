package com.Sistema.GestionTramites.controller;
import com.Sistema.GestionTramites.dto.AreaResponseDTO;
import com.Sistema.GestionTramites.dto.ServicioResponseDTO;
import com.Sistema.GestionTramites.dto.SolicitudResponseDTO;
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
    public List<AreaResponseDTO> obtenerAreasDelOperador(@PathVariable Integer idUsuario) {
        return operadorConsultaService.obtenerAreasDelOperador(idUsuario)
                .stream()
                .map(this::convertirAreaAResponseDTO)
                .toList();
    }

    @GetMapping("/{idUsuario}/servicios")
    public List<ServicioResponseDTO> obtenerServiciosDelOperador(@PathVariable Integer idUsuario) {
        return operadorConsultaService.obtenerServiciosDelOperador(idUsuario)
                .stream()
                .map(this::convertirServicioAResponseDTO)
                .toList();
    }

    @GetMapping("/{idUsuario}/solicitudes")
    public List<SolicitudResponseDTO> obtenerSolicitudesDelOperador(@PathVariable Integer idUsuario) {
        return operadorConsultaService.obtenerSolicitudesDelOperador(idUsuario)
                .stream()
                .map(this::convertirSolicitudAResponseDTO)
                .toList();
    }

    private SolicitudResponseDTO convertirSolicitudAResponseDTO(SolicitudServicio solicitud) {
        return new SolicitudResponseDTO(
                solicitud.getIdSolicitud(),
                solicitud.getFolio(),
                solicitud.getFechaRecepcion(),
                solicitud.getMotivoSolicitud(),
                solicitud.getEstado().name(),

                solicitud.getSolicitante().getIdSolicitante(),
                solicitud.getSolicitante().getNombreCompleto(),
                solicitud.getSolicitante().getCorreo(),
                solicitud.getSolicitante().getTipoSolicitante(),

                solicitud.getServicio().getIdServicio(),
                solicitud.getServicio().getNumeroServicio(),
                solicitud.getServicio().getNombre(),

                solicitud.getServicio().getArea().getIdArea(),
                solicitud.getServicio().getArea().getNombre(),

                solicitud.getUsuarioRegistra().getIdUsuario(),
                solicitud.getUsuarioRegistra().getNombre(),

                solicitud.getFechaCreacion(),
                solicitud.getFechaActualizacion()
        );
    }

    private AreaResponseDTO convertirAreaAResponseDTO(AreaServicio area) {
        return new AreaResponseDTO(
                area.getIdArea(),
                area.getNombre(),
                area.getDescripcion(),
                area.getEstado().name()
        );
    }

    private ServicioResponseDTO convertirServicioAResponseDTO(Servicio servicio) {
        return new ServicioResponseDTO(
                servicio.getIdServicio(),
                servicio.getNumeroServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getTiempoInternoDias(),
                servicio.getTiempoExternoDias(),
                servicio.getTiempoTotalDias(),
                servicio.getArea().getIdArea(),
                servicio.getArea().getNombre(),
                servicio.getEstado().name()
        );
    }
}
