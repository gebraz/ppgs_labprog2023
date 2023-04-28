package br.ufma.sppg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepository
        extends JpaRepository<Tecnica, Integer> {

}


