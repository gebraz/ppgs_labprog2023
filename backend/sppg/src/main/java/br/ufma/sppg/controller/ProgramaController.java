package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.services.ProgramaService;
import br.ufma.sppg.services.exceptions.RegraNegocioRunTime;


@RestController
@RequestMapping("/api/programa")
public class ProgramaController {

    @Autowired
    ProgramaService programa;

    @GetMapping("/obterProgramaController")
    public ResponseEntity obterProgramaController(
            @RequestParam("programa") String nome){
        try{
            List <Programa> programas = programa.obterPrograma(nome);
            return new ResponseEntity(programas, HttpStatus.OK);
        }catch (RegraNegocioRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterDocentesProgramaController")
    public ResponseEntity obterDocentesProgramaController(
        @RequestParam("docente") Integer idPrograma){
        try{
            List <Docente> docentes = programa.obterDocentePrograma(idPrograma);
            return new ResponseEntity(docentes, HttpStatus.OK);
        }catch (RegraNegocioRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesProgramaController")
    public ResponseEntity obterProducoesProgramaController(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Producao> producoes = programa.obterProducoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (RegraNegocioRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesProgramaController")
    public ResponseEntity obterOrientacoesPorgramaController(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Orientacao> orientacoes = programa.obterOrientacoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity(orientacoes, HttpStatus.OK);
        }catch (RegraNegocioRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasProgramaController")
    public ResponseEntity obterTecnicasProgramaController(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Tecnica> tecnicas = programa.obterTecnicas(idPrograma, anoIni, anoFin);
            return new ResponseEntity(tecnicas, HttpStatus.OK);
        }catch (RegraNegocioRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
