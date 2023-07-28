package br.ufma.sppg.dto;

import java.util.List;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DocenteProducaoDTO {
    Docente docente;
    List<Producao> producoes;
    List<Integer> qualis;
}