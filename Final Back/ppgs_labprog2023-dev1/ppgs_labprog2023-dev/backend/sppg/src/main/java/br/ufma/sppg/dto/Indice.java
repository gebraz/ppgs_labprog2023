package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Indice {
    
    Double indiceRest;
    Double indiceNRest;
    Double indiceGeral;
    Integer totalProd;
    public Indice(Double indiceRest, Double indiceNRest, Double indiceGeral) {
        this.indiceRest = indiceRest;
        this.indiceNRest = indiceNRest;
        this.indiceGeral = indiceGeral;
    }
}
