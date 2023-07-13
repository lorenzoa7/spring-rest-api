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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/edicoes")
@Tag(name = "edicao-api")
public class EdicaoController {

    private final EdicaoService edicaoService;

    public EdicaoController(EdicaoService edicaoService) {
        this.edicaoService = edicaoService;
    }

    // Obter todas as edicoes
    @Operation(summary = "Busca dados de todas as edições", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping
    public ResponseEntity<List<Edicao>> listarEdicoes() {
        List<Edicao> edicoes = edicaoService.listarEdicoes();
        return ResponseEntity.ok(edicoes);
    }

    // Obter detalhes de uma edicao específica
    @Operation(summary = "Busca dados de uma edição específica", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Edicao> obterEdicao(@PathVariable("id") Long id) {
        Edicao edicao = edicaoService.obterEdicao(id);
        if (edicao != null) {
            return ResponseEntity.ok(edicao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar uma nova edicao
    @Operation(summary = "Cria uma nova edição", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uma nova edição foi criada"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar edição")
    })
    @PostMapping
    public ResponseEntity<Edicao> criarEdicao(@RequestBody Edicao edicao) {
        Edicao edicaoCriada = edicaoService.criarEdicao(edicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(edicaoCriada);
    }

    // Atualizar detalhes de uma edicao específica
    @Operation(summary = "Atualiza detalhes de uma edição específica", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar dados")
    })
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

    // Atualizar detalhes parciais de uma edicao específica
    @Operation(summary = "Atualiza detalhes parciais de uma edição específica", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar dados")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Edicao> atualizarCampoEdicao(@PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {
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

    // Excluir uma edicao específica
    @Operation(summary = "Exclui uma edição", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edição excluída com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir edição")
    })
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
