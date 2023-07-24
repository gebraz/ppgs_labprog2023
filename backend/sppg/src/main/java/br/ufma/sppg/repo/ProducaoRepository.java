package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.model.Producao;

public interface ProducaoRepository 
    extends JpaRepository<Producao,Integer> {
    
        Producao findByTituloAndNomeLocal(String titulo, String nomeLocal);
        Producao findByTitulo(String titulo);

        @Query("SELECT p FROM Producao p " +
                "WHERE p.ano >= :anoIni " +
                "AND p.ano <= :anoFim")
        List<Producao> findProducoesDocentes(@Param("anoIni") Integer anoIni, @Param("anoFim") Integer anoFim);
}
