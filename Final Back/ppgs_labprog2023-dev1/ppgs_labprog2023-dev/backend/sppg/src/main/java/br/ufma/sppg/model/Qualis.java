package br.ufma.sppg.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qualis {
    @Id
    @Column(name = "id_qualis")
    Integer idQualis;
    @Column(name = "issn_sigla")
    String issnSigla;
    String titulo;
    @Column(name = "estrato_sucupira")
    String estratoSucupira;
    @Column(name = "estrato_atualizado")
    String estratoAtualizado;
    @Column(name = "ajuste_sbc")
    String ajusteSbc;
    @Column(name = "link_scopus")
    String linkScopus;
    String percentil;
    @Column(name = "data_atualizacao")
    String dataAtualizacao;
    String logs;
    String area;
    String tipo;

    @OneToMany(mappedBy = "qualisRef")
    List<Producao> producoes;

}
