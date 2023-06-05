package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Orientacao;

public interface OrientacaoRepository extends JpaRepository<Orientacao, Integer> {
    List<Orientacao> findAllById(Integer id);

    @Query("SELECT o FROM Orientacao o JOIN o.orientador d JOIN d.programas p where p.id = :idPrograma AND o.ano >= :anoInicio AND o.ano<= :anoFim")
    Optional<List<Orientacao>> findByPPG(Integer idPrograma, Integer anoInicio, Integer anoFim);

    @Query("SELECT o FROM Orientacao o JOIN o.orientador d WHERE d.id = :idDocente AND o.ano >= :anoInicio AND o.ano<= :anoFim")
    Optional<List<Orientacao>> findByDocente(Integer idDocente, Integer anoInicio, Integer anoFim);

    Orientacao findByTipoAndDiscenteAndTitulo(String tipo, String discente, String titulo);
}
