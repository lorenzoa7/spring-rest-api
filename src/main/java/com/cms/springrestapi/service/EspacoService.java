package com.cms.springrestapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.springrestapi.model.Espaco;
import com.cms.springrestapi.repository.EspacoRepository;

@Service
public class EspacoService {

    private final EspacoRepository espacoRepository;

    public EspacoService(EspacoRepository espacoRepository) {
        this.espacoRepository = espacoRepository;
    }

    public Espaco criarEspaco(Espaco espaco) {
        return espacoRepository.save(espaco);
    }

    public Espaco obterEspaco(Long id) {
        return espacoRepository.findById(id).orElse(null);
    }

    public Espaco atualizarEspaco(Espaco espaco) {
        return espacoRepository.save(espaco);
    }

    public void excluirEspaco(Long id) {
        espacoRepository.deleteById(id);
    }

    public List<Espaco> obterTodosEspacos() {
        return espacoRepository.findAll();
    }
}
