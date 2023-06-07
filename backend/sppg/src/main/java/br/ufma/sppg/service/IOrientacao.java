package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;


import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Tecnica;

public interface IOrientacao {
    
    public List<Orientacao> obterOrientacaoPPG(Integer id,Integer anoInicio, Integer anoFim);
    public List<Orientacao> obterOrientacaoDocentes(Integer idDocente, Integer anoInicio, Integer anoFim);
    public String associarOrientacaoProducao(Integer idOrientacao,Integer idProducao);
    public String associarOrientacaoTecnica(Integer idOrientacao, Integer idTecnica);

}
