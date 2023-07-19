package br.ufma.sppg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import br.ufma.sppg.Security.JwtService;
import br.ufma.sppg.dto.AuthenticateReponse;
import br.ufma.sppg.dto.AuthenticateRequest;
import br.ufma.sppg.model.Usuario;
import br.ufma.sppg.repo.UsuarioRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;


@Service
public class AuthenticantonService  {
     
    @Autowired
    private AuthenticationManager authenticationManager; 
    
    @Autowired
    private UsuarioService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
   

    public AuthenticateReponse auth(AuthenticateRequest request) throws Exception {
        var user = userService.gettEmail(request.email);

        if(user == null){
            return null;
        }
        if(request.senha == null) throw new Exception("Credencias ");{
            
        }

        if(!passwordEncoder.matches(request.senha,user.getPassword())){
           throw new Exception("Credencias invalidas!");
        }

        var token  = jwtService.generateToken(user);

        var reponse = new AuthenticateReponse();
        
        reponse.setToken(token);

        return reponse;
    }
    
    
}