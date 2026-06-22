package com.Sistema.GestionTramites.service;

import com.Sistema.GestionTramites.dto.auth.LoginRequestDTO;
import com.Sistema.GestionTramites.dto.auth.LoginResponseDTO;
import com.Sistema.GestionTramites.enums.EstadoGeneral;
import com.Sistema.GestionTramites.exeption.BadRequestException;
import com.Sistema.GestionTramites.exeption.ResourceNotFoundException;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioSistemaRepository usuarioRepository;

    public AuthService(UsuarioSistemaRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        UsuarioSistema usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new ResourceNotFoundException("Correo o contraseña incorrectos"));

        if (!usuario.getContrasena().equals(dto.getContrasena())) {
            throw new BadRequestException("Correo o contraseña incorrectos");
        }

        if (usuario.getEstado() != EstadoGeneral.ACTIVO) {
            throw new BadRequestException("El usuario está inactivo");
        }

        return new LoginResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTipoUsuario().name(),
                usuario.getEstado().name()
        );
    }
}