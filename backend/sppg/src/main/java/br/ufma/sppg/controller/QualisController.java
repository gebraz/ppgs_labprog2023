package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
@RequestMapping(value = "/api/v1/qualis")
public class QualisController {

    @Autowired
    ProgramaService service;

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
            indice = service.obterProducaoIndices(idProg, 1950, 2050);
            producoes = service.obterProducoes(idProg, 1950, 2050);
        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        IndiceQualisDTO res = IndiceQualisDTO.builder().indice(indice).producoes(producoes).build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // PASSA O ANO
    @GetMapping(value = "/indice/{idProg}/filter")
    public ResponseEntity obterIndicesCapes(@PathVariable Integer idProg, @RequestParam Integer anoIni,
            @RequestParam Integer anoFim) {

        Indice indice;
        List<Producao> producoes;

        try {
            indice = service.obterProducaoIndices(idProg, anoIni, anoFim);
            producoes = service.obterProducoes(idProg, anoIni, anoFim);
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
            List<Producao> producoes = service.obterProducoes(idProg, 1950, 2502);
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
            List<Producao> producoes = service.obterProducoes(idProg, anoIni, anoFim);
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
    @GetMapping(value = "/stats/{idProg}/filter")
    public ResponseEntity obterEstatisticas(@PathVariable Integer idProg, @RequestParam Integer anoIni,
            @RequestParam Integer anoFim) {

        QualisStatsDTO stats;

        try {
            Integer producoes = service.obterProducoes(idProg, anoIni, anoFim).size();
            Integer ori = service.obterOrientacoes(idProg, anoIni, anoFim).size();
            Integer tec = service.obterTecnicas(idProg, anoIni, anoFim).size();

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
            Integer producoes = service.obterProducoes(idProg, 1950, 2502).size();
            Integer ori = service.obterOrientacoes(idProg, 1950, 2502).size();
            Integer tec = service.obterTecnicas(idProg, 1950, 2502).size();

            stats = QualisStatsDTO.builder().orientacoes(ori).producoes(producoes).tecnicas(tec).build();

        } catch (ServicoRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<QualisStatsDTO>(stats, HttpStatus.OK);
    }
}
