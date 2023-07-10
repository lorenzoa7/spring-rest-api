package com.cms.springrestapi.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.springrestapi.model.Evento;
import com.cms.springrestapi.repository.EventoRepository;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento obterEvento(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento criarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public boolean excluirEvento(Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

