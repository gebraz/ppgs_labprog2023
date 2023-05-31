package br.ufma.sppg.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QualisSummaryDTO {
    private Integer prod;
    private Integer tec;
    private Integer ori;
}
