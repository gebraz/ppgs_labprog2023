package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.AllQualisProd;
import br.ufma.sppg.dto.DocenteProducao;
import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.service.DocenteService;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;



@RequestMapping("/api/docente")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DocenteController{
    @Autowired
    TecnicaService tecnicaServivce;

    @Autowired
    ProducaoService producaoServivce;

    @Autowired
    OrientacaoService orientacaoServivce;

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    DocenteService docenteService;

    @GetMapping("/obter_producoes/{id}/{data1}/{data2}")
    public ResponseEntity<?> obterProducoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Producao> orientacaoDocente = producaoServivce.obterProducoesDocente(idDocente, data1, data2);
            return ResponseEntity.ok(orientacaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    

    @GetMapping("/obter_orientacoes/{id}/{data1}/{data2}")
    public ResponseEntity<?> obterOrientacoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Orientacao> orientacaoDocente = orientacaoServivce.obterOrientacaoDocente(idDocente, data1, data2);
            return ResponseEntity.ok(orientacaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_tecnicas/{id}")
    public ResponseEntity<?> obterTecnicasDeDocente(@PathVariable(value = "id", required = true) Integer idDocente){

        try{
            List<Tecnica> tecnicaDocente = tecnicaServivce.obterTecnicasDocente(idDocente); 
            return ResponseEntity.ok(tecnicaDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_docentes")
    public ResponseEntity<?> obterDocentes(){
        try{
            List<Docente> docentes = docenteRepository.findAll();
            return ResponseEntity.ok(docentes);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/indicadores/{id}/{data1}/{data2}")
    public ResponseEntity<?> docenteIndice(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){
        var response=docenteService.obterIndicadores(idDocente, data1, data2);
        if(response!=null) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.badRequest().build();
    } 

    @GetMapping("/teste/{data1}/{data2}")
    public ResponseEntity<?> docenteAll(
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){
        var response=docenteService.obtertTodosQualis(data1, data2);
        if(response!=null) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.badRequest().build();
    } 

    @GetMapping("/prodQualis/{data1}/{data2}")
    public ResponseEntity<?> prodQ(@PathVariable(value = "data1", required = true)  Integer data1,@PathVariable(value = "data2", required = true)  Integer data2){
        var response=docenteService.allqualis(data1, data2);        
        if(response!=null) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.badRequest().build();
    }

    @GetMapping("/graficoDocente/{id}/{data1}/{data2}/{tipoQualis}")
    public ResponseEntity<?> getGraficoDocente(
            @PathVariable(value = "id", required = true) Integer id,
            @PathVariable(value = "data1", required = true) Integer data1,
            @PathVariable(value = "data2", required = true) Integer data2,
            @PathVariable(value = "tipoQualis", required = true) String tipoQualis) {

        AllQualisProd response = docenteService.obterQualisPorTipo(id, data1, data2, tipoQualis);
        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/nome/{id}")
    public ResponseEntity<?> DocenteNome( @PathVariable(value = "id", required = true) Integer id){
        var response = docenteService.obterDocentePeloIdNome(id);
         if (response != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }
}
