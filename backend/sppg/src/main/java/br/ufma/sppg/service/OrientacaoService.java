package br.ufma.sppg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;

@Service
public class OrientacaoService {

    @Autowired
    OrientacaoRepository orientacaoRepository;

    @Autowired
    ProgramaRepository programaRepository;

    public List<Orientacao> obterTodasOrientacoes() {
        return orientacaoRepository.findAll();
    }

    // public List<Orientacao> obterOrientacoesPPG(Integer idPrograma) {

    // validarIdPrograma(idPrograma);
    // Optional<Programa> programa = programaRepository.findById(idPrograma);

    // validarOrientacoes(programa);
    // Optional<List<Orientacao>> orientacoes =
    // orientacaoRepository.findByPrograma(programa.get());

    // return orientacoes.get();
    // }

    // private void validarIdPrograma(Integer idPrograma) {
    // Optional<Programa> programa = programaRepository.findById(idPrograma);

    // if (programa.isEmpty())
    // throw new RuntimeException("Não foi encontrado um programa.");
    // }

    // private void validarOrientacoes(Optional<Programa> programa) {

    // Optional<List<Orientacao>> orientacoes =
    // orientacaoRepository.findByPrograma(programa.get());

    // if (orientacoes.isEmpty())
    // throw new RuntimeException("Não foram encontradas orientacoes para este
    // programa.");
    // }

}
