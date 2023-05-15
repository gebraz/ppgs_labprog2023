package br.ufma.sppg.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinTable(
        name="tecnica_orientacao",
        joinColumns = @JoinColumn(name="id_tecnica"),
        inverseJoinColumns = @JoinColumn(name="id_orientacao")
    )
    List<Orientacao> orientacoes;

    @ManyToMany
    @JoinTable(
        name="docente_tecnica",
        joinColumns = @JoinColumn(name="id_tecnica")
    )
     List<Docente> docentes;
}
