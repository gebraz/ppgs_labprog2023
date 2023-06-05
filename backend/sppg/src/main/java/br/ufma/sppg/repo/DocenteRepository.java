package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.model.*;

public interface DocenteRepository 
    extends JpaRepository<Docente, Integer>{

    List<Docente> findByNome(String nome);
    
    Optional<Docente> findById(Integer idDocente);

    boolean existsById(Integer idDocente);

    @Query("select p from Docente d join d.producoes p where (d.id = :idDocente and p.ano >= :anoIni and p.ano <= :anoFin)")
    List<Producao> obterProducoes(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("select o from Docente d join d.orientacoes o where (d.id = :idDocente and o.ano >= :anoIni and o.ano <= :anoFin)")
    List<Orientacao> obterOrientacoes(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("select t from Docente d join d.tecnicas t where (d.id = :idDocente and t.ano >= :anoIni and t.ano <= :anoFin)")
    List<Tecnica> obterTecnicas(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);
}
