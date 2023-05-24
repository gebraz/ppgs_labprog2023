package br.ufma.sppg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Docente;

public interface DocenteRepository 
    extends JpaRepository<Docente, Integer>{
    
}
