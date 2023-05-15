package br.ufma.sppg.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufma.sppg.Dto.Orientação.OrientacaoResponse;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.repo.OrientacaoRepository;

public class OrientacaoService implements IOrientacao {

    @Autowired
    private OrientacaoRepository orientacaoRepository;


    @Override
    public ArrayList<OrientacaoResponse> obterOrientacaoPPG(Integer id) {
       
        var orientacoes = orientacaoRepository.findAllById(id);
        var responses = new ArrayList<OrientacaoResponse>();
        for (var orientacao : orientacoes) {
            var response = new OrientacaoResponse(orientacao);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public ArrayList<OrientacaoResponse> obterOrientacaoDocentes(Integer idDocente) {
        var orientacoes = orientacaoRepository.findAllById(idDocente);
        var responses = new ArrayList<OrientacaoResponse>();
        for (var orientacao : orientacoes) {
            var response = new OrientacaoResponse(orientacao);
            responses.add(response);
        }
        return responses;
        
    }

    @Override
    public String associarOrientacaoProducao(Integer idOrientacao,Integer idProducao){
        var orientacao = orientacaoRepository.findAllById(idOrientacao);
        throw new UnsupportedOperationException("Unimplemented method 'obterOrientacaoTecnica'");
       
      
    }

    @Override
    public String obterOrientacaoTecnica() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obterOrientacaoTecnica'");
    }
    
}
