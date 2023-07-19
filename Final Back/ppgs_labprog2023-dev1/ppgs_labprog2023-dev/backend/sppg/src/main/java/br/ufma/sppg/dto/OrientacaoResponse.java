package br.ufma.sppg.dto;

import java.util.List;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import lombok.Data;

@Data
public class OrientacaoResponse {
    Integer id;
    String tipo;
    String discente;
    String titulo;
    Integer ano;
    String modalidade;
    String instituicao;
    String curso;
    String status;
    Docente orientador;
    List<Producao> producoes;
    List<Tecnica> tecnicas;


    public OrientacaoResponse(Orientacao orientacao) {
        this.id = orientacao.getId();
        this.tipo = orientacao.getTipo();
        this.discente = orientacao.getDiscente();
        this.titulo = orientacao.getTitulo();
        this.ano = orientacao.getAno();
        this.modalidade = orientacao.getModalidade();
        this.instituicao = orientacao.getInstituicao();
        this.curso = orientacao.getCurso();
        this.status = orientacao.getStatus();
        this.orientador=orientacao.getOrientador();
        this.producoes=orientacao.getProducoes();
        this.tecnicas=orientacao.getTecnicas();
    }
}
