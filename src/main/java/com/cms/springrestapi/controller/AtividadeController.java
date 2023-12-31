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

import com.cms.springrestapi.model.Atividade;
import com.cms.springrestapi.service.AtividadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/atividades")
@Tag(name = "atividade-api")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    // Obter todas as atividades
    @Operation(summary = "Busca dados de todas as atividades", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividades() {
        List<Atividade> atividades = atividadeService.listarAtividades();
        return ResponseEntity.ok(atividades);
    }

    // Obter detalhes de uma atividade específica
    @Operation(summary = "Busca dados de um atividade específica", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Atividade> obterAtividade(@PathVariable("id") Long id) {
        Atividade atividade = atividadeService.obterAtividade(id);
        if (atividade != null) {
            return ResponseEntity.ok(atividade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar uma nova atividade
    @Operation(summary = "Cria uma nova atividade", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uma nova atividade foi criada"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar atividade")
    })
    @PostMapping
    public ResponseEntity<Atividade> criarAtividade(@RequestBody Atividade atividade) {
        Atividade atividadeCriada = atividadeService.criarAtividade(atividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeCriada);
    }

    // Atualizar detalhes de uma atividade específica
    @Operation(summary = "Atualiza detalhes de uma atividade específica", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar dados")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizarAtividade(@PathVariable("id") Long id,
            @RequestBody Atividade atividadeAtualizada) {
        Atividade atividade = atividadeService.obterAtividade(id);
        if (atividade != null) {
            atividade.setNome(atividadeAtualizada.getNome());
            atividade.setTipo(atividadeAtualizada.getTipo());
            atividade.setDescricao(atividadeAtualizada.getDescricao());
            atividade.setData(atividadeAtualizada.getData());
            atividade.setHorarioInicial(atividadeAtualizada.getHorarioInicial());
            atividade.setHorarioFinal(atividadeAtualizada.getHorarioFinal());
            atividade.setLocal(atividadeAtualizada.getLocal());
            Atividade atividadeAtualizadaEntity = atividadeService.atualizarAtividade(atividade);
            return ResponseEntity.ok(atividadeAtualizadaEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar detalhes parciais de uma atividade específica
    @Operation(summary = "Atualiza detalhes parciais de uma atividade específica", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar dados")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Atividade> atualizarCampoAtividade(@PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {
        Atividade atividade = atividadeService.obterAtividade(id);
        if (atividade != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            modelMapper.map(updates, atividade);

            Atividade atividadeAtualizada = atividadeService.atualizarAtividade(atividade);
            return ResponseEntity.ok(atividadeAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir uma atividade específica
    @Operation(summary = "Exclui uma atividade", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade excluída com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir atividade")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtividade(@PathVariable("id") Long id) {
        boolean removido = atividadeService.excluirAtividade(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
