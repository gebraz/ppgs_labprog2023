package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.model.Usuario;
import br.ufma.sppg.service.UsuarioService;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    @Autowired
    public UsuarioService userService;
   
    
   @PostMapping()
    public ResponseEntity<String> CreateUsuario(@RequestBody Usuario request){
        var response = userService.createUser(request);
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/senha")
    public ResponseEntity<Usuario> Getemail(@RequestParam String email ){
          var response = userService.getEmail(email);
           if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }
    
    
}
