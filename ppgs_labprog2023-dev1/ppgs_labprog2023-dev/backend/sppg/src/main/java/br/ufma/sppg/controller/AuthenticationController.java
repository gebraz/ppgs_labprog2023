package br.ufma.sppg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.AuthenticateReponse;
import br.ufma.sppg.dto.AuthenticateRequest;
import br.ufma.sppg.service.AuthenticantonService;

@RestController
@RequestMapping("api/authentication") 
public class AuthenticationController {
    
    @Autowired
    private AuthenticantonService authenticationService;

    @PostMapping()
    public ResponseEntity<AuthenticateReponse> authenticate(@RequestBody AuthenticateRequest  request){
        try {
            var res =ResponseEntity.ok().body(authenticationService.authenticate(request));
            return res;
     
        } catch (Exception e) {
            return new ResponseEntity (e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
              

    }


}