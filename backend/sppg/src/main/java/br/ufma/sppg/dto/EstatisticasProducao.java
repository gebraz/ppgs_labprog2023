package br.ufma.sppg.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class EstatisticasProducao {
    Integer qtd_grad;
    Integer qtd_mestrado;
    Integer qtd_doutorado;
}
