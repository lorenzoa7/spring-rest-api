package com.cms.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.springrestapi.model.Edicao;

public interface EdicaoRepository extends JpaRepository<Edicao, Long> {
    
}
