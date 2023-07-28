package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.dto.IndiceQualisDTO;
import br.ufma.sppg.dto.QualisStatsDTO;
import br.ufma.sppg.dto.QualisSummaryDTO;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.service.DocenteService;
import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/qualis")
public class QualisController {

    @Autowired
    ProgramaService programaService;
    @Autowired
    DocenteService docenteService;

    // NÃO PASSA O ANO
    /*
     * ao resolver conflitos:
     * mudamos o nome da rota
     * e estamos retornando uma
     * response entity para
     * tratativa de exceções
     */
    @GetMapping(value = "/indice/{idProg}")
    public ResponseEntity obterIndicesCapes(@PathVariable Integer idProg) {

        Indice indice;
        List<Producao> producoes;

        try {
            indice = programaService.obterProducaoIndices(idProg, 1950, 2050);
            producoes = programaService.obterProducoes(idProg, 1950, 2050);
        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        IndiceQualisDTO res = IndiceQualisDTO.builder().indice(indice).producoes(producoes).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // PASSA O ANO
    @GetMapping(value = "/indice/{idProg}/{anoIni}/{anoFim}")
    public ResponseEntity obterIndicesCapes(@PathVariable Integer idProg, @PathVariable Integer anoIni,
            @PathVariable Integer anoFim) {

        Indice indice;
        List<Producao> producoes;

        try {
            indice = programaService.obterProducaoIndices(idProg, anoIni, anoFim);
            producoes = programaService.obterProducoes(idProg, anoIni, anoFim);
        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        IndiceQualisDTO res = IndiceQualisDTO.builder().indice(indice).producoes(producoes).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping(value = "/indice/docente/{idDocente}/{anoIni}/{anoFim}")
    public ResponseEntity obterIndicesCapesDocente(@PathVariable Integer idDocente, @PathVariable Integer anoIni,
            @PathVariable Integer anoFim) {

        Indice indice;
        List<Producao> producoes;

        try {
            indice = docenteService.obterIndice(idDocente, anoIni, anoFim);
            producoes = docenteService.obterProducoes(idDocente, anoIni, anoFim);
        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        IndiceQualisDTO res = IndiceQualisDTO.builder().indice(indice).producoes(producoes).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // NÃO PASSA O ANO
    @GetMapping(value = "/{idProg}/{tipo}")
    public ResponseEntity obterQualisPorTipo(@PathVariable Integer idProg, @PathVariable String tipo) {

        QualisSummaryDTO summary = QualisSummaryDTO.builder().qtd(0).build();

        try {
            List<Producao> producoes = programaService.obterProducoes(idProg, 1950, 2502);
            List<Producao> prodFiltro = new ArrayList<Producao>();
            for (Producao prod : producoes) {

                if (prod.getQualis().equals(tipo)) {
                    summary.setQtd(summary.getQtd() + 1);
                    prodFiltro.add(prod);
                }
            }

            summary.setProds(prodFiltro);
        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<QualisSummaryDTO>(summary, HttpStatus.OK);
    }

    // PASSA O ANO
    @GetMapping(value = "/{idProg}/{tipo}/filter")
    public ResponseEntity obterQualisPorTipo(@PathVariable Integer idProg, @PathVariable String tipo,
            @RequestParam Integer anoIni, @RequestParam Integer anoFim) {

        QualisSummaryDTO summary = QualisSummaryDTO.builder().qtd(0).build();

        try {
            List<Producao> producoes = programaService.obterProducoes(idProg, anoIni, anoFim);
            List<Producao> prodFiltro = new ArrayList<Producao>();
            for (Producao prod : producoes) {

                if (prod.getQualis().equals(tipo)) {
                    summary.setQtd(summary.getQtd() + 1);
                    prodFiltro.add(prod);
                }
            }

            summary.setProds(prodFiltro);
        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<QualisSummaryDTO>(summary, HttpStatus.OK);
    }
    

    // PASSA O ANO
    @GetMapping(value = "/stats/{idProg}/{anoIni}/{anoFim}")
    public ResponseEntity obterEstatisticas(@PathVariable Integer idProg, @PathVariable Integer anoIni,
            @PathVariable Integer anoFim) {

        QualisStatsDTO stats;

        try {
            Integer producoes = programaService.obterProducoes(idProg, anoIni, anoFim).size();
            Integer ori = programaService.obterOrientacoes(idProg, anoIni, anoFim).size();
            Integer tec = programaService.obterTecnicas(idProg, anoIni, anoFim).size();

            stats = QualisStatsDTO.builder().orientacoes(ori).producoes(producoes).tecnicas(tec).build();

        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<QualisStatsDTO>(stats, HttpStatus.OK);
    }

    // NÃO PASSA O ANO
    @GetMapping(value = "/stats/{idProg}")
    public ResponseEntity obterEstatisticas(@PathVariable Integer idProg) {

        QualisStatsDTO stats;

        try {
            Integer producoes = programaService.obterProducoes(idProg, 1950, 2502).size();
            Integer ori = programaService.obterOrientacoes(idProg, 1950, 2502).size();
            Integer tec = programaService.obterTecnicas(idProg, 1950, 2502).size();

            stats = QualisStatsDTO.builder().orientacoes(ori).producoes(producoes).tecnicas(tec).build();

        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<QualisStatsDTO>(stats, HttpStatus.OK);
    }

    @GetMapping(value = "/{idPrograma}/{anoIni}/{anoFim}")
    public ResponseEntity obterQualisPorAno(
            @PathVariable Integer idPrograma,
            @PathVariable Integer anoIni,
            @PathVariable Integer anoFim) {

        try {
            List<Producao> producoes = programaService.obterProducoes(idPrograma, anoIni, anoFim);
            List<List<Integer>> qualis = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                qualis.add(new ArrayList<>(Collections.nCopies(anoFim - anoIni + 1, 0)));
            }

            for (Producao prod : producoes) {
                if (prod.getAno() >= anoIni && prod.getAno() <= anoFim) {
                    if (prod.getTipo() != null && prod.getQualis() != null) {
                        int index = getIndexFromQualis(prod.getQualis());

                        if (index != -1) {
                            qualis.get(index).set(anoFim - prod.getAno(),
                                    qualis.get(index).get(anoFim - prod.getAno()) + 1);
                        }
                    }
                }
            }

            return ResponseEntity.ok(qualis);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private int getIndexFromQualis(String qualisValue) {
        String[] qualisArray = { "A1", "A2", "A3", "A4" };

        for (int i = 0; i < qualisArray.length; i++) {
            if (qualisArray[i].equals(qualisValue)) {
                return i;
            }
        }

        return -1;
    }
}
