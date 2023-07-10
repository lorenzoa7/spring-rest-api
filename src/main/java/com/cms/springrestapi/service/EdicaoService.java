package com.cms.springrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cms.springrestapi.model.Atividade;
import com.cms.springrestapi.model.Edicao;
import com.cms.springrestapi.model.Evento;
import com.cms.springrestapi.model.Usuario;
import com.cms.springrestapi.repository.AtividadeRepository;
import com.cms.springrestapi.repository.EdicaoRepository;
import com.cms.springrestapi.repository.EventoRepository;
import com.cms.springrestapi.repository.UsuarioRepository;

@Service
public class EdicaoService {

    private final EdicaoRepository edicaoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;

    public EdicaoService(EdicaoRepository edicaoRepository, EventoRepository eventoRepository,
                         UsuarioRepository usuarioRepository, AtividadeRepository atividadeRepository) {
        this.edicaoRepository = edicaoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
    }

    public List<Edicao> listarEdicoes() {
        return edicaoRepository.findAll();
    }

    public Edicao obterEdicao(Long id) {
        return edicaoRepository.findById(id).orElse(null);
    }

    public Edicao criarEdicao(Edicao edicao) {
        Long eventoId = edicao.getEvento().getId();
        Evento evento = eventoRepository.findById(eventoId).orElse(null);
        if (evento != null) {
            edicao.setEvento(evento);
            Usuario organizador = usuarioRepository.findById(edicao.getOrganizador().getId()).orElse(null);
            if (organizador != null) {
                edicao.setOrganizador(organizador);
            }
            List<Atividade> atividades = edicao.getAtividades();
            if (atividades != null) {
                for (int i = 0; i < atividades.size(); i++) {
                    Atividade atividade = atividades.get(i);
                    Long atividadeId = atividade.getId();
                    Atividade atividadeExistente = atividadeRepository.findById(atividadeId).orElse(null);
                    if (atividadeExistente != null) {
                        atividades.set(i, atividadeExistente);
                    }
                }
            }
            return edicaoRepository.save(edicao);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento not found with ID: " + eventoId);
        }
    }

    public Edicao atualizarEdicao(Edicao edicao) {
        return edicaoRepository.save(edicao);
    }

    public boolean excluirEdicao(Long id) {
        if (edicaoRepository.existsById(id)) {
            edicaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
