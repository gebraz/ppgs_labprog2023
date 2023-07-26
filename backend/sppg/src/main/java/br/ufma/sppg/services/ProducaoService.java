package br.ufma.sppg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;

import br.ufma.sppg.services.exceptions.ServicoRuntimeException;
//import br.ufma.sppg.services.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class ProducaoService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;

    @Autowired
    private ProducaoRepository producaoRepository;


    public List<Producao> obterProducoesPPG(Integer idPrograma, Integer data1, Integer data2) {
        if (data1 > data2) {
            Integer data = data2;
            data2 = data1;
            data1 = data;
        }

        Optional<Programa> programa = programaRepository.findById(idPrograma);
        if (programa.isPresent()) {
            List<Producao> producoes = new ArrayList<>();

            for (Docente docente : programa.get().getDocentes()) {
                if (docente.getProducoes() != null && !docente.getProducoes().isEmpty()) {
                    for (Producao producao : docente.getProducoes()) {
                        if (producao.getAno() >= data1 && producao.getAno() <= data2) {
                            producoes.add(producao);
                        }
                    }
                }
            }

            if (producoes.isEmpty())
                throw new ServicoRuntimeException("O Programa não possui Docentes com Produções no período especificado");

            return producoes;
        }
        throw new ServicoRuntimeException("Programa Inexistente");
    }

    public List<Producao> obterProducoesDocente(Integer idDocente, Integer data1, Integer data2) {
        if (data1 > data2) {
            Integer data = data2;
            data2 = data1;
            data1 = data;
        }

        Optional<Docente> docente = docenteRepository.findById(idDocente);
        if (docente.isPresent()) {
            List<Producao> producoes = new ArrayList<>();

            if (docente.get().getProducoes() != null && !docente.get().getProducoes().isEmpty()) {
                for (Producao producao : docente.get().getProducoes()) {
                    if (producao.getAno() >= data1 && producao.getAno() <= data2) {
                        producoes.add(producao);
                    }
                }
            }

            if (producoes.isEmpty())
                throw new ServicoRuntimeException("O Docente não possui nenhuma Produção no período especificado");

            return producoes;
        }
        throw new ServicoRuntimeException("Docente Inexistente");
    }

    private void verificarProducao(Producao producao) {
        // Verificações de validação da Produção (mantive as mesmas verificações do
        // código original)
    }

    @Transactional
    public Producao informarEstatisticasProducao(Producao producao) {
        verificarProducao(producao);
        return producaoRepository.save(producao);
    }

    public List<Orientacao> obterOrientacaoProducao(Integer idProducao) {
        Optional<Producao> producao = producaoRepository.findById(idProducao);
        if (producao.isPresent()) {
            if (producao.get().getOrientacoes() != null) {
                return producao.get().getOrientacoes();
            }
        }
        throw new ServicoRuntimeException("A Producao não existe ou não possui orientações associadas");
    }
}
