package com.cms.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.springrestapi.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    
}
