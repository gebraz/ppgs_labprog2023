package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.dto.IndiceQualisDTO;
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
    @GetMapping(value = "/{idProg}")
    public IndiceQualisDTO obterIndicesCapes(@PathVariable Integer idProg) {

        Indice indice;
        List<Producao> producoes;

        try {
            indice = service.obterProducaoIndices(idProg, 1950, 2050);
            producoes = service.obterProducoes(idProg, 1950, 2050);
        } catch (ServicoRuntimeException e) {
            throw e;
        }

        return IndiceQualisDTO.builder().indice(indice).producoes(producoes).build();
    }

    // PASSA O ANO
    @GetMapping(value = "/{idProg}/filter")
    public IndiceQualisDTO obterIndicesCapes(@PathVariable Integer idProg, @RequestParam Integer anoIni,
            @RequestParam Integer anoFim) {

        Indice indice;
        List<Producao> producoes;

        try {
            indice = service.obterProducaoIndices(idProg, anoIni, anoFim);
            producoes = service.obterProducoes(idProg, anoIni, anoFim);
        } catch (ServicoRuntimeException e) {
            throw e;
        }

        return IndiceQualisDTO.builder().indice(indice).producoes(producoes).build();
    }

    // NÃO PASSA O ANO
    @GetMapping(value = "/{idProg}/{tipo}")
    public QualisSummaryDTO obterQualisPorTipo(@PathVariable Integer idProg, @PathVariable String tipo) {

        QualisSummaryDTO summary = QualisSummaryDTO.builder().ori(0).prod(0).build();

        try {
            List<Producao> producoes = service.obterProducoes(idProg, 1950, 2502);
            for (Producao prod : producoes) {

                if (prod.getQualis().equals(tipo)) {
                    summary.setProd(summary.getProd() + 1);
                    if (prod.getOrientacoes() != null)
                        summary.setOri(summary.getOri() + prod.getOrientacoes().size());
                }
            }
        } catch (ServicoRuntimeException e) {
            throw e;
        }

        return summary;
    }

    // PASSA O ANO
    @GetMapping(value = "/{idProg}/{tipo}/filter")
    public QualisSummaryDTO obterQualisPorTipo(@PathVariable Integer idProg, @PathVariable String tipo,
            @PathVariable Integer anoIni, @PathVariable Integer anoFim) {

        QualisSummaryDTO summary = QualisSummaryDTO.builder().ori(0).prod(0).build();

        try {
            List<Producao> producoes = service.obterProducoes(idProg, anoIni, anoFim);
            for (Producao prod : producoes) {

                if (prod.getQualis().equals(tipo)) {
                    summary.setProd(summary.getProd() + 1);
                    if (prod.getOrientacoes() != null)
                        summary.setOri(summary.getOri() + prod.getOrientacoes().size());
                }
            }
        } catch (ServicoRuntimeException e) {
            throw e;
        }

        return summary;
    }
}
