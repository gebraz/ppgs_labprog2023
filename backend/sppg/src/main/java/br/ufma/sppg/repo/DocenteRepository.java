package br.ufma.sppg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Docente;
import java.util.List;


public interface DocenteRepository 
    extends JpaRepository<Docente, Integer>{
    
    Optional<Docente> findById(Integer idDocente);

    boolean existsById(Integer idDocente);

    List<Docente> findByNome(String nome);
}
