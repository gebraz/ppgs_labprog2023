package br.ufma.sppg.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@SpringBootTest
public class TecnicaServiceTest {

  @Autowired
  TecnicaService tecnicaService;

  @Autowired
  TecnicaRepository tecnicaRepository;

  @Autowired
  OrientacaoRepository orientacaoRepository;

  @Autowired
  DocenteRepository docenteRepository;

  @Autowired
  ProgramaRepository programaRepository;

  private Docente docenteFactory() throws ParseException {
    // esse parseException é para caso de erro na conversão de data

    Docente docente = Docente.builder()
        .nome("docenteTeste")
        .lattes("123456").id(1)
        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/11/2019"))
        .build();

    return docente;
  }

  private Tecnica tecnicaFactory() throws ParseException {
    Tecnica tecnica = Tecnica.builder().tipo("tipoTeste")
        .id(1)
        .titulo("tituloTeste")
        .ano(2023)
        .financiadora("financiadoraTeste")
        .outrasInformacoes("outrasInformacoesTeste")
        .qtdGrad(40)
        .qtdDoutorado(20)
        .qtdMestrado(10)
        .build();
    return tecnica;
  }

  @Test
  public void deveInformarIntervaloDeTempoComoDoisInteiros() throws ParseException {

    // Cenário
    Tecnica tecnicaTeste = tecnicaFactory();
    Docente docenteTeste = docenteFactory();

    // Lista de Técnicas e de docentes
    List<Docente> docentes = new ArrayList<>();
    List<Tecnica> tecnicas = new ArrayList<>();

    docentes.add(docenteRepository.save(docenteTeste));
    tecnicas.add(tecnicaRepository.save(tecnicaTeste));

    docenteTeste.setTecnicas(tecnicas);
    tecnicaTeste.setDocentes(docentes);

    // Ação
    Integer anoInicio = 2020;
    Integer anoFim = 2023;

    // Obtendo as técnicas por período
    Optional<List<Tecnica>> tecnicasOptional = tecnicaService.obterTecnicasDocentePorPeriodo(docenteTeste.getId(),
        anoInicio, anoFim);
    List<Tecnica> tecnicasBanco = tecnicasOptional.get();

    // Verificação
    Assertions.assertNotNull(tecnicasBanco);
    Assertions.assertFalse(tecnicasBanco.isEmpty());

    // O tipo informado pelo usuário deve ser o mesmo que o que está nas técnicas
    Assertions.assertInstanceOf(tecnicasBanco.get(0).getAno().getClass(), anoInicio);
    Assertions.assertInstanceOf(tecnicasBanco.get(0).getAno().getClass(), anoFim);

    // Se esse teste falhar, quer dizer que o service ainda não implementou a
    // mensagem de erro
    Assertions.assertThrows(ServicoRuntimeException.class,
        () -> tecnicaService.obterTecnicasDocentePorPeriodo(docenteTeste.getId(), -100, anoFim),
        "O período informado é inválido!");
  }
}