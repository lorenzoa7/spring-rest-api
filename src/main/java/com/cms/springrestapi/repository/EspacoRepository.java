package com.cms.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.springrestapi.model.Espaco;

@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long> {
    
}
