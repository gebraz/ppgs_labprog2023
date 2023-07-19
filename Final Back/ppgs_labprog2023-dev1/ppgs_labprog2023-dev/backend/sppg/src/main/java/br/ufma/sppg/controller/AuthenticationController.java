package br.ufma.sppg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.AuthenticateReponse;
import br.ufma.sppg.dto.AuthenticateRequest;
import br.ufma.sppg.model.Usuario;
import br.ufma.sppg.service.AuthenticantonService;
import br.ufma.sppg.service.UsuarioService;

@RestController
@RequestMapping("api/auth") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    

    
    @Autowired
    private AuthenticantonService authenticationService;

    @Autowired
    public UsuarioService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticateReponse> authenticate(@RequestBody AuthenticateRequest  request){
       
        try {
            var res =ResponseEntity.ok().body(authenticationService.auth(request));//auth
            return res;
     
        } catch (Exception e) {
            return new ResponseEntity (e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
              

    }

   
    @PostMapping("/register")
    public ResponseEntity<String> CreateUsuario(@RequestBody Usuario request){
        var response = userService.createUser(request);
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/teste")
    public ResponseEntity<Usuario> Getemail(@RequestParam String email ){
          var response = userService.gettEmail(email);
           if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }
}