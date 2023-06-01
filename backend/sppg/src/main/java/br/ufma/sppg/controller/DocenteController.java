package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.List;

import br.ufma.sppg.dto.EstatisticasProducao;
import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import br.ufma.sppg.dto.OrientacaoResponse;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;


@RestController
public class DocenteController{
    @Autowired
    TecnicaService tecnicaService;

    @Autowired
    ProducaoService producaoService;

    @Autowired
    OrientacaoService orientacaoService;

    @Autowired
    DocenteService docenteService;

   // @GetMapping
    public ResponseEntity<?> obterProducoesDeDocente(
            @RequestParam("docente") Integer idDocente, Integer data1, Integer data2){

        try{
            List<Producao> producaoDocente = producaoService.obterProducoesDocente(idDocente, data1, data2);
            return ResponseEntity.ok(producaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /*
    @GetMapping
    public ResponseEntity<?> obterOrientacoesDeDocente(
            @RequestParam("docente") Integer idDocente){

        try{
            ArrayList<OrientacaoResponse> orientacaoDocente = orientacaoServivce.obterOrientacaoDocentes(idDocente); //não vou setar o get mapping ainda
            return ResponseEntity.ok(orientacaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
     */

    public ResponseEntity<?> obterTecnicasDeDocente(
            @RequestParam("docente") Integer idDocente){

        try{
            List<Tecnica> tecnicaDocente = tecnicaService.obterTecnicasDocente(idDocente); //não vou setar o get mapping ainda
            return ResponseEntity.ok(tecnicaDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("api/obterIndicesCapes")
    public ResponseEntity obterIndicesCapes(
            @RequestParam("docente") Integer idDocente, Integer anoInicial, Integer anoFinal){

        try{
            Indice indicesCapes = docenteService.obterIndice(idDocente, anoInicial, anoFinal);
            return new ResponseEntity(indicesCapes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("api/obterProducoesPorQualisEAno")
    public ResponseEntity obterProducoesPorQualisEAno(
            @RequestParam("docente") Integer idDocente, String qualis, Integer anoInicial, Integer anoFinal){

        try{
            List<Producao> producoes = producaoService.obterProducoesPPG(idDocente, anoInicial, anoFinal);
            List<Producao> producoesPorQualisEAno = new ArrayList<>();
            for(Producao p : producoes){
                if(p.getQualis().equals(qualis)){
                    producoesPorQualisEAno.add(p);
                }
            }
            return new ResponseEntity(producoesPorQualisEAno, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("api/obterEstatisticasProducao")
    public ResponseEntity obterEstatisticasProducao(
            @RequestParam("docente") Integer idDocente, Integer anoInicial, Integer anoFinal){

        try{
            List<Producao> producoes = producaoService.obterProducoesPPG(idDocente, anoInicial, anoFinal);
            List<EstatisticasProducao> estatisticasProducoes = new ArrayList<>();
            for(Producao p : producoes){
                EstatisticasProducao ep = new EstatisticasProducao(p.getQtdGrad(), p.getQtdMestrado(), p.getQtdDoutorado());
                estatisticasProducoes.add(ep);
            }
            return new ResponseEntity(estatisticasProducoes, HttpStatus.OK);
        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}