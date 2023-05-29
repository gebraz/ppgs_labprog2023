package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.OrientacaoResponse;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.RegrasRunTime;

@RestController
public class DocenteController{
    @Autowired
    TecnicaService tecnicaServivce;

    @Autowired
    ProducaoService producaoServivce;

    @Autowired
    OrientacaoService orientacaoServivce;

    @GetMapping
    public ResponseEntity<?> obterProducoesDeDocente(
            @RequestParam("docente") Integer idDocente, Integer data1, Integer data2){

        try{
            List<Producao> producaoDocente = producaoServivce.obterProducoesDocente(idDocente, data1, data2);
            return ResponseEntity.ok(producaoDocente);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obterOrientacoesDeDocente(
            @RequestParam("docente") Integer idDocente){

        try{
            ArrayList<OrientacaoResponse> orientacaoDocente = orientacaoServivce.obterOrientacaoDocentes(idDocente); //não vou setar o get mapping ainda
            return ResponseEntity.ok(orientacaoDocente);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> obterTecnicasDeDocente(
            @RequestParam("docente") Integer idDocente){

        try{
            List<Tecnica> tecnicaDocente = tecnicaServivce.obterTecnicasDocente(idDocente); //não vou setar o get mapping ainda
            return ResponseEntity.ok(tecnicaDocente);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}