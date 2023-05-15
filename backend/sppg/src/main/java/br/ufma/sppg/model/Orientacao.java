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
    @Column(name = "id_orientacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="tipo")
    private String tipo;
    
    @Column(name="discente")
    private String discente;

    @Column(name="titulo")
    private String titulo;

    @Column(name="ano")
    private Integer ano;

    @Column(name="modalidade")
    private String modalidade;

    @Column(name="instituicao")
    private String instituicao;

    @Column(name="curso")
    private String curso;

    @Column(name="status")
    private String status;

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
