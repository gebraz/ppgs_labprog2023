package br.ufma.sppg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
