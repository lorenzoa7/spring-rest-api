package com.cms.springrestapi.controller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.springrestapi.model.Edicao;
import com.cms.springrestapi.service.EdicaoService;

@RestController
@RequestMapping("/edicoes")
public class EdicaoController {

    private final EdicaoService edicaoService;

    public EdicaoController(EdicaoService edicaoService) {
        this.edicaoService = edicaoService;
    }

    @GetMapping
    public ResponseEntity<List<Edicao>> listarEdicoes() {
        List<Edicao> edicoes = edicaoService.listarEdicoes();
        return ResponseEntity.ok(edicoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Edicao> obterEdicao(@PathVariable("id") Long id) {
        Edicao edicao = edicaoService.obterEdicao(id);
        if (edicao != null) {
            return ResponseEntity.ok(edicao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Edicao> criarEdicao(@RequestBody Edicao edicao) {
        Edicao edicaoCriada = edicaoService.criarEdicao(edicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(edicaoCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Edicao> atualizarEdicao(@PathVariable("id") Long id, @RequestBody Edicao edicaoAtualizada) {
        Edicao edicao = edicaoService.obterEdicao(id);
        if (edicao != null) {
            edicao.setNumero(edicaoAtualizada.getNumero());
            edicao.setAno(edicaoAtualizada.getAno());
            edicao.setDataInicial(edicaoAtualizada.getDataInicial());
            edicao.setDataFinal(edicaoAtualizada.getDataFinal());
            edicao.setCidade(edicaoAtualizada.getCidade());
            edicao.setEvento(edicaoAtualizada.getEvento());
            edicao.setOrganizador(edicaoAtualizada.getOrganizador());
            edicao.setAtividades(edicaoAtualizada.getAtividades());
            Edicao edicaoAtualizadaEntity = edicaoService.atualizarEdicao(edicao);
            return ResponseEntity.ok(edicaoAtualizadaEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Edicao> atualizarCampoEdicao(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Edicao edicao = edicaoService.obterEdicao(id);
        if (edicao != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            modelMapper.map(updates, edicao);

            Edicao edicaoAtualizada = edicaoService.atualizarEdicao(edicao);
            return ResponseEntity.ok(edicaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEdicao(@PathVariable("id") Long id) {
        boolean removido = edicaoService.excluirEdicao(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
