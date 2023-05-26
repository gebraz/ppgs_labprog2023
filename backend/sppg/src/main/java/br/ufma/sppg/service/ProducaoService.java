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
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import jakarta.transaction.Transactional;

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

    //Obter Producoes com base no ppg (programa)


    public List<Producao> obterProducoesPPG(Integer idPrograma, Integer anoInicial, Integer anoFinal) {
        if (anoInicial > anoFinal) {
            // Inverter os valores se o anoInicial for maior que o anoFinal
            Integer temp = anoInicial;
            anoInicial = anoFinal;
            anoFinal = temp;
        }

        Optional<Programa> programaOptional = programaRepository.findById(idPrograma);
        if (programaOptional.isPresent()) {
            Programa programa = programaOptional.get();

            // Verificar se o Programa possui Docentes cadastrados
            List<Docente> docentes = programa.getDocentes();
            if (docentes == null || docentes.isEmpty()) {
                throw new RegrasRunTime("Docente inexistente ou não cadastrado.");
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
                throw new RegrasRunTime("Produções associadas a esse programa não foram encontradas no período inserido.");
            }

            return producoes;
        } else {
            throw new RegrasRunTime("Programa não encontrado ou não existe.");
        }
    }

 /*   public List<Producao> obterProducoesPPG(Integer idPrograma, Integer anoInicial, Integer anoFinal) {

        // Se o usuário informa primeiro o ano maior e depois o menor, faz-se a inversão
        // dos valores:
        if (anoInicial > anoFinal) {
            Integer data = anoFinal;
            anoFinal = anoInicial;
            anoInicial = data;
        }

        Optional<Programa> programa = programaRepository.findById(idPrograma);

        if (programa.isPresent()) {

            // Verificando se o Programa possui Docentes cadastrados
            if (programaRepository.getReferenceById(idPrograma).getDocentes() == null
                    || programaRepository.getReferenceById(idPrograma).getDocentes().isEmpty())
                throw new RegrasRunTime("Docente inexistente ou não cadastrado.");

            ArrayList<Producao> producoes = new ArrayList<>();
            for (Docente docente : programaRepository.getReferenceById(idPrograma).getDocentes()) {
                if ((!docente.getProducoes().isEmpty()) && (docente.getProducoes() != null)) {
                    for (Producao producao : docente.getProducoes()) {
                        if ((producao.getAno() >= anoInicial) && (producao.getAno() <= anoFinal)) {
                            producoes.add(producao);
                        }
                    }
                    if (producoes.isEmpty())
                        throw new RegrasRunTime(
                                "Produções associadas a esse docente não foram encontradas no período inserido");

                    return producoes;
                }
                throw new RegrasRunTime("Docente não possui produções no período inserido");
            }

        }
        throw new RegrasRunTime("Programa não encontrado ou não existe");

    }*/

    //Obter producoes com base no docente

    public List<Producao> obterProducoesDocente(Integer idDocente, Integer anoInicial, Integer anoFinal) {

        // Se o usuário informa primeiro o ano maior e depois o menor, faz-se a inversão
        // dos valores:
        if (anoInicial > anoFinal) {
            Integer data = anoFinal;
            anoFinal = anoInicial;
            anoInicial = data;
        }

        Optional<Docente> docente = docenteRepository.findById(idDocente);
        if (docente.isPresent()) {

            if (docenteRepository.getReferenceById(idDocente).getProducoes() == null
                    || docenteRepository.getReferenceById(idDocente).getProducoes().isEmpty())
                throw new RegrasRunTime("O Docente não possui nenhuma Produção Registrada");

            ArrayList<Producao> producoes = new ArrayList<>();

            for (Producao producao : docenteRepository.getReferenceById(idDocente).getProducoes()) {
                if ((producao.getAno() >= anoInicial) && (producao.getAno() <= anoFinal)) {
                    producoes.add(producao);
                }
            }
            if (producoes.isEmpty())
                throw new RegrasRunTime("Não foram encontradas produções associadas a esse docente.");

            return producoes;
        }
        throw new RegrasRunTime("Docente não encontrado.");
    }

    //Atualizar as estatisticas de producao (integers)
    @Transactional
    public void informarEstatisticasProducao(Integer idProducao, Integer qtdGrad, Integer qtdMestrado, Integer qtdDoutorado) {
        Producao producao = producaoRepository.findById(idProducao)
                .orElseThrow(() -> new RegrasRunTime("Produção não encontrada com o ID: " + idProducao));
        producao.setQtdGrad(qtdGrad);
        producao.setQtdMestrado(qtdMestrado);
        producao.setQtdDoutorado(qtdDoutorado);
        producaoRepository.save(producao);
    }

    //Obter orientacaoAssoaciadaAproducao

    public List<Orientacao> obterOrientacaoProducao(Integer idProducao) {
        Optional<Producao> producao = producaoRepository.findById(idProducao);
        if (producao.isPresent()) {
            if (producaoRepository.getReferenceById(idProducao).getOrientacoes() != null)
                return producaoRepository.getReferenceById(idProducao).getOrientacoes();
        }
        throw new RegrasRunTime("A Producao não existe");
    }
}

