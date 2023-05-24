package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;

public interface ProgramaRepository 
    extends JpaRepository<Programa, Integer> {
        List<Programa> findAllByNome(String nomePg);

        @Query("SELECT p FROM Programa p JOIN Docente o where o.id = :idPg")
        List<Docente> obterDocentes(@Param("idPg") Integer idPg);

        @Query("SELECT p FROM Programa p JOIN Docente d JOIN Producao o WHERE o.id = :idPg")
        List<Producao> obterallProducoes(@Param("idPg") Integer idPg);

        @Query("SELECT p FROM Programa p JOIN Docente d JOIN Tecnica o WHERE o.id = :idPg")
        List<Tecnica> obterallTecnicas(@Param("idPg") Integer idPg);
    
        @Query("SELECT p FROM Programa p JOIN Docente d JOIN Orientacao o WHERE o.id = :idPg")
        List<Orientacao> obterallOrientacoes(@Param("idPg") Integer idPg);

}
