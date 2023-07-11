package br.ufma.sppg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufma.sppg.Security.IJwtService;
import br.ufma.sppg.dto.AuthenticateReponse;
import br.ufma.sppg.dto.AuthenticateRequest;
import br.ufma.sppg.model.Usuario;
import br.ufma.sppg.repo.UsuarioRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;


@Service
public class AuthenticantonService  {
    
    @Autowired
    private UsuarioService userService;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticateReponse authenticate(AuthenticateRequest request) throws Exception {
       var usuario = userService.getEmail(request.email);

        if(usuario == null){
            return null;
        }

        if(!passwordEncoder.matches(request.password, usuario.getSenha())){;
           throw new Exception("Credencias invalidas!");
        }
        
        var token  = jwtService.generateToken(usuario.getId());

        var reponse = new AuthenticateReponse();
        reponse.setUserID(usuario.getId());
        reponse.setToken(token);

        return reponse;
    }
}