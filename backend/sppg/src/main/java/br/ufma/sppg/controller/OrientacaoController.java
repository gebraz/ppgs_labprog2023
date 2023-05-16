package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.dto.CriarOrientacaoDTO;

@RestController
@RequestMapping(value = "/api/orientacao")
public class OrientacaoController {

    @Autowired
    OrientacaoService service;

    @GetMapping()
    public List<Orientacao> obterTodasOrientacoes() {
        return service.obterTodasOrientacoes();
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody CriarOrientacaoDTO dto) {
        return new ResponseEntity<>(service.criarOrientacao(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/summary")
    public List<Orientacao> obterOrientacoes(@RequestParam String tipo, Integer value) {
        if (tipo.equals("ppg")) {
            return obterOrientacaosPPG(value);
        }

        throw new IllegalArgumentException("Erro.");
    }

    private List<Orientacao> obterOrientacaosPPG(Integer idPrograma) {
        return service.obterOrientacoesPPG(idPrograma);
    }

}
