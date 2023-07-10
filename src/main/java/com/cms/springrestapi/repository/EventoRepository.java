package com.cms.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.springrestapi.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    
}
