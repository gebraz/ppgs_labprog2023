package br.ufma.sppg.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import jakarta.persistence.JoinColumn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "docente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    Integer id;

    @Column(name = "id_lattes")
    String lattes;

    @Column(name = "nome")
    String nome;

    // Relacionamentos
    @Temporal(TemporalType.DATE)
    @Column(name = "data_atualizacao")
    Date dataAtualizacao;

    @JsonIgnore
    @ManyToMany(mappedBy = "docentes")
    List<Programa> programas;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "docente_producao", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_producao"))
    List<Producao> producoes;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "docente_tecnica", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_tecnica"))
    List<Tecnica> tecnicas;

    @JsonIgnore
    @OneToMany(mappedBy = "orientador")
    List<Orientacao> orientacoes;

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = new Date();
    }

    public void adicionarProducao(Producao a) {
        if (producoes == null)
            producoes = new ArrayList<>();
        producoes.add(a);
    }

    public void adicionarOrientacao(Orientacao a) {
        if (orientacoes == null)
            orientacoes = new ArrayList<>();
        orientacoes.add(a);
    }

    public void adicionarTecnica(Tecnica a) {
        if (tecnicas == null)
            tecnicas = new ArrayList<>();
        tecnicas.add(a);
    }

}
