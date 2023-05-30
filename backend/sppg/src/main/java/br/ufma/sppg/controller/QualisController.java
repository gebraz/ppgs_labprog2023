package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.QualisDTO;
import br.ufma.sppg.dto.QualisENUM;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.service.ProgramaService;

@RestController
@RequestMapping(value = "/api/v1/qualis")
public class QualisController {

    @Autowired
    ProgramaService service;

    @GetMapping
    public List<Programa> todos() {
        return service.todos();
    }

    @GetMapping(value = "/{idProg}/filter")
    public String obterIndicesCapes(@PathVariable Integer idProg, @RequestParam Integer anoIni,
            @RequestParam Integer anoFim) {
        /*
         * TODO: IMPLEMENTAR METODO
         * 
         */
        return "passou o ano";
    }

    @GetMapping(value = "/{idProg}")
    public String obterIndicesCapes(@PathVariable Integer idProg) {

        /*
         * TODO: IMPLEMENTAR METODO
         * 
         */

        return "não passou o ano";
    }

    @GetMapping(value = "/{idProg}/{tipo}")
    public String obterQualisPorTipo(@PathVariable Integer idProg, @PathVariable QualisENUM tipo) {

        /*
         * TODO: IMPLEMENTAR METODO
         * 
         */

        return "não passou o ano";
    }

    @GetMapping(value = "/{idProg}/{tipo}/filter")
    public String obterQualisPorTipo(@PathVariable Integer idProg, @PathVariable QualisENUM tipo,
            @PathVariable Integer anoIni, @PathVariable Integer anoFim) {

        /*
         * TODO: IMPLEMENTAR METODO
         * 
         */

        return "não passou o ano";
    }
}
