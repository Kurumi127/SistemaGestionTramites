package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.ServicioRequestDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.model.Servicio;
import com.Sistema.GestionTramites.repository.AreaServicioRepository;
import com.Sistema.GestionTramites.repository.ServicioRepository;
import org.springframework.web.bind.annotation.*;
import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.repository.OperadorAreaRepository;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import com.Sistema.GestionTramites.dto.ServicioResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioRepository servicioRepository;
    private final AreaServicioRepository areaServicioRepository;
    private final OperadorAreaRepository operadorAreaRepository;
    private final UsuarioSistemaRepository usuarioSistemaRepository;

    public ServicioController(
            ServicioRepository servicioRepository,
            AreaServicioRepository areaServicioRepository,
            OperadorAreaRepository operadorAreaRepository,
            UsuarioSistemaRepository usuarioSistemaRepository
    ) {
        this.servicioRepository = servicioRepository;
        this.areaServicioRepository = areaServicioRepository;
        this.operadorAreaRepository = operadorAreaRepository;
        this.usuarioSistemaRepository = usuarioSistemaRepository;
    }

    @GetMapping
    public List<ServicioResponseDTO> listarServicios() {
        return servicioRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ServicioResponseDTO obtenerServicio(@PathVariable Integer id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        return convertirAResponseDTO(servicio);
    }

    @GetMapping("/area/{idArea}")
    public List<ServicioResponseDTO> listarServiciosPorArea(@PathVariable Integer idArea) {
        return servicioRepository.findByAreaIdArea(idArea)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @PostMapping
    public ServicioResponseDTO crearServicio(@RequestBody ServicioRequestDTO dto) {
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

        Servicio servicioGuardado = servicioRepository.save(servicio);
        return convertirAResponseDTO(servicioGuardado);
    }

    @PostMapping("/operador/{idUsuario}")
    public ServicioResponseDTO crearServicioComoOperador(
            @PathVariable Integer idUsuario,
            @RequestBody ServicioRequestDTO dto
    ) {
        validarOperadorAutorizadoEnArea(idUsuario, dto.getIdArea());

        return crearServicio(dto);
    }

    @PutMapping("/{id}")
    public ServicioResponseDTO editarServicio(
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

        Servicio servicioActualizado = servicioRepository.save(servicio);
        return convertirAResponseDTO(servicioActualizado);
    }

    @PutMapping("/operador/{idUsuario}/{idServicio}")
    public ServicioResponseDTO editarServicioComoOperador(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idServicio,
            @RequestBody ServicioRequestDTO dto
    ) {
        Servicio servicioActual = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        validarOperadorAutorizadoEnArea(idUsuario, servicioActual.getArea().getIdArea());
        validarOperadorAutorizadoEnArea(idUsuario, dto.getIdArea());

        return editarServicio(idServicio, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarServicio(@PathVariable Integer id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        servicioRepository.delete(servicio);
    }

    private void validarOperadorAutorizadoEnArea(Integer idUsuario, Integer idArea) {
        UsuarioSistema usuario = usuarioSistemaRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (usuario.getTipoUsuario() != TipoUsuario.OPERADOR) {
            throw new BadRequestException("El usuario no es operador");
        }

        boolean operadorPerteneceAlArea = operadorAreaRepository
                .existsByUsuarioIdUsuarioAndAreaIdArea(idUsuario, idArea);

        if (!operadorPerteneceAlArea) {
            throw new BadRequestException("El operador no puede crear o editar servicios de un área donde no está autorizado");
        }
    }

    private ServicioResponseDTO convertirAResponseDTO(Servicio servicio) {
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