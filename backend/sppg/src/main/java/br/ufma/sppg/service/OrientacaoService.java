package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.OrientacaoResponse;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.repo.OrientacaoRepository;

@Service
public class OrientacaoService  {

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    //TODO falta colocar o ID programa, intervalo inicio e fim    
    public ArrayList<OrientacaoResponse> obterOrientacaoPPG(Integer id) {

        var orientacoes = orientacaoRepository.findAllById(id);
        var responses = new ArrayList<OrientacaoResponse>();
        for (var orientacao : orientacoes) {
            var response = new OrientacaoResponse(orientacao);
            responses.add(response);
        }
        return responses;
    }

    //TODO falta colocar intervalo inicio e fim
    public ArrayList<OrientacaoResponse> obterOrientacaoDocentes(Integer idDocente) {
        var orientacoes = orientacaoRepository.findAllById(idDocente);
        var responses = new ArrayList<OrientacaoResponse>();
        for (var orientacao : orientacoes) {
            var response = new OrientacaoResponse(orientacao);
            responses.add(response);
        }
        return responses;

    }

    //TODO falta fazer
    public String associarOrientacaoProducao(Integer idOrientacao, Integer idProducao) {
        var orientacao = orientacaoRepository.findAllById(idOrientacao);
        throw new UnsupportedOperationException("Unimplemented method 'obterOrientacaoTecnica'");

    }

    //TODO falta fazer
    public String obterOrientacaoTecnica() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obterOrientacaoTecnica'");
    }

}
