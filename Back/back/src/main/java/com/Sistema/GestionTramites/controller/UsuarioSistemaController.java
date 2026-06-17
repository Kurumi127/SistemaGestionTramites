package com.Sistema.GestionTramites.controller;

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
    public List<UsuarioSistema> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/operadores")
    public List<UsuarioSistema> listarOperadores() {
        return usuarioService.listarOperadores();
    }

    @GetMapping("/{id}")
    public UsuarioSistema obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    @PostMapping
    public UsuarioSistema crearUsuario(@RequestBody UsuarioSistemaRequestDTO dto) {
        return usuarioService.crearUsuario(dto);
    }

    @PutMapping("/{id}")
    public UsuarioSistema editarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioSistemaRequestDTO dto
    ) {
        return usuarioService.editarUsuario(id, dto);
    }

    @PutMapping("/{id}/estado")
    public UsuarioSistema cambiarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body
    ) {
        return usuarioService.cambiarEstado(id, body.get("estado"));
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }
}