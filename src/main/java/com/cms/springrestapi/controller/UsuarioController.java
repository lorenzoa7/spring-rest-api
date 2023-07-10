package com.cms.springrestapi.controller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.springrestapi.model.Usuario;
import com.cms.springrestapi.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.obterUsuario(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.obterUsuario(id);
        if (usuario != null) {
            usuario.setLogin(usuarioAtualizado.getLogin());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setAfiliacao(usuarioAtualizado.getAfiliacao());
            Usuario usuarioAtualizadoEntity = usuarioService.atualizarUsuario(usuario);
            return ResponseEntity.ok(usuarioAtualizadoEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizarCampoUsuario(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Usuario usuario = usuarioService.obterUsuario(id);
        if (usuario != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            modelMapper.map(updates, usuario);

            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long id) {
        boolean removido = usuarioService.excluirUsuario(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

