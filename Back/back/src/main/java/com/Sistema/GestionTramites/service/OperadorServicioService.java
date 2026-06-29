package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.OperadorServicioResponseDTO;
import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.OperadorServicio;
import com.Sistema.GestionTramites.model.Servicio;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.repository.OperadorAreaRepository;
import com.Sistema.GestionTramites.repository.OperadorServicioRepository;
import com.Sistema.GestionTramites.repository.ServicioRepository;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorServicioService {

    private final OperadorServicioRepository operadorServicioRepository;
    private final UsuarioSistemaRepository usuarioSistemaRepository;
    private final ServicioRepository servicioRepository;
    private final OperadorAreaRepository operadorAreaRepository;

    public OperadorServicioService(
            OperadorServicioRepository operadorServicioRepository,
            UsuarioSistemaRepository usuarioSistemaRepository,
            ServicioRepository servicioRepository,
            OperadorAreaRepository operadorAreaRepository
    ) {
        this.operadorServicioRepository = operadorServicioRepository;
        this.usuarioSistemaRepository = usuarioSistemaRepository;
        this.servicioRepository = servicioRepository;
        this.operadorAreaRepository = operadorAreaRepository;
    }

    public List<OperadorServicioResponseDTO> listarServiciosPropios(Integer idUsuario) {
        UsuarioSistema usuario = obtenerOperador(idUsuario);

        return operadorServicioRepository.findByUsuarioIdUsuario(usuario.getIdUsuario())
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    public OperadorServicioResponseDTO marcarServicioComoPropio(Integer idUsuario, Integer idServicio) {
        UsuarioSistema usuario = obtenerOperador(idUsuario);

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        Integer idAreaServicio = servicio.getArea().getIdArea();

        boolean operadorPerteneceAlArea = operadorAreaRepository
                .existsByUsuarioIdUsuarioAndAreaIdArea(idUsuario, idAreaServicio);

        if (!operadorPerteneceAlArea) {
            throw new BadRequestException("El operador no puede seleccionar servicios de un área donde no está autorizado");
        }

        boolean yaExiste = operadorServicioRepository
                .existsByUsuarioIdUsuarioAndServicioIdServicio(idUsuario, idServicio);

        if (yaExiste) {
            throw new BadRequestException("El servicio ya está marcado como propio por este operador");
        }

        OperadorServicio operadorServicio = new OperadorServicio();
        operadorServicio.setUsuario(usuario);
        operadorServicio.setServicio(servicio);

        OperadorServicio guardado = operadorServicioRepository.save(operadorServicio);

        return convertirAResponseDTO(guardado);
    }

    public void quitarServicioPropio(Integer idUsuario, Integer idServicio) {
        OperadorServicio operadorServicio = operadorServicioRepository
                .findByUsuarioIdUsuarioAndServicioIdServicio(idUsuario, idServicio)
                .orElseThrow(() -> new ResourceNotFoundException("El servicio no está marcado como propio por este operador"));

        operadorServicioRepository.delete(operadorServicio);
    }

    private UsuarioSistema obtenerOperador(Integer idUsuario) {
        UsuarioSistema usuario = usuarioSistemaRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (usuario.getTipoUsuario() != TipoUsuario.OPERADOR) {
            throw new BadRequestException("El usuario no es operador");
        }

        return usuario;
    }

    private OperadorServicioResponseDTO convertirAResponseDTO(OperadorServicio operadorServicio) {
        return new OperadorServicioResponseDTO(
                operadorServicio.getIdOperadorServicio(),

                operadorServicio.getUsuario().getIdUsuario(),
                operadorServicio.getUsuario().getNombre(),

                operadorServicio.getServicio().getIdServicio(),
                operadorServicio.getServicio().getNombre(),
                operadorServicio.getServicio().getNumeroServicio(),

                operadorServicio.getServicio().getArea().getIdArea(),
                operadorServicio.getServicio().getArea().getNombre()
        );
    }
}