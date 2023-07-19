package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.dto.DocenteProducao;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;

public interface ProducaoRepository extends JpaRepository<Producao,Integer> {
    
    Producao findByTituloAndNomeLocal(String titulo, String nomeLocal);
    Producao findByTitulo(String titulo);

    
    @Query("SELECT p.ano FROM Producao p")
    List<Integer> findByAnoQualis();
    
    @Query("SELECT p.ano, p.qualis, COUNT(p) " +
           "FROM Producao p " +
           "WHERE p.ano >= :anoIni AND p.ano <= :anoFim " +
           "AND p.qualis IN ('A1', 'A2', 'A3', 'A4') " +
           "GROUP BY p.ano, p.qualis " +
           "ORDER BY p.ano")
    List<Object[]> obterContagemQualisPorAno(@Param("anoIni") Integer anoIni, @Param("anoFim") Integer anoFim);
}