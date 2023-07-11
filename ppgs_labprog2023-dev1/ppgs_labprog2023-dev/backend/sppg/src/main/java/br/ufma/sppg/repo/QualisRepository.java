package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufma.sppg.model.Qualis;

public interface QualisRepository 
    extends JpaRepository<Qualis, Integer> {

    Qualis findByIssnSigla(String issnSigla);
    List<Qualis> findByTituloContaining(String sigla);    
    List<Qualis> findByTipo(String tipo);
    
}
