package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class OrientacaoService {

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;

    // TODO: Validar entrada de dados
    public Optional<List<Orientacao>> obterOrientacoesComTecnicaPorPeriodo(Integer idDocente, Integer anoInicio,
            Integer anoFim) {
        return orientacaoRepository.obterOrientacoesComTecnicaPorPeriodo(idDocente, anoInicio, anoFim);
    }

    // TODO: Validar entrada de dados
    public Optional<List<Orientacao>> obterOrientacoesComProducaoPorPeriodo(Integer idDocente, Integer anoInicio,
            Integer anoFim) {
        return orientacaoRepository.obterOrientacoesComProducaoPorPeriodo(idDocente, anoInicio, anoFim);
    }

    // TODO: Validar o período informado
    public List<Orientacao> obterOrientacaoPPG(Integer id, Integer anoIni, Integer anoFim) {
        validarPeriodo(anoIni, anoFim);
        validarOrientacoesPpg(id, anoIni, anoFim);
        List<Orientacao> orientacoes = orientacaoRepository.findByPPG(id, anoIni, anoFim).get();

        return orientacoes;
    }

    // TODO: Validar o período informado
    public List<Orientacao> obterOrientacaoDocente(Integer id, Integer anoIni, Integer anoFim) {
        validarPeriodo(anoIni, anoFim);
        validarOrientacoesDoc(id, anoIni, anoFim);
        List<Orientacao> orientacoes = orientacaoRepository.findByDocente(id, anoIni, anoFim).get();

        return orientacoes;
    }

    @Transactional
    public Orientacao associarOrientacaoProducao(Integer idOri, Integer idProd) {
        validarOriProd(idOri, idProd);

        Orientacao orientacao = orientacaoRepository.findById(idOri).get();
        Producao prod = producaoRepository.findById(idProd).get();

        if (orientacao.getProducoes() == null) {
            List<Producao> producoes = new ArrayList<>();
            orientacao.setProducoes(producoes);
        }

        if (!orientacao.getProducoes().contains(prod)) {
            orientacao.getProducoes().add(prod); 
        } else {
            throw new ServicoRuntimeException("Produção já associada.");
        }
        
        return orientacaoRepository.save(orientacao);
    }

    @Transactional
    public Orientacao associarOrientacaoTecnica(Integer idOri, Integer idTec) {
        validarOriTec(idOri, idTec);

        Orientacao orientacao = orientacaoRepository.findById(idOri).get();
        Tecnica tec = tecnicaRepository.findById(idTec).get();

        if (orientacao.getTecnicas() == null) {
            List<Tecnica> tecnicas = new ArrayList<>();
            orientacao.setTecnicas(tecnicas);
        }
            
        if (!orientacao.getTecnicas().contains(tec)) {
            orientacao.getTecnicas().add(tec);
        } else {
            throw new ServicoRuntimeException("Técnica já associada.");
        }

        return orientacaoRepository.save(orientacao);
    }

    // TODO: Validar o período informado
    private void validarOrientacoesPpg(Integer idPrograma, Integer anoIni, Integer anoFim) {

        Optional<Programa> programa = programaRepository.findById(idPrograma);

        Optional<List<Orientacao>> orientacoes = orientacaoRepository.findByPPG(idPrograma, anoIni, anoFim);

        if (programa.isEmpty())
            throw new RuntimeException("Não foram encontrados programas com este Id.");
        if (orientacoes.isEmpty())
            throw new RuntimeException("Não foram encontradas orientações para este docente.");
    }

    // TODO: Validar o período informado
    private void validarOrientacoesDoc(Integer idDocente, Integer anoIni, Integer anoFim) {

        Optional<Docente> docente = docenteRepository.findById(idDocente);

        Optional<List<Orientacao>> orientacoes = orientacaoRepository.findByDocente(idDocente, anoIni, anoFim);

        if (docente.isEmpty())
            throw new RuntimeException("Não foram encontrados  programas com este Id.");
        if (orientacoes.isEmpty())
            throw new RuntimeException("Não foram encontradas orientações para este docente.");
    }

    private void validarOriProd(Integer idOri, Integer idProd) {

        Optional<Producao> prod = producaoRepository.findById(idProd);

        Optional<Orientacao> orientacao = orientacaoRepository.findById(idOri);

        if (prod.isEmpty())
            throw new RuntimeException("Não foram existe produção.");
        if (orientacao.isEmpty())
            throw new RuntimeException("Não foram existe orientação.");
    }

    private void validarOriTec(Integer idOri, Integer idTec) {

        Optional<Tecnica> tec = tecnicaRepository.findById(idTec);

        Optional<Orientacao> orientacao = orientacaoRepository.findById(idOri);

        if (tec.isEmpty())
            throw new RuntimeException("Não foram existe tecnica.");
        if (orientacao.isEmpty())
            throw new RuntimeException("Não foram existe orientação.");
    }

    private void validarPeriodo(Integer anoInicio, Integer anoFim) {
        if (anoInicio > anoFim) {
            throw new ServicoRuntimeException("Ano inicial maior que ano fim.");
        }
    }
}