package br.ufma.sppg.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name="data_atualizacao")
    Date dataAtualizacao;
    
    @ManyToMany(mappedBy = "docentes")
    List<Programa> programas;

    @ManyToMany()
    @JoinTable(name = "docente_producao", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_producao"))
    List<Producao> producoes;

    @ManyToMany()
    @JoinTable(name = "docente_tecnica", joinColumns = @JoinColumn(name = "id_docente"), inverseJoinColumns = @JoinColumn(name = "id_tecnica"))
    List<Tecnica> tecnicas;

    @OneToMany(mappedBy = "orientador")
    List<Orientacao> orientacoes;

}
