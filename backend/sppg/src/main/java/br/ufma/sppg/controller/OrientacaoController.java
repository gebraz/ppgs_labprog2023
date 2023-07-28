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
import br.ufma.sppg.services.OrientacaoService;
import br.ufma.sppg.services.ProducaoService;
import br.ufma.sppg.services.TecnicaService;
import br.ufma.sppg.services.exceptions.ServicoRuntimeException;

@RestController
public class OrientacaoController {

  @Autowired
  private OrientacaoService orientacaoService;

  @Autowired
  private ProducaoService producaoService;

  @Autowired
  private TecnicaService tecnicaService;

  @GetMapping("/obterOrientacoesDocenteComTecnica")
  public ResponseEntity<?> obterOrientacoesDocenteComTecnica(
      @RequestParam("docente") Integer idDocente, @RequestParam("dataInicio") Integer dataInicio,
      @RequestParam("dataFim") Integer dataFim) {
    
    try {
      Optional<List<Orientacao>> orientacoes = orientacaoService.(idDocente,
          dataInicio, dataFim);
      
      return new ResponseEntity<>(orientacoes.orElse(null), HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/obterOrientacoesDocenteComProducao")
  public ResponseEntity<?> obterOrientacoesDocenteComProducao(
      @RequestParam("docente") Integer idDocente, @RequestParam("dataInicio") Integer dataInicio,
      @RequestParam("dataFim") Integer dataFim) {
    
    try {
      Optional<List<Orientacao>> orientacoes = orientacaoService.obterOrientacoesComProducaoPorPeriodo(idDocente,
          dataInicio, dataFim);

      return new ResponseEntity<>(orientacoes.orElse(null), HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
