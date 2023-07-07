package com.cms.springrestapi.model;

import java.sql.Time;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Atividade {
    public enum Tipo {
        palestra,
        painel,
        sessao_tecnica
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private String descricao;
    private Date data;
    private Time horarioInicial;
    private Time horarioFinal;

    @OneToOne
    private Espaco local;
}
