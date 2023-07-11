package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Producao;
import br.ufma.sppg.service.ProducaoService;


@RestController
@RequestMapping("/api/producao")
public class ProducaoController {
    

    @Autowired
    public ProducaoService producao;


    @GetMapping("/all")
    public ResponseEntity<List<Producao>> allProducao(){
        var response=producao.obterProducao();
		if(response!=null) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.badRequest().build();
    } 
}
