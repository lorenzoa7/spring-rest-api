package com.cms.springrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.springrestapi.model.Atividade;
import com.cms.springrestapi.model.Usuario;
import com.cms.springrestapi.repository.AtividadeRepository;
import com.cms.springrestapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, AtividadeRepository atividadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obterUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario criarUsuario(Usuario usuario) {
        List<Atividade> favoritos = usuario.getFavoritos();
        if (favoritos != null) {
            for (int i = 0; i < favoritos.size(); i++) {
                Atividade favorito = favoritos.get(i);
                Long favoritoId = favorito.getId();
                Atividade atividadeExistente = atividadeRepository.findById(favoritoId).orElse(null);
                if (atividadeExistente != null) {
                    favoritos.set(i, atividadeExistente);
                }
            }
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean excluirUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

