package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.DocenteProducaoDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    @Autowired
    ProgramaService programaService;

    @Autowired
    ProducaoService producaoService;

    @GetMapping("/obterPrograma/{nomePrograma}")
    public ResponseEntity obterPrograma(@PathVariable String nomePrograma) {
        try {
            List<Programa> programa = programaService.obterPrograma(nomePrograma);
            return new ResponseEntity(programa, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @GetMapping("/obterDocentesPrograma/{idPrograma}")
    public ResponseEntity obterDocentesPrograma(
            @PathVariable Integer idPrograma) {
        try {
            List<Docente> docentes = programaService.obterDocentesPrograma(idPrograma);
            return new ResponseEntity(docentes, HttpStatus.OK);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProgramas")
    public ResponseEntity obterProgramas() {
        try {
            List<Programa> programas = programaService.obterProgramas();
            return new ResponseEntity(programas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoes/{anoIni}/{anoFim}")
    public ResponseEntity obterProducoesDocente(
            @PathVariable Integer anoIni,
            @PathVariable Integer anoFim) {

        try {
            List<DocenteProducaoDTO> producaoDocente = producaoService.obterProducoesDocentes(anoIni, anoFim);
            List<DocenteProducaoDTO> returnList = new ArrayList<>();

            for (DocenteProducaoDTO docenteProducao : producaoDocente) {
                List<Integer> qualis = new ArrayList<>(Collections.nCopies(8, 0));

                for (Producao producao : docenteProducao.getProducoes()) {
                    String qualisValue = producao.getQualis();

                    if (qualisValue != null) {
                        int index = getIndexFromQualis(qualisValue);

                        if (index != -1) {
                            qualis.set(index, qualis.get(index) + 1);
                        }
                    }
                }

                DocenteProducaoDTO returnObject = DocenteProducaoDTO.builder()
                        .docente(docenteProducao.getDocente())
                        .qualis(qualis)
                        .build();

                returnList.add(returnObject);
            }

            return ResponseEntity.ok(returnList);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private int getIndexFromQualis(String qualisValue) {
        String[] qualisArray = { "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4" };

        for (int i = 0; i < qualisArray.length; i++) {
            if (qualisArray[i].equals(qualisValue)) {
                return i;
            }
        }

        return -1;
    }

    @GetMapping("/obterProducoesPrograma/{idPrograma}/{anoIni}/{anoFim}")
    public ResponseEntity obterProducoesPrograma(
        @PathVariable Integer idPrograma, @PathVariable Integer anoIni,
            @PathVariable Integer anoFim){
        try{
            List <Producao> producoes = programaService.obterProducoes(idPrograma, anoIni, anoFim);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesPrograma")
    public ResponseEntity obterOrientacoesPorgrama(
            @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFim) {
        try {
            List<Orientacao> orientacoes = programaService.obterOrientacoes(idPrograma, anoIni, anoFim);
            return new ResponseEntity(orientacoes, HttpStatus.OK);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasPrograma")
    public ResponseEntity obterTecnicasPrograma(
            @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFim) {
        try {
            List<Tecnica> tecnicas = programaService.obterTecnicas(idPrograma, anoIni, anoFim);
            return new ResponseEntity(tecnicas, HttpStatus.OK);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/qtv_orientacoes_producao") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoProducao(
            @RequestParam("idPrograma") Integer idPrograma,
            @RequestParam("anoInicial") Integer anoIni,
            @RequestParam("anoFimal") Integer aniFin) {

        try {
            Integer quantitativo = programaService.quantitativoOrientacaoProducao(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/qtv_orientacoes_tecnica") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoTecnica(
            @RequestParam("idPrograma") Integer idPrograma,
            @RequestParam("anoInicial") Integer anoIni,
            @RequestParam("anoFimal") Integer aniFin) {

        try {
            Integer quantitativo = programaService.quantitativoOrientacaoTecnica(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
