package br.ufma.sppg.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufma.sppg.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
    
    UserDetails findByEmail(String email); 

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario getByEmail(@Param("email") String email); 
    
    
   
}
