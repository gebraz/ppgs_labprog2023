package br.ufma.sppg.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica, Integer> {
    Optional<Tecnica> findById(Integer idTecnica);

    // Obter todas as técnicas de um docente em um período
    @Query("SELECT t FROM Tecnica t " +
            "JOIN Docente d " +
            "WHERE d.id = :idDocente " +
            "AND t.ano >= :dataInicio " +
            "AND t.ano <= :dataFim")
    Optional<List<Tecnica>> obterTecnicasDocentePorPeriodo(Integer idDocente, Integer dataInicio, Integer dataFim);

}
