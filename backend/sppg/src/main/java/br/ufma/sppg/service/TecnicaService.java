package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import jakarta.transaction.Transactional;

@Service
public class TecnicaService {

  @Autowired
  TecnicaRepository tecnicaRepo;

  public Tecnica salvarTecnica(Tecnica tecnica) {
    return tecnicaRepo.save(tecnica);
  }

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

  // Obter todas as Orientações de uma téncnica
  public ArrayList<Orientacao> obterOrientacoesTecnica(Integer idTecnica) {
    ArrayList<Orientacao> orientacoes = new ArrayList<>();

    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {

      for (Orientacao orientacao : tecnica.get().getOrientacoes()) {
        orientacoes.add(orientacao);
      }

      return orientacoes;
    }

    throw new RegrasRunTime("A técnica informada não existe");
  }

}
