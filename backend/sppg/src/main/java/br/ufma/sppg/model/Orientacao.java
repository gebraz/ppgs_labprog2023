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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "orientacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orientacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_orientacao")
    Integer id;

    String tipo;
    String discente;
    String titulo;
    Integer ano;
    String modalidade;
    String instituicao;
    String curso;
    String status;

    @ManyToOne
    @JoinColumn(name="id_docente")
    Docente orientador;

    @ManyToMany
    @JoinTable(
        name="producao_orientacao",
        joinColumns = @JoinColumn(name="id_orientacao"),
        inverseJoinColumns = @JoinColumn(name="id_producao")
    )
    List<Producao> producoes;

    @ManyToMany
    @JoinTable(
        name="tecnica_orientacao",
        joinColumns = @JoinColumn(name="id_orientacao"),
        inverseJoinColumns = @JoinColumn(name="id_tecnica")
    )
    List<Tecnica> tecnicas;


}
