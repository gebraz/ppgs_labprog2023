package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepo extends JpaRepository<Tecnica, Integer>{
    @Query("SELECT d.id,d.lattes,d.nome,d.dataAtualizacao FROM Tecnica t join Docente d where t.id = :tecnicaId")
    List<Docente> obterTecnicasDocente(Integer tecnicaId);

    @Query("SELECT qtdGrad,qtdMestrado,qtdDoutorado FROM Tecnica where t.id = :tecnicaId")
    Tecnica informarEstatisticasTecnica(Integer tecnicaId);

}
