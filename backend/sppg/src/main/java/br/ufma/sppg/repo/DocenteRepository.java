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

public interface DocenteRepository 
    extends JpaRepository<Docente, Integer>{

        
    @Query("SELECT p FROM Docente d JOIN d.orientacoes p where d.id = :idPg")
    List<Orientacao> obterOrientacoes(@Param("idPg") Integer idPg);

    @Query("SELECT o FROM Docente d JOIN d.producoes o where d.id = :idPg")
    List<Producao> obterProducoes(@Param("idPg") Integer idPg);

    @Query("SELECT t FROM Docente d JOIN d.tecnicas t where d.id = :idPg")
    List<Tecnica> obterTecnicas(@Param("idPg") Integer idPg);
    
}
