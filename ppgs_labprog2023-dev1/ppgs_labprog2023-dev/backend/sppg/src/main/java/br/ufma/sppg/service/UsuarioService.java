package br.ufma.sppg.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Usuario;
import br.ufma.sppg.repo.UsuarioRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository userRepository;   

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(Usuario request)  {
        Usuario existeEmail= userRepository.findByEmail(request.getEmail());
        if (existeEmail!=null) {
            throw new ServicoRuntimeException("User com email " +request.getEmail()+ " ja existe");
        }

        
            var hash = passwordEncoder.encode(request.getSenha());
            
            request.setSenha(hash);
           
            if(request.getUsername()!=null){
                userRepository.save(request);
            }    
            return request.getId().toString();
      
    }
        
    public Usuario getEmail(String email){
        var user = userRepository.findByEmail(email);
        return user;

    }   

    public Usuario getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
        
    }


}
