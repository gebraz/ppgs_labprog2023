package br.ufma.sppg.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufma.sppg.Dto.OrientacaoResponse;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

public class OrientacaoService implements IOrientacao {

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;


    @Override
    public ArrayList<OrientacaoResponse> obterOrientacaoPPG(Integer id,Integer anoInicio, Integer anoFim){

        var orientacoes = orientacaoRepository.findOrientacaoPPG(id, anoInicio, anoFim);
        var responses = new ArrayList<OrientacaoResponse>();
        for (var orientacao : orientacoes) {
            var response = new OrientacaoResponse(orientacao);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public ArrayList<OrientacaoResponse> obterOrientacaoDocentes(Integer idDocente, Integer anoInicio, Integer anoFim) {
      
        var orientacoes = orientacaoRepository.findDocente(idDocente, anoInicio, anoFim);
        var responses = new ArrayList<OrientacaoResponse>();
        for (var orientacao : orientacoes) {
            var response = new OrientacaoResponse(orientacao);
            responses.add(response);
        }
        return responses;
        
    }

    @Override
    public String associarOrientacaoProducao(Integer idProducao,Integer idOrientacao) {
        var producaoOp =producaoRepository.findById(idProducao);
        var orientacaoOp = orientacaoRepository.findById(idOrientacao);
        if (orientacaoOp.isEmpty()) {
            throw new ServicoRuntimeException("A orientação não foi encontrada.");
        }
    
        if (producaoOp.isEmpty()) {
            throw new ServicoRuntimeException("A produção não foi encontrada.");
        }
        var  orientacao= orientacaoOp.get();
        var producao = producaoOp.get();

        var orientacoes = producao.getOrientacoes();
        orientacoes.add(orientacao);

        var producoes = orientacao.getProducoes();
        producoes.add(producao);

        orientacaoRepository.save(orientacao);
        producaoRepository.save(producao);

        
        return "A orientação foi associada à produção com sucesso.";

       
      
    }

    @Override
    public String associarOrientacaoTecnica(Integer idOrientacao, Integer Idtecnica) {
        var orientacaoOp = orientacaoRepository.findById(idOrientacao);
        var tecnicaOp = tecnicaRepository.findById(Idtecnica);

        if (orientacaoOp == null) {
            throw new ServicoRuntimeException("Orientacao com ID " +  idOrientacao + " não encontrada.");

        }

        if (tecnicaOp == null) {
            throw new ServicoRuntimeException("Nenhuma técnica selecionada para associação.");
        }
      
       
        var orientacao = orientacaoOp.get();
        var tecnica = tecnicaOp.get();

        var orientacoes = tecnica.getOrientacoes();
        orientacoes.add(orientacao);

        var tecnicas = orientacao.getTecnicas();
        tecnicas.add(tecnica);

        orientacaoRepository.save(orientacao);
        tecnicaRepository.save(tecnica);

     

        return "Associação concluída com sucesso.";
    }

  
    
}
