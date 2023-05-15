package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.service.OrientacaoService;

@RestController
@RequestMapping(value = "/api/orientacao")
public class OrientacaoController {

    @Autowired
    OrientacaoService service;

    @GetMapping
    public List<Orientacao> obterTodasOrientacoes() {
        return service.obterTodasOrientacoes();
    }
}
