package br.ufma.sppg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrientacaoController {

  @Autowired
  OrientacaoService orientacaoService;

  @Autowired
  ProducaoService producaoService;

  @Autowired
  TecnicaService tecnicaService;

  @GetMapping("/obterOrientacoesDocenteComTecnica/{idDocente}/{anoIni}/{anoFim}")
  public ResponseEntity<?> obterOrientacoesDocenteComTecnica(
      @RequestParam("docente") Integer idDocente, Integer anoIni, Integer anoFim) {
    try {
      Optional<List<Orientacao>> orientacoes = orientacaoService.obterOrientacoesComTecnicaPorPeriodo(idDocente,
          anoIni, anoFim);
      return new ResponseEntity<>(
          orientacoes.get(),
          HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/obterOrientacoesDocenteComProducao/{idDocente}/{anoIni}/{anoFim}")
  public ResponseEntity<?> obterOrientacaoDocenteComProducao(
      @RequestParam("docente") Integer idDocente, Integer anoIni, Integer anoFim) {
    try {
      Optional<List<Orientacao>> orientacoes = orientacaoService.obterOrientacoesComProducaoPorPeriodo(idDocente,
          anoIni, anoFim);

      return new ResponseEntity<>(
          orientacoes.get(),
          HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}