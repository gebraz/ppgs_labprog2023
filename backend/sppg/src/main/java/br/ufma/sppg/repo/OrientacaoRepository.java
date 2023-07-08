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

        @Query("SELECT o FROM Orientacao o " +
                        "JOIN Docente d " +
                        "JOIN Tecnica t " +
                        "WHERE d.id = :idDocente " +
                        "AND o.ano >= :anoInicio " +
                        "AND o.ano <= :anoFim " +
                        "AND t.ano >= :anoInicio " +
                        "AND t.ano <= :anoFim")
        Optional<List<Orientacao>> obterOrientacoesComTecnicaPorPeriodo(Integer idDocente, Integer anoInicio,
                        Integer anoFim);

        @Query("SELECT o FROM Orientacao o " +
                        "JOIN Docente d " +
                        "JOIN Producao p " +
                        "WHERE d.id = :idDocente " +
                        "AND o.ano >= :anoInicio " +
                        "AND o.ano <= :anoFim " +
                        "AND p.ano >= :anoInicio " +
                        "AND p.ano <= :anoFim")
        Optional<List<Orientacao>> obterOrientacoesComProducaoPorPeriodo(Integer idDocente, Integer anoInicio,
                        Integer anoFim);

        Orientacao findByTipoAndDiscenteAndTitulo(String tipo, String discente, String titulo);
}
