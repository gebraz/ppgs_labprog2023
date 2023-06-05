package br.ufma.sppg.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    private Programa programaFactory() throws ParseException {
        Programa programa = Programa.builder()
                .nome("programaTeste")
                .build();
        return programa;
    }

    private Orientacao orientacaoFactory() {
        Orientacao orientacao = Orientacao.builder()
                .tipo("tipoTeste")
                .ano(2023)
                .discente("discenteTeste")
                .titulo("tituloTeste")
                .modalidade("modalidadeTeste")
                .instituicao("instituicaoTeste")
                .curso("cursoTeste")
                .status("statusTeste")
                .build();
        return orientacao;
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
        Assertions.assertInstanceOf(tecnicasBanco.get(0).getAno().getClass(),
                anoInicio);
        Assertions.assertInstanceOf(tecnicasBanco.get(0).getAno().getClass(),
                anoFim);

        // Se esse teste falhar, quer dizer que o service ainda não implementou a
        // mensagem de erro
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.obterTecnicasDocentePorPeriodo(docenteTeste.getId(),
                        -100, anoFim),
                "O período informado é inválido!");
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.obterTecnicasDocentePorPeriodo(docenteTeste.getId(),
                        anoInicio, 5000),
                "O período informado é inválido!");
    }

    @Test
    public void deveRecuperarTecnicasPPGNumIntervaloDeTempo() throws ParseException {

        // Cenario
        Tecnica tecnica = tecnicaFactory();
        Docente docente = docenteFactory();
        Programa programa = programaFactory();

        Integer anoInicio = 2020;
        Integer anoFim = 2023;

        Tecnica tecnicaSalva = tecnicaRepository.save(tecnica);
        Docente docenteSalvo = docenteRepository.save(docente);
        Programa programaSalvo = programaRepository.save(programa);

        List<Docente> docentes = new ArrayList<>();
        List<Tecnica> tecnicas = new ArrayList<>();
        List<Programa> programas = new ArrayList<>();

        docentes.add(docenteSalvo);
        tecnicas.add(tecnicaSalva);
        programas.add(programaSalvo);

        docenteSalvo.setTecnicas(tecnicas);
        docenteSalvo.setProgramas(programas);
        tecnicaSalva.setDocentes(docentes);
        programaSalvo.setDocentes(docentes);

        tecnicaSalva = tecnicaRepository.save(tecnicaSalva);
        docenteSalvo = docenteRepository.save(docenteSalvo);
        programaSalvo = programaRepository.save(programaSalvo);

        // Ação

        Optional<List<Tecnica>> tecnicasOptional = tecnicaService.obterTecnicasPPGPorPeriodo(programaSalvo.getId(),
                anoInicio, anoFim);
        List<Tecnica> tecnicasBanco = tecnicasOptional.get();

        Optional<List<Tecnica>> tecnicasOptional2 = tecnicaService.obterTecnicasPPGPorPeriodo(programaSalvo.getId(),
                anoInicio, 2022);
        List<Tecnica> tecnicasBanco2 = tecnicasOptional2.get();

        Optional<List<Tecnica>> tecnicasOptional3 = tecnicaService.obterTecnicasPPGPorPeriodo(programaSalvo.getId(),
                2023, 2023);
        List<Tecnica> tecnicasBanco3 = tecnicasOptional3.get();

        Tecnica tecnicaAux = tecnicaRepository.findById(tecnicaSalva.getId()).get();

        // Verificação
        Assertions.assertNotNull(tecnicasBanco);

        Assertions.assertEquals(tecnicaSalva.getId(), tecnicaAux.getId());
        Assertions.assertEquals(tecnicaSalva.getId(), tecnicasBanco.get(0).getId());
        Assertions.assertEquals(tecnicas.size(), tecnicasBanco.size());

        // tem que ficar vazio, já que o registro feito é de 2023
        // pq o intervalo nesse tecnicasBanco2 é de 2020 - 2022
        Assertions.assertEquals(0, tecnicasBanco2.size());
        Assertions.assertNotEquals(tecnicas.size(), tecnicasBanco2.size());

        // o intervalo do tecnicasBanco3 é de 2023 - 2023
        Assertions.assertEquals(tecnicaSalva.getId(), tecnicasBanco3.get(0).getId());
    }

    @Test
    public void deveRecuperarTecnicasPorDocenteNumIntervaloDeTempo() throws ParseException {

        // Cenario
        Tecnica tecnica = tecnicaFactory();
        Docente docente = docenteFactory();
        Programa programa = programaFactory();

        Integer anoInicio = 2020;
        Integer anoFim = 2023;

        Tecnica tecnicaSalva = tecnicaRepository.save(tecnica);
        Programa programaSalvo = programaRepository.save(programa);
        Docente docenteSalvo = docenteRepository.save(docente);

        List<Docente> docentes = new ArrayList<>();
        List<Tecnica> tecnicas = new ArrayList<>();
        List<Programa> programas = new ArrayList<>();

        docentes.add(docenteSalvo);
        tecnicas.add(tecnicaSalva);
        programas.add(programaSalvo);

        docenteSalvo.setTecnicas(tecnicas);
        docenteSalvo.setProgramas(programas);
        tecnicaSalva.setDocentes(docentes);
        programaSalvo.setDocentes(docentes);

        tecnicaSalva = tecnicaRepository.save(tecnicaSalva);
        docenteSalvo = docenteRepository.save(docenteSalvo);
        programaSalvo = programaRepository.save(programaSalvo);

        // Ação

        Optional<List<Tecnica>> tecnicasOptional = tecnicaService.obterTecnicasDocentePorPeriodo(docenteSalvo.getId(),
                anoInicio, anoFim);
        List<Tecnica> tecnicasBanco = tecnicasOptional.get();

        Optional<List<Tecnica>> tecnicasOptional2 = tecnicaService.obterTecnicasDocentePorPeriodo(docenteSalvo.getId(),
                anoInicio, 2022);
        List<Tecnica> tecnicasBanco2 = tecnicasOptional2.get();

        Optional<List<Tecnica>> tecnicasOptional3 = tecnicaService.obterTecnicasDocentePorPeriodo(docenteSalvo.getId(),
                2023, 2023);
        List<Tecnica> tecnicasBanco3 = tecnicasOptional3.get();

        Tecnica tecnicaAux = tecnicaRepository.findById(tecnicaSalva.getId()).get();

        // Verificação
        Assertions.assertNotNull(tecnicasBanco);

        Assertions.assertEquals(tecnicaSalva.getId(), tecnicaAux.getId());
        Assertions.assertEquals(tecnicaSalva.getId(), tecnicasBanco.get(0).getId());
        Assertions.assertEquals(tecnicas.size(), tecnicasBanco.size());

        // tem que ficar vazio, já que o registro feito é de 2023
        // pq o intervalo nesse tecnicasBanco2 é de 2020 - 2022
        Assertions.assertEquals(0, tecnicasBanco2.size());
        Assertions.assertNotEquals(tecnicas.size(), tecnicasBanco2.size());

        // o intervalo do tecnicasBanco3 é de 2023 - 2023
        Assertions.assertEquals(tecnicaSalva.getId(), tecnicasBanco3.get(0).getId());

        Assertions.assertEquals(docenteSalvo.getTecnicas().size(), tecnicasBanco.size());
        Assertions.assertEquals(docenteSalvo.getTecnicas().get(0).getId(), tecnicasBanco.get(0).getId());
    }

    @Test
    public void naoDeveSalvarTecnicaComEstatisticasInvalidas() throws ParseException {
        // Cenario 1
        Tecnica tecnica = tecnicaFactory();
        tecnica.setQtdDoutorado(-1);

        // Cenario 2
        Tecnica tecnica2 = tecnicaFactory();
        tecnica2.setQtdMestrado(-1);

        // Cenario 3
        Tecnica tecnica3 = tecnicaFactory();
        tecnica3.setQtdGrad(-1);

        // Cenario 4
        Tecnica tecnica4 = tecnicaFactory();
        tecnica4.setQtdGrad(-1);
        tecnica4.setQtdMestrado(-1);
        tecnica4.setQtdDoutorado(-1);

        // Cenario 5
        Tecnica tecnica5 = tecnicaFactory();

        Tecnica tecnicaBanco = tecnicaRepository.save(tecnica5);

        tecnicaBanco.setQtdGrad(-1);
        tecnicaBanco.setQtdMestrado(-1);
        tecnicaBanco.setQtdDoutorado(-1);

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.salvarTecnica(tecnica),
                "Estatísticas inválidas!");

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.salvarTecnica(tecnica2),
                "Estatísticas inválidas!");

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.salvarTecnica(tecnica3),
                "Estatísticas inválidas!");

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.salvarTecnica(tecnica4),
                "Estatísticas inválidas!");

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> tecnicaService.atualizarTecnica(tecnicaBanco),
                "Estatísticas inválidas!");
    }

    @Test
    public void deveObterOrientacoesAssociadasAUmaTecnica() throws ParseException {
        // Cenario - cria / builder
        Tecnica tecnica = tecnicaFactory();
        Docente docente = docenteFactory();
        Orientacao orientacao = orientacaoFactory();

        Tecnica tecSalvo = tecnicaRepository.save(tecnica);
        Docente docSalvo = docenteRepository.save(docente);
        Orientacao oriSalvo = orientacaoRepository.save(orientacao);

        // listas para salvar e setar em cada classe
        List<Docente> docentes = new ArrayList<>();
        List<Tecnica> tecnicas = new ArrayList<>();
        List<Orientacao> orientacoes = new ArrayList<>();

        docentes.add(docSalvo);
        tecnicas.add(tecSalvo);
        orientacoes.add(oriSalvo);

        docSalvo.setTecnicas(tecnicas);
        tecSalvo.setDocentes(docentes);
        oriSalvo.setTecnicas(tecnicas);

        tecnicaRepository.save(tecSalvo);
        orientacaoRepository.save(oriSalvo);
        docenteRepository.save(docSalvo);

        // acao
        Optional<List<Tecnica>> tecnicaSalva = tecnicaService.obterTecnicasOrientacaoPorPeriodo(oriSalvo.getId(), 2020,
                2023);
        List<Tecnica> aux = tecnicaSalva.get();

        // Verificação
        Assertions.assertNotNull(aux);
        Assertions.assertFalse(aux.isEmpty());
        Assertions.assertEquals(orientacoes, aux);

        // Assertions.assertEquals(aux.get(aux.size()).getId(),oriSalvo.getId());
    }
}