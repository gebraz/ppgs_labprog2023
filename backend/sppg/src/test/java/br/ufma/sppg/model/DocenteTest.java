package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.OrientacaoRepository;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class DocenteTest {
    
    @Autowired
    DocenteRepository repo;

    @Autowired
    ProgramaRepository prog;

    @Autowired
    OrientacaoRepository orientacaoRepository;

    @Test
    public void deveSalvarDocente(){
        Docente docente = Docente.builder().nome("John Doe").build();

        repo.save(docente);

        List<Docente> queryResult = repo.findByNome("John Doe");

        Assertions.assertFalse(queryResult.isEmpty());
        Assertions.assertNotNull(queryResult.get(0));
    }

    @Test
    public void deveSalvarDocenteComPrograma() throws ParseException {
        //cenário
        Programa novoPPg = Programa.builder().nome("PPGCC").build();
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                        .build();
        
        Programa progSalvo = prog.save(novoPPg);
        Docente docSalvo = repo.save(novoDocente);

        //ação
        ArrayList<Programa> programas = new ArrayList<>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas); //adicionar lista de programas em Docente

        Docente docSalvo2 = repo.save(docSalvo);

        Assertions.assertNotNull(docSalvo2);
                                    //Tamanho esperado            //valor
        Assertions.assertEquals(docSalvo2.getProgramas().size(), 1);
    }

    @Test
    public void deveSalvarDocenteComOrientacao() throws ParseException{
        Orientacao novoOrientecao = Orientacao.builder().titulo("Projeto de Pesquisa").build();
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                                           .lattes("123")
                                           .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                           .build();

        Orientacao orientacaoSalvo = orientacaoRepository.save(novoOrientecao);
        Docente docenteSalvo = repo.save(novoDocente);

        //acao
        ArrayList<Orientacao> orientacaos = new ArrayList<>();
        orientacaos.add(orientacaoSalvo);
        docenteSalvo.setOrientacoes(orientacaos);

        
    }
}
