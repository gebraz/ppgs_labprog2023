package br.ufma.sppg.controller.loader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.service.loader.ImportadorService;

@RestController
@RequestMapping("/api/importador")
public class ImportadorControler {
    @Autowired
	ImportadorService imp;
		
    @GetMapping
    public ResponseEntity init() {
        try { 
            //TODO: mudar forma de passar repositório para web...
		    List<String> refs = imp.importadorEmMassa("/Users/gebraz/Desktop/sppgs_recursos/ppgcc_xml/");
            return ResponseEntity.ok(refs);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}


}
