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

import com.cms.springrestapi.model.Espaco;
import com.cms.springrestapi.service.EspacoService;

@RestController
@RequestMapping("/espacos")
public class EspacoController {

    private final EspacoService espacoService;

    public EspacoController(EspacoService espacoService) {
        this.espacoService = espacoService;
    }

    // Obter todos os espaços
    @GetMapping
    public ResponseEntity<List<Espaco>> obterTodosEspacos() {
        List<Espaco> espacos = espacoService.obterTodosEspacos();
        return ResponseEntity.ok(espacos);
    }

    // Obter detalhes de um espaço específico
    @GetMapping("/{id}")
    public ResponseEntity<Espaco> obterEspaco(@PathVariable("id") Long id) {
        Espaco espaco = espacoService.obterEspaco(id);
        if (espaco != null) {
            return ResponseEntity.ok(espaco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar um novo espaço
    @PostMapping
    public ResponseEntity<Espaco> criarEspaco(@RequestBody Espaco espaco) {
        Espaco novoEspaco = espacoService.criarEspaco(espaco);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEspaco);
    }


    // Atualizar todos os detalhes de um espaço específico
    @PutMapping("/{id}")
    public ResponseEntity<Espaco> atualizarEspaco(
            @PathVariable("id") Long id,
            @RequestBody Espaco espacoAtualizado
    ) {
        Espaco espaco = espacoService.obterEspaco(id);
        if (espaco != null) {
            espaco.setNome(espacoAtualizado.getNome());
            espaco.setLocalizacao(espacoAtualizado.getLocalizacao());
            espaco.setCapacidade(espacoAtualizado.getCapacidade());
            espaco.setRecursos(espacoAtualizado.getRecursos());
            Espaco espacoAtualizadoEntity = espacoService.atualizarEspaco(espaco);
            return ResponseEntity.ok(espacoAtualizadoEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar detalhes parciais de um espaço específico
    @PatchMapping("/{id}")
    public ResponseEntity<Espaco> atualizarEspaco(
        @PathVariable("id") Long id,
        @RequestBody Map<String, Object> updates
    ) {
        Espaco espaco = espacoService.obterEspaco(id);
        if (espaco != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            modelMapper.map(updates, espaco);

            Espaco espacoAtualizado = espacoService.atualizarEspaco(espaco);
            return ResponseEntity.ok(espacoAtualizado);
        } else {
            return ResponseEntity.  notFound().build();
        }
    }

    // Excluir um espaço específico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEspaco(@PathVariable("id") Long id) {
        Espaco espaco = espacoService.obterEspaco(id);
        if (espaco != null) {
            espacoService.excluirEspaco(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
