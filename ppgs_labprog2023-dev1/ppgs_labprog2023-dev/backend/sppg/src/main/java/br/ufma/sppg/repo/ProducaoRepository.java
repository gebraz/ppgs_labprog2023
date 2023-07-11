package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.dto.DocenteProducao;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;

public interface ProducaoRepository extends JpaRepository<Producao,Integer> {
    
    Producao findByTituloAndNomeLocal(String titulo, String nomeLocal);
    Producao findByTitulo(String titulo);

    



}
