package br.ufma.sppg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica, Long> {
    Optional<Tecnica> findById(Integer idTecnica);

    Boolean existsById(Integer idTecnica);

}
