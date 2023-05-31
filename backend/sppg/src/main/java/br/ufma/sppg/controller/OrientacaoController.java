package br.ufma.sppg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
public class OrientacaoController {
  @Autowired
  OrientacaoService orientacaoService;

  @Autowired
  ProducaoService producaoService;

  @Autowired
  TecnicaService tecnicaService;

  @GetMapping("/obterOrientacoesDocenteComTecnica")
  public ResponseEntity<?> obterOrientacoesDocenteComTecnica(
      @RequestParam("docente") Integer idDocente, Integer dataInicio, Integer dataFim) {
    try {
      List<Orientacao> orientacoes = orientacaoService.obterOrientacaoDocente(idDocente, dataInicio, dataFim);

      for (Orientacao orientacao : orientacoes) {
        Optional<List<Tecnica>> tecnicas = tecnicaService.obterTecnicasOrientacaoPorPeriodo(orientacao.getId(),
            dataInicio,
            dataFim);

        orientacao.setTecnicas(tecnicas.get());
      }

      return new ResponseEntity<>(
          orientacoes,
          HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  // @GetMapping("/obterOrientacoesDocenteComProducao")
  // public ResponseEntity<?> obterOrientacaoDocenteComProducao(
  // @RequestParam("docente") Integer idDocente, Integer dataInicio, Integer
  // dataFim) {
  // try {
  // List<Orientacao> orientacoes =
  // orientacaoService.obterOrientacaoDocente(idDocente, dataInicio, dataFim);

  // for (Orientacao orientacao : orientacoes) {
  // Optional<List<Tecnica>> producoes =
  // producaoService.obterProducoesOrientacaoPorPeriodo(orientacao.getId(),
  // dataInicio,
  // dataFim);

  // orientacao.setTecnicas(tecnicas.get());
  // }

  // return new ResponseEntity<>(
  // orientacoes,
  // HttpStatus.OK);
  // } catch (ServicoRuntimeException e) {
  // return ResponseEntity.badRequest().body(e.getMessage());
  // }
  // }
}