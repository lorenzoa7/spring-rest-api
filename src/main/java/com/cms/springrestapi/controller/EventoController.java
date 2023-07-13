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

import com.cms.springrestapi.model.Evento;
import com.cms.springrestapi.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/eventos")
@Tag(name = "evento-api")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // Obter todos os eventos
    @Operation(summary = "Busca dados de todos os eventos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos() {
        List<Evento> eventos = eventoService.listarEventos();
        return ResponseEntity.ok(eventos);
    }

    // Obter detalhes de um evento específico
    @Operation(summary = "Busca dados de um evento específico", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Evento> obterEvento(@PathVariable("id") Long id) {
        Evento evento = eventoService.obterEvento(id);
        if (evento != null) {
            return ResponseEntity.ok(evento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar um novo evento
    @Operation(summary = "Cria um novo evento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uma nova evento foi criada"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar evento")
    })
    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody Evento evento) {
        Evento eventoCriado = eventoService.criarEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
    }

    // Atualizar todos os detalhes de um evento específico
    @Operation(summary = "Atualiza detalhes de um evento específico", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar dados")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizarEvento(@PathVariable("id") Long id, @RequestBody Evento eventoAtualizado) {
        Evento evento = eventoService.obterEvento(id);
        if (evento != null) {
            evento.setNome(eventoAtualizado.getNome());
            evento.setSigla(eventoAtualizado.getSigla());
            evento.setDescricao(eventoAtualizado.getDescricao());
            Evento eventoAtualizadoEntity = eventoService.atualizarEvento(evento);
            return ResponseEntity.ok(eventoAtualizadoEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar detalhes parciais de um evento específico
    @Operation(summary = "Atualiza detalhes parciais de um evento específico", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar dados")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Evento> atualizarCampoEvento(@PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {
        Evento evento = eventoService.obterEvento(id);
        if (evento != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            modelMapper.map(updates, evento);

            Evento eventoAtualizado = eventoService.atualizarEvento(evento);
            return ResponseEntity.ok(eventoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir um evento específico
    @Operation(summary = "Exclui um evento", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento excluída com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir evento")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEvento(@PathVariable("id") Long id) {
        boolean removido = eventoService.excluirEvento(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
