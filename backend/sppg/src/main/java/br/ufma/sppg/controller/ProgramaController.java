package br.ufma.sppg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.RegrasRunTime;

@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    @Autowired
    ProgramaService servicePPG;

    @GetMapping("/qtv_orientacoes_producao") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoProducao(
                        @RequestParam("idPrograma") Integer idPrograma,
                        @RequestParam("anoInicial") Integer anoIni,
                        @RequestParam("anoFinal") Integer aniFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoProducao(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        }catch(RegrasRunTime e){
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
            
        }catch(RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
