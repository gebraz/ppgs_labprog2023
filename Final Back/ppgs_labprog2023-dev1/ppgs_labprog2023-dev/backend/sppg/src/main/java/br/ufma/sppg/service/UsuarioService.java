package br.ufma.sppg.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Usuario;
import br.ufma.sppg.repo.UsuarioRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository userRepository;   

   

    public String createUser(Usuario request)  {
        Usuario existeEmail= userRepository.getByEmail(request.getEmail());
        if (existeEmail!=null) {
            throw new ServicoRuntimeException("User com email " +request.getEmail()+ " ja existe");
        }

        
            String encryptedPassword = new BCryptPasswordEncoder().encode(request.getSenha());
            
            request.setSenha(encryptedPassword);
           
            if(request.getUsername()!=null){
                userRepository.save(request);
            }    
            return request.getId().toString();
      
    }
        
    public Usuario gettEmail(String email){
        var user = userRepository.getByEmail(email);
        return user;

    }   

    public Usuario getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
        
    }


}
