package br.ufma.sppg.services;

import java.util.ArrayList;

import br.ufma.sppg.Dto.Orientação.OrientacaoRequest;
import br.ufma.sppg.Dto.Orientação.OrientacaoResponse;
import br.ufma.sppg.model.Orientacao;

public interface IOrientacao {
    
    public ArrayList<OrientacaoResponse> obterOrientacaoPPG(Integer id);
    public ArrayList<OrientacaoResponse> obterOrientacaoDocentes(Integer idDocente);
    public String associarOrientacaoProducao(Integer idOrientacao,Integer idProducao);
    public String obterOrientacaoTecnica();

}
