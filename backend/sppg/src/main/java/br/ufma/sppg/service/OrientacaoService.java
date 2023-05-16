package br.ufma.sppg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.dto.CriarOrientacaoDTO;

@Service
public class OrientacaoService {

    @Autowired
    OrientacaoRepository orientacaoRepository;

    @Autowired
    ProgramaRepository programaRepository;

    @Autowired
    DocenteRepository docenteRepository;

    public List<Orientacao> obterTodasOrientacoes() {
        return orientacaoRepository.findAll();
    }

    public List<Orientacao> obterOrientacoesPPG(Integer idPrograma) {

        validarOrientacoes(idPrograma);
        List<Orientacao> orientacoes = orientacaoRepository.findByPPG(idPrograma).get();

        return orientacoes;
    }

    public Orientacao criarOrientacao(CriarOrientacaoDTO dto) {

        validarDocente(dto.getIdDocente());
        Docente docente = docenteRepository.findById(dto.getIdDocente()).get();

        // não pode criar orientacao com o mesmo titulo e mesmo orientador
        // TODO: validar orientacao

        Orientacao novaOrientacao = Orientacao.builder().orientador(docente).titulo(dto.getTitulo()).build();

        return orientacaoRepository.save(novaOrientacao);
    }

    private void validarOrientacoes(Integer idPrograma) {

        Optional<Programa> programa = programaRepository.findById(idPrograma);

        Optional<List<Orientacao>> orientacoes = orientacaoRepository.findByPPG(idPrograma);

        if (programa.isEmpty())
            throw new RuntimeException("Não foram encontrados  programas com este Id.");
        if (orientacoes.isEmpty())
            throw new RuntimeException("Não foram encontradas orientações para este docente.");

    }

    private void validarDocente(Integer idDocente) {
        Optional<Docente> docente = docenteRepository.findById(idDocente);
        if (docente.isEmpty())
            throw new RuntimeException("Não foram encontrados docentes com este Id.");
    }
}
