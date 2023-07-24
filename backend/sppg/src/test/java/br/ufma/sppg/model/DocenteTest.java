package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.transaction.annotation.Transactional;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@SpringBootTest
public class DocenteTest {

    @Autowired
    DocenteRepository repo;

    @Autowired
    ProgramaRepository prog;

    @Autowired
    OrientacaoRepository orientacaoRepository;

    @Autowired
    TecnicaRepository tecnicaRepository;

    @Autowired
    ProducaoRepository prodRepository;

    /*@Test
    public void deveSalvarDocente() {
        Docente docente = Docente.builder().nome("John Doe").build();

        repo.save(docente);

        Docente queryResult = repo.findByNome("John Doe"); 
        //Docente queryResult = repo.getByNome("John Doe");       
        Assertions.assertNotNull(queryResult);
    }*/

    /*@Test
    public void deveSalvarDocente() {
        Docente docente = Docente.builder().nome("John Doe").build();

        repo.save(docente);

        List<Docente> optionalDocente = repo.findByNome("John Doe");
        assertTrue(optionalDocente.isPresent()); // Verifica se o Optional contém um valor
        Docente queryResult = optionalDocente.get(); // Acessa o objeto Docente dentro do Optional
        assertNotNull(queryResult); // Verifica se o Docente não é nulo
    }*/


    @Test
    public void deveSalvarDocente() {
        Docente docente = Docente.builder().nome("John Doe").build();

        repo.save(docente);

        List<Docente> docentes = repo.findByNome("John Doe");
        assertFalse(docentes.isEmpty()); // Verifica se a lista de Docentes não está vazia
        Docente queryResult = docentes.get(0); // Acessa o primeiro objeto Docente da lista
        assertNotNull(queryResult); // Verifica se o Docente não é nulo
    }


    @Test
    @Transactional // Adicione a anotação @Transactional aqui
    public void deveSalvarDocenteComPrograma() throws ParseException {
        // cenário
        Programa novoPPg = Programa.builder().nome("PPGCC").build();
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/05/2022"))
                                        .build();
        
        Programa progSalvo = prog.save(novoPPg);
        Docente docSalvo = repo.save(novoDocente);

        //ação
        List<Programa> programas = new ArrayList<Programa>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas); // adicionar lista de programas em Docente

        Docente docSalvo2 = repo.save(docSalvo);

