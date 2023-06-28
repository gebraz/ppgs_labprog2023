package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufma.sppg.dto.OrientacaoResponse;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;

public class OrientacaoService implements IOrientacao {

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

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
    public String obterOrientacaoTecnica() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obterOrientacaoTecnica'");
    }

    // ##############################################################################################################
    public List<Orientacao> obterTodasOrientacoes() {
        return orientacaoRepository.findAll();
    }

    public List<Orientacao> obterOrientacoesPPG(Integer idPrograma, Integer anoInicio, Integer anoFim) {

        validarOrientacoes(idPrograma, anoInicio, anoFim);
        List<Orientacao> orientacoes = orientacaoRepository.findByPPG(idPrograma, anoInicio, anoFim).get();

        return orientacoes;

    }

    public List<Orientacao> obterOrientacoesDocente(Integer idDocente, Integer anoInicio, Integer anoFim) {

        validarOrientacoes(idDocente, anoInicio, anoFim);
        List<Orientacao> orientacoes = orientacaoRepository.findByDocente(idDocente, anoInicio, anoFim).get();

        return orientacoes;
    }

    public Orientacao associarOrientacaoProducao(Integer idProducao, Integer idOrientacao) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(idProducao);
        Optional<Producao> producao = producaoRepository.findById(idOrientacao);
        //
        orientacao.get().getProducoes().add(producao.get());
        return orientacaoRepository.save(orientacao.get());
    }

    public Orientacao associarOrientacaoTecnica(Integer idTecnica, Integer idOrientacao) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(idTecnica);
        Optional<Tecnica> tecnica = tecnicaRepository.findById(idOrientacao);

        orientacao.get().getTecnicas().add(tecnica.get());
        return orientacaoRepository.save(orientacao.get());

    }

    private void validarProducoes(Integer idProducao) {
        Optional<Producao> producao = producaoRepository.findById(idProducao);
        if (producao.isEmpty())
            throw new RuntimeException("Nao foram encontrados Producoes com este Id");
    }

    private void validarOrientacoes(Integer idPrograma, Integer anoInicio, Integer anoFim) {
        // identifica programa
        // dentro da lista de orientacoes é feito um query no BD e busca se existe o
        // programa
        Optional<Programa> programa = programaRepository.findById(idPrograma);
        Optional<List<Orientacao>> orientacoes = orientacaoRepository.findByPPG(idPrograma, anoInicio, anoFim);

        if (programa.isEmpty())
            throw new RuntimeException("Não foram encontrados programas com este Id. ");
        if (orientacoes.isEmpty())
            throw new RuntimeException("Nao foram encontradas orientações para este docente");
    }

    private void validarDocente(Integer idDocente) {
        Optional<Docente> docente = docenteRepository.findById(idDocente);
        if (docente.isEmpty())
            throw new RuntimeException("Não foram encontrados Docentes com este Id");
    }

    private void validarTecnica(Integer idTecnica) {
        Optional<Tecnica> tecnica = tecnicaRepository.findById(idTecnica);
        if (tecnica.isEmpty())
            throw new RuntimeException("Não foram encontrados Tecnicas com este Id");
    }

    private void validarProducao(Integer idProducao) {
        Optional<Producao> producao = producaoRepository.findById(idProducao);
        if (producao.isEmpty())
            throw new RuntimeException("Não foram encontrados Producoes com este Id");
    }

}
