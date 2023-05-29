package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica, Long> {
    Optional<Tecnica> findById(Integer idTecnica);

    Boolean existsById(Integer idTecnica);

    @Query("SELECT t FROM Tecnica t join Docente d where d.id = :docenteId and t.ano >= :anoInicio and t.ano <= :anoFim")
    List<Tecnica> obterTecnicasDocente(Integer docenteId, Integer anoInicio, Integer anoFim);

    @Query("SELECT t FROM Tecnica t join Docente d join Programa p where p.id = :programaId and t.ano >= :anoInicio and t.ano <= :anoFim")
    List<Tecnica> obterTecnicasPPG(Integer programaId, Integer anoInicio, Integer anoFim);
   
    @Query("SELECT o FROM Orientacao o join Tecnica t where t.id = :tecnicaId and o.ano >= :anoInicio and o.ano <= :anoFim")
    List<Orientacao> obterOrientacoesTecnica(Integer tecnicaId, Integer anoInicio, Integer anoFim);

}
