package br.ufma.sppg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.model.*;

public interface ProgramaRepository 
    extends JpaRepository<Programa, Integer> {
        List<Programa> findAllByNome(String nomePPG);

        @Query("select p.docentes from Programa p where p.id = :idPPG")
        List<Docente> obterDocentes(@Param("idPPG") Integer idPPG);
}
