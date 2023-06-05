package br.ufma.sppg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    @Autowired
    ProgramaService servicePPG;

    @Autowired
    ProgramaService programa;

    @GetMapping("/obterPrograma")
    public ResponseEntity obterPrograma(
            @RequestParam("programa") String nome){
        try{
            List <Programa> programas = programa.obterPrograma(nome);
            return new ResponseEntity(programas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterDocentesPrograma")
    public ResponseEntity obterDocentesPrograma(
        @RequestParam("docente") Integer idPrograma){
        try{
            List <Docente> docentes = programa.obterDocentesPrograma(idPrograma);
            return new ResponseEntity(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesPrograma")
    public ResponseEntity obterProducoesPrograma(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Producao> producoes = programa.obterProducoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesPrograma")
    public ResponseEntity obterOrientacoesPorgrama(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Orientacao> orientacoes = programa.obterOrientacoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasPrograma")
    public ResponseEntity obterTecnicasPrograma(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Tecnica> tecnicas = programa.obterTecnicas(idPrograma, anoIni, anoFin);
            return new ResponseEntity(tecnicas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    ////////////////

    @GetMapping("/qtv_orientacoes_producao") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoProducao(
                        @RequestParam("idPrograma") Integer idPrograma,
                        @RequestParam("anoInicial") Integer anoIni,
                        @RequestParam("anoFinal") Integer aniFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoProducao(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/qtv_orientacoes_tecnica") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoTecnica(
                        @RequestParam("idPrograma") Integer idPrograma,
                        @RequestParam("anoInicial") Integer anoIni,
                        @RequestParam("anoFinal") Integer aniFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoTecnica(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);
            
        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
