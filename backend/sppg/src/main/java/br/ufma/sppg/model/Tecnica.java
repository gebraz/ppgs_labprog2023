package br.ufma.sppg.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.AllArgsConstructor;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tecnica")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tecnica {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tecnica")
    Integer id;

    @Column(name = "tipo")
    String tipo;

    @Column(name="titulo")
    String titulo;

    @Column(name="ano")
    Integer ano;

    @Column(name = "financiadora")
    String financiadora;

    @Column(name="outras_informacoes")
    String outrasInformacoes;

    @Column(name="qtd_grad")
    Integer qtdGrad;

    @Column(name="qtd_mestrado")
    Integer qtdMestrado;

    @Column(name="qtd_doutorado")
    Integer qtdDoutorado;

    @ManyToMany
    @JoinTable(name = "tecnica_orientacao",//nome da tabela
    joinColumns = @JoinColumn(name = "id_orientacao"),// chave de destino
    inverseJoinColumns = @JoinColumn(name = "id_tecnica"))// chave de origem
    Set<Orientacao> orientacoes;


    @ManyToMany
    @JoinTable(name = "docente_tecnica",//nome da tabela
    joinColumns = @JoinColumn(name = "id_docente"),// chave de destino
    inverseJoinColumns = @JoinColumn(name = "id_tecnica"))// chave de origem
    Set<Docente> docentes; 
    
}
