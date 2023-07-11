package br.ufma.sppg.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QualisStatsDTO {
    Integer producoes;
    Integer orientacoes;
    Integer tecnicas;
}
