package br.ufma.sppg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica, Long> {
    Optional<Tecnica> findById(Integer idTecnica);
    Boolean existsById(Integer idTecnica);
    
    @Modifying
    @Transactional
    @Query("delete from Tecnica t where (t.idTecnica = :idTec and t.orientacoes is empty)")
    void remover(@Param("idTec") Integer idTecnica);

    @Transactional
    @Modifying()
    @Query("update Tecnica t set t.qtdGrad = :qtd_Grad where t.id = :id_Tecnica")
    void atualizarQtdGrad(@Param("id_Tecnica") Integer idTecnica, @Param("qtd_Grad") Integer qtdGrad);

    @Transactional
    @Modifying()
    @Query("update Tecnica t set t.qtdMestrado = :qtd_Mestrado where t.id = :id_Tecnica")
    void atualizarQtdMestrado(@Param("id_Tecnica") Integer idTecnica, @Param("qtd_Mestrado") Integer qtdMestrado);

    @Transactional
    @Modifying()
    @Query("update Tecnica t set t.qtdDoutorado = :qtd_Doutorado where t.id = :id_Tecnica")
    void atualizarQtdDoutorado(@Param("id_Tecnica") Integer idTecnica, @Param("qtd_Doutorado") Integer qtdDoutorado);
}
