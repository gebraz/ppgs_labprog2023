package br.ufma.sppg.Controller;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import br.ufma.sppg.service.ProgramaService;

@RestController
@RequestMapping("api/v1/programa") 
public class ProgramaController {
    

    @Autowired
    private ProgramaService programaService;

    @GetMapping()
    public ResponseEntity<Object> getPrograma(@RequestParam String nome){
        var response = programaService.obterPrograma(nome);

        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        

    }
    @GetMapping("/docentes")
    public ResponseEntity<Object> getDocentePrograma(@RequestParam Integer id){
        var response = programaService.obterDocentesPrograma(id);

        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        

    }

    @GetMapping("/indices")
    public ResponseEntity<Object> getIndice(@RequestParam Integer id,@RequestParam Integer anoIni, @RequestParam Integer anoFin){
        var response = programaService.obterProducaoIndices(id, anoIni, anoFin);

        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        

    }
    @GetMapping("/orientacao")
    public ResponseEntity<Object> getOrientacao(@RequestParam Integer id,@RequestParam Integer anoIni, @RequestParam Integer anoFin){
        var response = programaService.obterOrientacoes(id, anoIni, anoFin);

        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        

    }
    @GetMapping("/producoes")
    public ResponseEntity<Object> getProducao(@RequestParam Integer id,@RequestParam Integer anoIni, @RequestParam Integer anoFin){
        var response = programaService.obterProducoes(id, anoIni, anoFin);
        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        

    }
    @GetMapping("/tecnicas")
    public ResponseEntity<Object> getTecnicas(@RequestParam Integer id,@RequestParam Integer anoIni, @RequestParam Integer anoFin){
        var response = programaService.obterTecnicas(id, anoIni, anoFin);

        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        

    }

}

