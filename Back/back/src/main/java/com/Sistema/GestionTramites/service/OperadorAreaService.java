package com.Sistema.GestionTramites.service;
import com.Sistema.GestionTramites.dto.OperadorAreaRequestDTO;
import com.Sistema.GestionTramites.enums.TipoUsuario;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.AreaServicio;
import com.Sistema.GestionTramites.model.OperadorArea;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.repository.AreaServicioRepository;
import com.Sistema.GestionTramites.repository.OperadorAreaRepository;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorAreaService {

    private final OperadorAreaRepository operadorAreaRepository;
    private final UsuarioSistemaRepository usuarioRepository;
    private final AreaServicioRepository areaRepository;

    public OperadorAreaService(
            OperadorAreaRepository operadorAreaRepository,
            UsuarioSistemaRepository usuarioRepository,
            AreaServicioRepository areaRepository
    ) {
        this.operadorAreaRepository = operadorAreaRepository;
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
    }

    public List<OperadorArea> listarAsignaciones() {
        return operadorAreaRepository.findAll();
    }

    public List<OperadorArea> listarPorOperador(Integer idUsuario) {
        return operadorAreaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<OperadorArea> listarPorArea(Integer idArea) {
        return operadorAreaRepository.findByAreaIdArea(idArea);
    }

    public OperadorArea obtenerPorId(Integer id) {
        return operadorAreaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));
    }

    public OperadorArea crearAsignacion(OperadorAreaRequestDTO dto) {
        UsuarioSistema usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        AreaServicio area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada"));

        if (usuario.getTipoUsuario() != TipoUsuario.OPERADOR) {
            throw new BadRequestException("Solo se pueden asignar áreas a usuarios de tipo OPERADOR");
        }

        boolean yaExiste = operadorAreaRepository.existsByUsuarioIdUsuarioAndAreaIdArea(
                dto.getIdUsuario(),
                dto.getIdArea()
        );

        if (yaExiste) {
            throw new BadRequestException("El operador ya tiene asignada esta área");
        }

        OperadorArea asignacion = new OperadorArea();
        asignacion.setUsuario(usuario);
        asignacion.setArea(area);

        return operadorAreaRepository.save(asignacion);
    }

    public void eliminarAsignacion(Integer id) {
        OperadorArea asignacion = obtenerPorId(id);
        operadorAreaRepository.delete(asignacion);
    }

    public void eliminarAsignacionPorOperadorYArea(Integer idUsuario, Integer idArea) {
        OperadorArea asignacion = operadorAreaRepository
                .findByUsuarioIdUsuarioAndAreaIdArea(idUsuario, idArea)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada"));

        operadorAreaRepository.delete(asignacion);
    }
}