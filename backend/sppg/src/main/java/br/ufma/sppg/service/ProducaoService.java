package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.DocenteProducaoDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;


/*
 * A QUANTIDADE DE A1,A2,... POR ANO
 * SORT()
 */
@Service
public class ProducaoService {
    
    @Autowired
    ProducaoRepository producaoRepository;

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    ProgramaRepository programaRepository;

    @Autowired
    OrientacaoRepository orientacaoRepository;

    // TODO: checar tempo de processamento
    public List<Producao> obterProducoesPPG(Integer idPrograma, Integer anoInicial, Integer anoFinal) {
        if (anoInicial >= anoFinal) {
            Integer temp = anoFinal;
            anoFinal = anoInicial;
            anoInicial = temp;
        }

        Optional<Programa> programaOptional = programaRepository.findById(idPrograma);
        if (programaOptional.isEmpty()) {
            throw new ServicoRuntimeException("Programa Inexistente");
        }

        Programa programa = programaOptional.get();

        List<Docente> docentes = programa.getDocentes();
        if (docentes == null || docentes.isEmpty()) {
            throw new ServicoRuntimeException("O programa não possui Docentes cadastrados");
        }

        List<Producao> producoes = new ArrayList<>();

        for (Docente docente : docentes) {
            List<Producao> producoesDocente = docente.getProducoes();
            if (producoesDocente != null && !producoesDocente.isEmpty()) {
                for (Producao producao : producoesDocente) {
                    if (producao.getAno() >= anoInicial && producao.getAno() <= anoFinal) {
                        producoes.add(producao);
                    }
                }
            }
        }

        if (producoes.isEmpty()) {
            throw new ServicoRuntimeException("O Programa não possui Docentes com Produções no período especificado");
        }

        return producoes;
    }

    public List<Producao> obterProducoesDocente(Integer idDocente, Integer anoInicial, Integer anoFinal) {
        if (anoInicial >= anoFinal) {
            Integer temp = anoFinal;
            anoFinal = anoInicial;
            anoInicial = temp;
        }

        Optional<Docente> docenteOptional = docenteRepository.findById(idDocente);
        if (docenteOptional.isEmpty()) {
            throw new ServicoRuntimeException("Docente Inexistente");
        }

        Docente docente = docenteOptional.get();
        List<Producao> producoesDocente = docente.getProducoes();

        if (producoesDocente == null || producoesDocente.isEmpty()) {
            throw new ServicoRuntimeException("O Docente não possui nenhuma Produção Registrada");
        }

        List<Producao> producoes = new ArrayList<>();

        for (Producao producao : producoesDocente) {
            if (producao.getAno() >= anoInicial && producao.getAno() <= anoFinal) {
                producoes.add(producao);
            }
        }

        if (producoes.isEmpty()) {
            throw new ServicoRuntimeException("O Docente não possui nenhuma Produção no período especificado");
        }

        return producoes;
    }
    
    private void verificarProducao(Producao producao) {
        if (producao == null) {
            throw new ServicoRuntimeException("Produção deve ser informada");
        }
        // Verificar as outras condições...
    }

    @Transactional
    public Producao informarEstatisticasProducao(Producao producao) {
        verificarProducao(producao);
        return producaoRepository.save(producao);
    }

    public List<Orientacao> obterOrientacaoProducao(Integer idProducao) {
        Optional<Producao> producaoOptional = producaoRepository.findById(idProducao);
        if (producaoOptional.isEmpty()) {
            throw new ServicoRuntimeException("A Producao não existe");
        }

        Producao producao = producaoOptional.get();
        List<Orientacao> orientacoes = producao.getOrientacoes();

        if (orientacoes == null) {
            throw new ServicoRuntimeException("A Producao não possui orientações registradas");
        }

        return orientacoes;
    }

    public List<DocenteProducaoDTO> obterProducoesDocentes(Integer anoInicial, Integer anoFinal) {
        final Integer menorData = anoInicial;
        final Integer maiorData = anoFinal;
    
        List<DocenteProducaoDTO> producoesDocentes = docenteRepository.findAll().stream()
            .filter(docente -> {
                List<Producao> producoesDocente = docenteRepository.getReferenceById(docente.getId()).getProducoes();
                return producoesDocente != null && !producoesDocente.isEmpty();
            })
            .map(docente -> {
                List<Producao> producoesDocente = docenteRepository.getReferenceById(docente.getId()).getProducoes();
                List<Producao> producoesFiltradas = producoesDocente.stream()
                    .filter(producao -> {
                        Integer ano = producao.getAno();
                        return ano >= menorData && ano <= maiorData;
                    })
                    .collect(Collectors.toList());
    
                return DocenteProducaoDTO.builder()
                    .docente(docente)
                    .producoes(producoesFiltradas)
                    .build();
            })
            .collect(Collectors.toList());
    
        if (producoesDocentes.isEmpty()) {
            throw new ServicoRuntimeException("O Docente não possui nenhuma Produção Registrada");
        }
    
        return producoesDocentes;
    }
}
