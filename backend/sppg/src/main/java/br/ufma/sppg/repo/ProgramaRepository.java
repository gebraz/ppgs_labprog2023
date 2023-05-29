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

        @Query("SELECT u FROM Programa p JOIN p.docentes u where p.id = :idPg")
        List<Docente> obterDocentes(@Param("idPg") Integer idPg);

        @Query("SELECT o FROM Programa p JOIN p.docentes j JOIN j.producoes o WHERE p.id = :idPg")
        List<Producao> obterallProducoes(@Param("idPg") Integer idPg);

        @Query("SELECT t FROM Programa p JOIN p.docentes k JOIN k.tecnicas t WHERE p.id = :idPg")
        List<Tecnica> obterallTecnicas(@Param("idPg") Integer idPg);
    
        @Query("SELECT s FROM Programa p JOIN p.docentes m JOIN m.orientacoes s WHERE p.id = :idPg")
        List<Orientacao> obterallOrientacoes(@Param("idPg") Integer idPg);

}
