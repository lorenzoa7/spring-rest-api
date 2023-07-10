package com.cms.springrestapi.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cms.springrestapi.model.Atividade;
import com.cms.springrestapi.model.Espaco;
import com.cms.springrestapi.repository.AtividadeRepository;
import com.cms.springrestapi.repository.EspacoRepository;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final EspacoRepository espacoRepository;

    public AtividadeService(AtividadeRepository atividadeRepository, EspacoRepository espacoRepository) {
        this.atividadeRepository = atividadeRepository;
        this.espacoRepository = espacoRepository;
    }

    public List<Atividade> listarAtividades() {
        return atividadeRepository.findAll();
    }

    public Atividade obterAtividade(Long id) {
        return atividadeRepository.findById(id).orElse(null);
    }

    public Atividade criarAtividade(Atividade atividade) {
        Long localId = atividade.getLocal().getId();
        Espaco local = espacoRepository.findById(localId).orElse(null);
        if (local != null) {
            atividade.setLocal(local);
            return atividadeRepository.save(atividade);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not found with ID: " + localId);
        }
    }


    public Atividade atualizarAtividade(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public boolean excluirAtividade(Long id) {
        if (atividadeRepository.existsById(id)) {
            atividadeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

