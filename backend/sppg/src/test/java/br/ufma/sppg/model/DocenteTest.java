package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.repo.ProducaoRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;



@SpringBootTest
public class DocenteTest {
    


    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;

    @Autowired
    private ProducaoRepository producaoRepository;


    @Test
    public void deveSalvarDocente() throws ParseException{
        // Cenário
        Docente docente = Docente.builder()
            .nome("John Doe")
            .lattes("123456")
            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/07/2023"))
            .build();

        // Ação
        Docente docenteSalvo = docenteRepository.save(docente);

        // Verificação
        assertNotNull(docenteSalvo.getId());
    }

    @Test
    public void deveSalvarDocenteComPrograma() throws ParseException {
        // Cenário
        Programa programa = Programa.builder()
            .nome("Programa de Pós-Graduação em Engenharia")
            .build();
        programaRepository.save(programa);

        Docente docente = Docente.builder()
            .nome("John Doe")
            .lattes("123456")
            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/05/2022"))
            .programas(List.of(programa))
            .build();

        // Ação
        Docente docenteSalvo = docenteRepository.save(docente);

        // Verificação
        assertNotNull(docenteSalvo.getId());
        assertNotNull(docenteSalvo.getProgramas());
        assertEquals(1, docenteSalvo.getProgramas().size());
    }

    // Outros testes para os cenários de Docente com Orientação, Tecnica e Produção...
    
    @Test
    public void deveImpedirRemoverDocenteComDependencia() throws ParseException {
        // Crie e salve um novo docente no banco de dados
        Docente docente = Docente.builder()
            .nome("João da Silva")
            .lattes("123")
            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023"))
            .build();
        docenteRepository.save(docente);
    
        // Tente remover o docente
        try {
            docenteRepository.delete(docente);
            // Se chegar aqui, a remoção não foi impedida e o teste falha
            fail("Deveria ter lançado uma exceção devido a dependências");
        } catch (DataIntegrityViolationException e) {
            // A exceção foi lançada corretamente, o teste passa
            // Aqui você pode fazer mais verificações se necessário
        }
    }
    
    @Test
    public void deveAtualizarDataAtualizacaoDocente() throws ParseException {
        // Cenário
        Docente docente = Docente.builder()
            .nome("John Doe")
            .lattes("123456")
            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023"))
            .build();
        docenteRepository.save(docente);

        // Ação
        Date novaData = new Date();
        docente.setDataAtualizacao(novaData);
        docenteRepository.save(docente);

        // Verificação
        Docente docenteAtualizado = docenteRepository.findById(docente.getId()).orElse(null);
        assertNotNull(docenteAtualizado);
        assertEquals(novaData, docenteAtualizado.getDataAtualizacao());
    }


    @Test
    public void deveSalvarDocenteComTecnica() {
    // Cenário
    Tecnica tecnica = Tecnica.builder()
        .titulo("Tecnica 1")
        .build();
        tecnicaRepository.save(tecnica);

    Docente docente = Docente.builder()
        .nome("John Dias")
        .lattes("66666")
        .dataAtualizacao(new Date())
        .tecnicas(List.of(tecnica))
        .build();

    // Ação
    Docente docenteSalvo = docenteRepository.save(docente);

    // Verificação
    assertNotNull(docenteSalvo.getId());
    assertNotNull(docenteSalvo.getTecnicas());
    assertEquals(1, docenteSalvo.getTecnicas().size());
    }

    @Test
    public void deveSalvarDocenteComProducao() {
        // Cenário
        Producao producao = Producao.builder()
            .titulo("Producao 1")
            .build();
            producaoRepository.save(producao);

        Docente docente = Docente.builder()
            .nome("John Doe")
            .lattes("123456")
            .dataAtualizacao(new Date())
            .producoes(List.of(producao))
            .build();

        // Ação
        Docente docenteSalvo = docenteRepository.save(docente);

        // Verificação
        assertNotNull(docenteSalvo.getId());
        assertNotNull(docenteSalvo.getProducoes());
        assertEquals(1, docenteSalvo.getProducoes().size());
    }

}
