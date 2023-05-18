package br.ufma.sppg.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import jakarta.transaction.Transactional;

@Service
public class TecnicaService {

  @Autowired
  TecnicaRepository tecnicaRepo;

  @Autowired
  DocenteRepository docenteRepo;

  @Autowired
  ProgramaRepository programaRepo;

  @Autowired
  OrientacaoRepository orientacaoRepo;

  // Salva uma técnica
  public Tecnica salvarTecnica(Tecnica tecnica) {
    return tecnicaRepo.save(tecnica);
  }

  // Atualiza as estatísticas de uma técnica
  @Transactional
  public Tecnica atualizarEstatisticas(Integer idTecnica, Integer qtdGrad, Integer qtdMestrado, Integer qtdDoutorado) {

    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {
      Tecnica tecnicaObj = tecnica.get();

      tecnicaObj.setQtdGrad(qtdGrad);
      tecnicaObj.setQtdMestrado(qtdMestrado);
      tecnicaObj.setQtdDoutorado(qtdDoutorado);

      return tecnicaRepo.save(tecnicaObj);
    }

    throw new RegrasRunTime("A técnica informada não existe!");
  }

  // Retorna todas as orientações de uma téncnica
  public List<Orientacao> obterOrientacoesTecnica(Integer idTecnica) {
    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {
      return tecnica.get().getOrientacoes();
    }

    throw new RegrasRunTime("A técnica informada não existe");
  }

  // Retorna todas as técnicas de uma orientação em um período
  public Optional<List<Tecnica>> obterTecnicasOrientacaoPorPeriodo(Integer idOrientacao, Integer anoInicio,
      Integer anoFim) {
    Optional<Orientacao> orientacao = orientacaoRepo.findById(idOrientacao);

    // verificando se o docente existe
    if (orientacao.isPresent()) {

      if (anoInicio > anoFim) {
        Integer dataAuxiliar = anoInicio;

        anoInicio = anoFim;
        anoInicio = dataAuxiliar;
      }

      return tecnicaRepo.obterTecnicasOrientacaoPorPeriodo(idOrientacao, anoInicio, anoFim);
    }

    throw new RegrasRunTime("O docente informado não existe!");
  }

  // Retorna todas as técnicas de um docente
  public List<Tecnica> obterTecnicasDocente(Integer idDocente) {

    Optional<Docente> docente = docenteRepo.findById(idDocente);

    if (docente.isPresent()) {
      return docente.get().getTecnicas();
    }

    throw new RegrasRunTime("O docente informado não existe");
  }

  // Retorna todos os docentes de uma técnica
  public List<Docente> obterDocentesTecnica(Integer idTecnica) {

    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {
      return tecnica.get().getDocentes();
    }

    throw new RegrasRunTime("A técnica informada não existe");
  }

  // Retorna todas as técnicas de um docente em um período
  public Optional<List<Tecnica>> obterTecnicasDocentePorPeriodo(Integer idDocente, Integer anoInicio,
      Integer anoFim) {
    Optional<Docente> docente = docenteRepo.findById(idDocente);

    // verificando se o docente existe
    if (docente.isPresent()) {

      if (anoInicio > anoFim) {
        Integer dataAuxiliar = anoInicio;

        anoInicio = anoFim;
        anoInicio = dataAuxiliar;
      }

      return tecnicaRepo.obterTecnicasDocentePorPeriodo(idDocente, anoInicio, anoFim);
    }

    throw new RegrasRunTime("O docente informado não existe!");
  }

  // Retorna todas as técnicas de um programa
  public Optional<List<Tecnica>> obterTecnicasPPG(Integer idPrograma) {

    Optional<Programa> programa = programaRepo.findById(idPrograma);

    if (programa.isPresent()) {
      return tecnicaRepo.obterTecnicasPPG(idPrograma);
    }

    throw new RegrasRunTime("O programa informado não existe!");
  }

  // Retorna todas as técnicas de um programa em um período
  public Optional<List<Tecnica>> obterTecnicasPPGPorPeriodo(Integer idPrograma, Integer anoInicio, Integer anoFim) {

    Optional<Programa> programa = programaRepo.findById(idPrograma);

    // verificando se o docente existe
    if (programa.isPresent()) {

      if (anoInicio > anoFim) {
        Integer dataAuxiliar = anoInicio;

        anoInicio = anoFim;
        anoInicio = dataAuxiliar;
      }

      return tecnicaRepo.obterTecnicasPPGPorPeriodo(idPrograma, anoInicio, anoFim);
    }
    throw new RegrasRunTime("O programa informado não existe!");
  }
}
