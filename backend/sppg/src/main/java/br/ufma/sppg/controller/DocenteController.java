package br.ufma.sppg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.DocenteService;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RequestMapping("/api/docente")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DocenteController {
    @Autowired
    TecnicaService tecnicaServivce;

    @Autowired
    ProducaoService producaoServivce;

    

    @Autowired
    OrientacaoService orientacaoServivce;

    @Autowired
    DocenteService service;

    @GetMapping("/obterDocente/{idDocente}")
    public ResponseEntity obterDocentePorId(@PathVariable(value = "idDocente", required = true) Integer idDocente) {
        try {
            Optional<Docente> docente = service.obterDocentePorId(idDocente);
            return ResponseEntity.ok(docente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterDocentes")
    public ResponseEntity obterDocentes() {
        try {
            List<Docente> docentes = service.obterDocentes();
            return ResponseEntity.ok().body(docentes);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoes/{id}/{anoIni}/{anoFim}")
    public ResponseEntity<?> obterProducoesDeDocente(
            @PathVariable(value = "id", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFim", required = true) Integer anoFim) {

        try {
            List<Producao> producaoDocente = producaoServivce.obterProducoesDocente(idDocente, anoIni, anoFim);
            return ResponseEntity.ok(producaoDocente);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoes/{id}/{anoIni}/{anoFim}")
    public ResponseEntity<?> obterOrientacoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFim", required = true) Integer anoFim) {

        try {
            List<Orientacao> orientacaoDocente = orientacaoServivce.obterOrientacaoDocente(idDocente, anoIni, anoFim);
            return ResponseEntity.ok(orientacaoDocente);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicas/{id}")
    public ResponseEntity<?> obterTecnicasDeDocente(@PathVariable(value = "id", required = true) Integer idDocente) {

        try {
            List<Tecnica> tecnicaDocente = tecnicaServivce.obterTecnicasDocente(idDocente);
            return ResponseEntity.ok(tecnicaDocente);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}