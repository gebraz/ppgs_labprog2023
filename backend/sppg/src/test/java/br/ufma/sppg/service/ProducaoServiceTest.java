package br.ufma.sppg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.ufma.sppg.service.exceptions.RegrasRunTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.ProducaoService;

@SpringBootTest
class ProducaoServiceTest {

    @Autowired
    ProducaoService producaoService;

    @MockBean
    ProducaoRepository producaoRepository;

    @MockBean
    DocenteRepository docenteRepository;

    @MockBean
    ProgramaRepository programaRepository;

    @MockBean
    OrientacaoRepository orientacaoRepository;

    @Test
    void obterProducoesPPG_DeveRetornarListaProducoes() {
        Integer idPrograma = 1;
        Integer anoInicial = 2020;
        Integer anoFinal = 2022;

        Programa programa = new Programa();
        programa.setId(idPrograma);
        Docente docente = new Docente();
        Producao producao1 = new Producao();
        producao1.setId(1);
        producao1.setAno(2020);
        Producao producao2 = new Producao();
        producao2.setId(2);
        producao2.setAno(2021);
        List<Docente> docentes = new ArrayList<>();
        docentes.add(docente);
        List<Producao> producoes = new ArrayList<>();
        producoes.add(producao1);
        producoes.add(producao2);

        programa.setDocentes(docentes);
        docente.setProducoes(producoes);

        Optional<Programa> programaOptional = Optional.of(programa);
        when(programaRepository.findById(idPrograma)).thenReturn(programaOptional);

        List<Producao> result = producaoService.obterProducoesPPG(idPrograma, anoInicial, anoFinal);

        assertEquals(2, result.size());
        assertTrue(result.contains(producao1));
        assertTrue(result.contains(producao2));
    }

    @Test
    void obterProducoesPPG_SemProducoesDeveLancarExcecao() {
        Integer idPrograma = 1;
        Integer anoInicial = 2020;
        Integer anoFinal = 2022;

        Programa programa = new Programa();
        programa.setId(idPrograma);
        Docente docente = new Docente();
        List<Docente> docentes = new ArrayList<>();
        docentes.add(docente);

        programa.setDocentes(docentes);

        Optional<Programa> programaOptional = Optional.of(programa);
        when(programaRepository.findById(idPrograma)).thenReturn(programaOptional);

        assertThrows(RegrasRunTime.class, () ->
                producaoService.obterProducoesPPG(idPrograma, anoInicial, anoFinal)
        );
    }

    @Test
    void obterProducoesPPG_ProgramaNaoEncontradoDeveLancarExcecao() {
        Integer idPrograma = 1;
        Integer anoInicial = 2020;
        Integer anoFinal = 2022;

        when(programaRepository.findById(idPrograma)).thenReturn(Optional.empty());

        assertThrows(RegrasRunTime.class, () ->
                producaoService.obterProducoesPPG(idPrograma, anoInicial, anoFinal)
        );
    }
    @Test
    public void deveObterProducoesDocente() {
        // Criação do Docente para o teste
        Docente docente = new Docente();
        docente.setNome("Geraldo");
        docente.setLattes("123");

        // Criação de Produções para o Docente
        Producao producao1 = new Producao();
        producao1.setTipo("Artigo");
        producao1.setAno(2021);
        producao1.setTitulo("Producao 1");

        Producao producao2 = new Producao();
        producao2.setTipo("Livro");
        producao2.setAno(2022);
        producao2.setTitulo("Producao 2");

        Producao producao3 = new Producao();
        producao3.setTipo("Artigo");
        producao3.setAno(2023);
        producao3.setTitulo("Producao 3");
        // Criação da lista de produções
        List<Producao> producoes = new ArrayList<>();
        producoes.add(producao1);
        producoes.add(producao2);
        producoes.add(producao3);

        // Associando a lista de produções ao Docente
        docente.setProducoes(producoes);

        // Salvando o Docente no repositório
        docenteRepository.save(docente);
        // Executando o teste do serviço
        List<Producao> producoesEncontradas = producaoService.obterProducoesDocente(docente.getId(), 2022, 2023);

        // Verificando o resultado do teste
        assertEquals(2, producoesEncontradas.size());

        Producao producaoEncontrada1 = producoesEncontradas.get(0);
        assertEquals("Livro", producaoEncontrada1.getTipo());
        assertEquals(2022, producaoEncontrada1.getAno());
        assertEquals("Producao 2", producaoEncontrada1.getTitulo());

        Producao producaoEncontrada2 = producoesEncontradas.get(1);
        assertEquals("Artigo", producaoEncontrada2.getTipo());
        assertEquals(2023, producaoEncontrada2.getAno());
        assertEquals("Producao 3", producaoEncontrada2.getTitulo());
    }
    @Test
    public void deveObterProducoesDocente_DocenteSemProducoesTest() {
        // Criação do Docente sem Produções para o teste
        Docente docente = new Docente();
        docente.setNome("Geraldo");
        docente.setLattes("123");

        // Salvando o Docente no repositório (se necessário)

        // Executando o teste do serviço e verificando se uma exceção é lançada
        assertThrows(RegrasRunTime.class, () ->
                producaoService.obterProducoesDocente(docente.getId(), 2022, 2023)
        );
    }
    // Outros testes para os demais métodos do serviço ProducaoService
}
