package br.ufma.sppg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Producao;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.service.ProducaoService;

@RestController
@RequestMapping("/api/producao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProducaoController {
  @Autowired
  ProducaoService producaoService;

  @Autowired
  ProducaoRepository producaoRepository;


  @PostMapping(path = "/informarEstatisticasProducao"
      
        )
        private ResponseEntity<Producao> informarEstatisticasProducao(@RequestBody Producao producao) {
          //show producao as a text in console
          Producao producaoExistente = producaoRepository.findById(producao.getId()).get();

          if (producaoExistente == null) {
            return ResponseEntity.notFound().build();
          }
        
      producaoExistente.setQtdGrad(producao.getQtdGrad());
        producaoExistente.setQtdMestrado(producao.getQtdMestrado());
        producaoExistente.setQtdDoutorado(producao.getQtdDoutorado());
    Producao producaoRetorno = producaoService.informarEstatisticasProducao(producaoExistente);
    if (producaoRetorno == null) {
      return ResponseEntity.badRequest().body(producaoRetorno);
    }
    return ResponseEntity.ok(producaoRetorno);

  }
}
