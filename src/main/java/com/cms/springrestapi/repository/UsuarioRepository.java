package com.cms.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.springrestapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
