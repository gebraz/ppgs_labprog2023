package br.ufma.sppg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufma.sppg.model.Docente;

//Tipo do repository e Tipo do ID
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    List<Docente> findByNome(String nome);

    @Query("SELECT d FROM Docente d JOIN d.programas p where p.id = :idPrograma")
    List<Docente> findByPPG(Integer idPrograma);
}
