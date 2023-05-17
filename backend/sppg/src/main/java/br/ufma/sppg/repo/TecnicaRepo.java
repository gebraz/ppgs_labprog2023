package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepo extends JpaRepository<Tecnica, Integer>{
    @Query("SELECT t FROM Tecnica t join Docente d where d.id = :docenteId and t.ano >= :anoInicio and t.ano <= :anoFim")
    List<Tecnica> obterTecnicasDocente(Integer docenteId, Integer anoInicio, Integer anoFim);

    @Query("SELECT t FROM Tecnica t join Docente d join Programa p where p.id = :programaId and t.ano >= :anoInicio and t.ano <= :anoFim")
    List<Tecnica> obterTecnicasPPG(Integer programaId, Integer anoInicio, Integer anoFim);
   
    @Query("SELECT o FROM Orientacao o join Tecnica t where t.id = :tecnicaId and o.ano >= :anoInicio and o.ano <= :anoFim")
    List<Orientacao> obterOrientacoesTecnica(Integer tecnicaId, Integer anoInicio, Integer anoFim);

    @Query("SELECT qtdGrad,qtdMestrado,qtdDoutorado FROM Tecnica where t.id = :tecnicaId")
    Tecnica obterEstatisticasTecnica(Integer tecnicaId); 

    @Query("UPDATE Tecnica t SET t.qtdGrand = :qtdGrand, t.qtdMestrado = :qtdMestrado, t.qtdDoutorado  = :qtdDoutorado FROM Tecnica where t.id = :tecnicaId")
    void associarEstatisticasTecnica(Integer tecnicaId, Integer qtdGrand,Integer qtdMestrado, Integer qtdDoutorado  );
    
}