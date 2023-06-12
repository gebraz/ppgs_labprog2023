package br.ufma.sppg.dto;

import java.util.List;

import br.ufma.sppg.model.Producao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndiceQualisDTO {
    private Indice indice;
    private List<Producao> producoes;
}
