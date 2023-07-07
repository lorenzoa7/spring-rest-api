package com.cms.springrestapi.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Edicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;
    private int ano;
    private Date dataInicial;
    private Date dataFinal;
    private String cidade;

    @OneToOne
    private Evento evento;

    @OneToOne
    private Usuario organizador;

    @OneToMany
    private List<Atividade> atividades;
}
