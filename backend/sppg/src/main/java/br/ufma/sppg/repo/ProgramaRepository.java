package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.model.*;

public interface ProgramaRepository
        extends JpaRepository<Programa, Integer> {
    List<Programa> findAllByNome(String nomePPG);

public interface ProgramaRepository 
    extends JpaRepository<Programa, Integer> {
        List<Programa> findAllByNome(String nomePg);

//         @Query("SELECT u FROM Programa p JOIN p.docentes u where p.id = :idPg")
//         List<Docente> obterDocentes(@Param("idPg") Integer idPg);

//         @Query("SELECT o FROM Programa p JOIN p.docentes j JOIN j.producoes o WHERE p.id = :idPg")
//         List<Producao> obterallProducoes(@Param("idPg") Integer idPg);

//         @Query("SELECT t FROM Programa p JOIN p.docentes k JOIN k.tecnicas t WHERE p.id = :idPg")
//         List<Tecnica> obterallTecnicas(@Param("idPg") Integer idPg);
    
//         @Query("SELECT s FROM Programa p JOIN p.docentes m JOIN m.orientacoes s WHERE p.id = :idPg")
//         List<Orientacao> obterallOrientacoes(@Param("idPg") Integer idPg);
// >>>>>>> db8d3b0bc52a8c710ff9b9dee4a8f3115328839b

    @Query("select p.docentes from Programa p where p.id = :idPPG")
    List<Docente> obterDocentes(@Param("idPPG") Integer idPPG);
}
