package br.ufma.sppg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Programa;

public interface ProgramaRepository 
    extends JpaRepository<Programa, Integer> {
        
}
