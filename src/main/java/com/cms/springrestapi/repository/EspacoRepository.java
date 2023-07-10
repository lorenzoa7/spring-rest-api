package com.cms.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.springrestapi.model.Espaco;

public interface EspacoRepository extends JpaRepository<Espaco, Long> {
    
}