        Assertions.assertNotNull(docSalvo2);
        // Tamanho esperado //valor
        Assertions.assertEquals(docSalvo2.getProgramas().size(), 1);
    }

   /* @Test
    public void deveSalvarDocenteComPrograma() throws ParseException {
        // cenário
        Programa novoPPg = Programa.builder().nome("PPGCC").build();
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/05/2022"))
                                        .build();
        
        Programa progSalvo = prog.save(novoPPg);
        Docente docSalvo = repo.save(novoDocente);

        //ação
        List<Programa> programas = new ArrayList<Programa>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas); // adicionar lista de programas em Docente

        Docente docSalvo2 = repo.save(docSalvo);

        Assertions.assertNotNull(docSalvo2);
        // Tamanho esperado //valor
        Assertions.assertEquals(docSalvo2.getProgramas().size(), 1);
    }*/

    @Test
    public void deveSalvarDocenteComOrientacao() throws ParseException {
        Orientacao novoOrientecao = Orientacao.builder().titulo("Projeto de Pesquisa").build();
        Docente novoDocente = criaDocente();

        Orientacao orientacaoSalvo = orientacaoRepository.save(novoOrientecao);
        Docente docenteSalvo = repo.save(novoDocente);

        // acao
        ArrayList<Orientacao> orientacaos = new ArrayList<>();
        orientacaos.add(orientacaoSalvo);
        docenteSalvo.setOrientacoes(orientacaos);

    }

    @Test
    public void deveSalvarDocenteComTecnica() throws ParseException {
        // Cenario
        Docente novoDocente = Docente.builder().nome("Gebraz").lattes("123")
                .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023")).build();
        Tecnica novaTecnica = Tecnica.builder().titulo("Qualquer").build();

        Docente docenteSalvo = repo.save(novoDocente);
        Tecnica tecnicaSalva = tecnicaRepository.save(novaTecnica);

        List<Tecnica> tecnicas = new ArrayList<Tecnica>();
        tecnicas.add(tecnicaSalva);
        docenteSalvo.setTecnicas(tecnicas);

        Docente novoDocenteSalvo = repo.save(docenteSalvo);

        Assertions.assertNotNull(novoDocenteSalvo);
        Assertions.assertEquals(novoDocenteSalvo.getTecnicas().size(), 1);
    }

    @Test
    public void deveSalvarDocenteComProducao() throws ParseException {
        // Cenario
        Docente novoDocente = criaDocente();
        Producao novaProd = Producao.builder().titulo("Desenvolvimento de sistemas").build();

        Docente docenteSalvo = repo.save(novoDocente);
        Producao prodSalva = prodRepository.save(novaProd);

        List<Producao> prods = new ArrayList<Producao>();
        prods.add(prodSalva);
        docenteSalvo.setProducoes(prods);

        Docente novoDocenteSalvo = repo.save(docenteSalvo);

        Assertions.assertNotNull(novoDocenteSalvo);
        Assertions.assertEquals(novoDocenteSalvo.getProducoes().size(), 1);

    }

    @Test
    public void deveAtualizarDataAtualizacaoDocente() throws ParseException {
        // cenário
        Docente novoDocente = criaDocente();
        Docente docenteSalvo = repo.save(novoDocente);

        // ação
        docenteSalvo.setNome("Novo nomet");
        Docente novoDocenteSalvo = repo.save(docenteSalvo);

        // teste
        // verifica se a data foi atualizada
        Assertions.assertNotEquals(novoDocenteSalvo.getDataAtualizacao(), docenteSalvo.getDataAtualizacao());
    }

    @Test
    public void deveImpedirRemoverDocenteComDependencia() throws ParseException {
        // Cenário -----------------------------------------------------
        // Cria-se um docente e suas depedencias
        Docente novoDocente = criaDocente();

        // Programa
        Programa novoPrograma = Programa.builder().nome("PPGCC").build();
        Programa programaSalvo = prog.save(novoPrograma);
        List<Programa> programas = new ArrayList<>();

        Docente docenteSalvoSemPrograma = repo.save(novoDocente);
        programas.add(programaSalvo);
        docenteSalvoSemPrograma.setProgramas(programas);
        Docente docenteSalvoComPrograma = repo.save(docenteSalvoSemPrograma);

        // Orientacao
        Orientacao novoOrientecao = Orientacao.builder().titulo("Projeto de Pesquisa").build();
        Orientacao orientacaoSalva = orientacaoRepository.save(novoOrientecao);
        List<Orientacao> orientacaos = new ArrayList<>();
        orientacaos.add(orientacaoSalva);

        Docente docenteSalvoSemOrientacao = repo.save(novoDocente);
        orientacaos.add(orientacaoSalva);
        docenteSalvoSemOrientacao.setOrientacoes(orientacaos);
        Docente docenteSalvoComOrientacao = repo.save(docenteSalvoSemPrograma);

        // Tecnica
        Tecnica novaTecnica = Tecnica.builder().titulo("Qualquer").build();
        Tecnica tecnicaSalva = tecnicaRepository.save(novaTecnica);
        List<Tecnica> tecnicas = new ArrayList<Tecnica>();
        tecnicas.add(tecnicaSalva);

        Docente docenteSalvoSemTecnica = repo.save(novoDocente);
        docenteSalvoSemTecnica.setTecnicas(tecnicas);
        Docente docenteSalvoComTecnica = repo.save(docenteSalvoSemTecnica);

        // Producao
        Producao novaProd = Producao.builder().titulo("Desenvolvimento de sistemas").tipo("P").build();
        Producao prodSalva = prodRepository.save(novaProd);
        List<Producao> prods = new ArrayList<Producao>();
        prods.add(prodSalva);

        Docente docenteSalvoSemProd = repo.save(novoDocente);
        docenteSalvoSemProd.setProducoes(prods);
        Docente docenteSalvoComProd = repo.save(docenteSalvoSemProd);

        // Ação -----------------------------------------------------
        // Tenta-se remover docentes

        // Programa
        repo.delete(docenteSalvoComPrograma);

        // Orientacao
        repo.delete(docenteSalvoComOrientacao);

        // Tecnica
        repo.delete(docenteSalvoComTecnica);

        // Producao
        repo.delete(docenteSalvoComProd);

        // Teste -----------------------------------------------------
        // Verifica-se a integridade dos docentes salvos

        // Programa
        Optional<Docente> docenteComPrograma = repo.findById(docenteSalvoComPrograma.id);
        Assertions.assertNotNull(docenteComPrograma);

        // Orientacao
        Optional<Docente> docenteComOrientacao = repo.findById(docenteSalvoComOrientacao.id);
        Assertions.assertNotNull(docenteComOrientacao);

        // Tecnica
        Optional<Docente> docenteComTecnica = repo.findById(docenteSalvoComTecnica.id);
        Assertions.assertNotNull(docenteComTecnica);

        // Producao
        Optional<Docente> docenteComProducao = repo.findById(docenteSalvoComProd.id);
        Assertions.assertNotNull(docenteComProducao);

    }

    private Docente criaDocente() throws ParseException {
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                .lattes("123")
                .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                .build();

        return novoDocente;
    }
}
