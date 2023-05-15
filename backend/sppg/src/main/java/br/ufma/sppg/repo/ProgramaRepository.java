package br.ufma.sppg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Programa;

public interface ProgramaRepository
        extends JpaRepository<Programa, Integer> {
    Optional<Programa> findById(Integer idProgama);
}
