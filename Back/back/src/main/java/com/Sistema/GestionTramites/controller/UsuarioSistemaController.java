package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.UsuarioResponseDTO;
import com.Sistema.GestionTramites.dto.UsuarioSistemaRequestDTO;
import com.Sistema.GestionTramites.model.UsuarioSistema;
import com.Sistema.GestionTramites.service.UsuarioSistemaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioSistemaController {

    private final UsuarioSistemaService usuarioService;

    public UsuarioSistemaController(UsuarioSistemaService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/operadores")
    public List<UsuarioResponseDTO> listarOperadores() {
        return usuarioService.listarOperadores()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO obtenerUsuario(@PathVariable Integer id) {
        UsuarioSistema usuario = usuarioService.obtenerPorId(id);
        return convertirAResponseDTO(usuario);
    }

    @PostMapping
    public UsuarioResponseDTO crearUsuario(@RequestBody UsuarioSistemaRequestDTO dto) {
        UsuarioSistema usuario = usuarioService.crearUsuario(dto);
        return convertirAResponseDTO(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO editarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioSistemaRequestDTO dto
    ) {
        UsuarioSistema usuario = usuarioService.editarUsuario(id, dto);
        return convertirAResponseDTO(usuario);
    }

    @PutMapping("/{id}/estado")
    public UsuarioResponseDTO cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String estado
    ) {
        UsuarioSistema usuario = usuarioService.cambiarEstado(id, estado);
        return convertirAResponseDTO(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }

    private UsuarioResponseDTO convertirAResponseDTO(UsuarioSistema usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTipoUsuario().name(),
                usuario.getEstado().name()
        );
    }
}