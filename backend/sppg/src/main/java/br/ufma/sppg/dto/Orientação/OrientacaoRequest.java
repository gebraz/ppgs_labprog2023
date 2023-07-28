package br.ufma.sppg.dto.Orientação;

import lombok.Data;

@Data
public class OrientacaoRequest {
    String tipo;
    String discente;
    String titulo;
    Integer ano;
    String modalidade;
    String instituicao;
    String curso;
    String status;
}
