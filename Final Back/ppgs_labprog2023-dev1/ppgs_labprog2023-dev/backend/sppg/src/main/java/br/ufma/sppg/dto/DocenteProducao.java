package br.ufma.sppg.dto;

import java.util.List;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DocenteProducao {
    Docente docente;
    List<Producao> producoes;
    List<Integer> qualis;
}