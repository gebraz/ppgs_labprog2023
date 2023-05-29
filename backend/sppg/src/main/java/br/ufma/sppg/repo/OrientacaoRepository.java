package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Orientacao;

public interface OrientacaoRepository 
    extends JpaRepository<Orientacao, Integer>{

    
}
