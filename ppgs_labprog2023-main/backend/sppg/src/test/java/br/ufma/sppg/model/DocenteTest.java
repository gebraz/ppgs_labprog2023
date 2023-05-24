package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;

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
    OrientacaoRepository orient;

    @Autowired
    TecnicaRepository tecn;
    
    @Test
    public void deveSalvarDocente() throws ParseException {
        //cenario
            Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                                .lattes("123")
                                                .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                                .build();
        //acao
            Docente docSalvo = repo.save(novDocente);
                                                
        //teste
            Assertions.assertNotNull(docSalvo);
            Assertions.assertEquals(novDocente.getId(), docSalvo.getId());
            Assertions.assertEquals(novDocente.getNome(), docSalvo.getNome());
            Assertions.assertEquals(novDocente.getDataAtualizacao(), docSalvo.getDataAtualizacao());


    }

    @Test
    public void deveSalvarDocentComPrograma() throws ParseException {
        //cenário
        Programa novoPPg = Programa.builder().nome("PPGCC").build();

        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                        .build();
        
        Programa progSalvo = prog.save(novoPPg);
        Docente docSalvo = repo.save(novDocente);

        //ação
        ArrayList<Programa> programas = new ArrayList<>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas);
        
        Docente docSalvo2 = repo.save(docSalvo);

        //teste
        Assertions.assertNotNull(docSalvo2);
        Assertions.assertEquals(docSalvo2.getProgramas().size(), 1);

    }

    @Test
    public void deveSalvarDocentComOrientacao() throws ParseException {
        //cenario
        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                            .lattes("123")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .build();

        Orientacao novaOrientacao = Orientacao.builder().id(1).tipo("teste").discente("teste_disc")
                                        .titulo("teste_titulo")
                                        .ano(2023)
                                        .modalidade("teste_modalidade")
                                        .instituicao("teste_orientacao")
                                        .curso("teste_curso")
                                        .status("teste_status")
                                        .orientador(novDocente).build();


        Docente docSalvo = repo.save(novDocente);        
        Orientacao orientSalvo = orient.save(novaOrientacao);
        
        //acao
        ArrayList<Orientacao> orientacoes = new ArrayList<>();
        orientacoes.add(orientSalvo);
        docSalvo.setOrientacoes(orientacoes);

        //teste
        Assertions.assertNotNull(docSalvo);
        Assertions.assertEquals(docSalvo.getOrientacoes().size(), 1);
    }

    @Test
    public void deveSalvarDocentComTecnica() throws ParseException {
        //cenario
        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                            .lattes("123")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .build();

        Tecnica novTecnica = Tecnica.builder().id(1).tipo("teste_tipo").titulo("teste_titulo")
                                            .ano(2023)
                                            .financiadora("teste_financiadora")
                                            .outrasInformacoes("teste_outrasInformacoes")
                                            .qtdGrad(1)
                                            .qtdMestrado(2)
                                            .qtdDoutorado(3).build();

        Docente docSalvo = repo.save(novDocente);
        Tecnica tecSalvo = tecn.save(novTecnica);

        //acao
        ArrayList<Tecnica> tecnicas = new ArrayList<>();
        tecnicas.add(tecSalvo);
        docSalvo.setTecnicas(tecnicas);

        //teste
        Assertions.assertNotNull(docSalvo);
        Assertions.assertEquals(docSalvo.getTecnicas().size(), 1);
    }

    @Test
    public void deveImpedirRemoverDocenteComDependencia() throws ParseException {
        //cenario
        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                            .lattes("123")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .build();

        Tecnica novTecnica = Tecnica.builder().id(1).tipo("teste_tipo").titulo("teste_titulo")
                                            .ano(2023)
                                            .financiadora("teste_financiadora")
                                            .outrasInformacoes("teste_outrasInformacoes")
                                            .qtdGrad(1)
                                            .qtdMestrado(2)
                                            .qtdDoutorado(3).build();

        Orientacao novaOrientacao = Orientacao.builder().id(1).tipo("teste").discente("teste_disc")
                                            .titulo("teste_titulo")
                                            .ano(2023)
                                            .modalidade("teste_modalidade")
                                            .instituicao("teste_orientacao")
                                            .curso("teste_curso")
                                            .status("teste_status")
                                            .orientador(novDocente).build();
        Programa novoPPg = Programa.builder().nome("PPGCC").build();


        Docente docSalvo = repo.save(novDocente);        
        Tecnica tecSalvo = tecn.save(novTecnica);
        Orientacao orientSalvo = orient.save(novaOrientacao);
        Programa progSalvo = prog.save(novoPPg);
        
        //acao

        ArrayList<Tecnica> tecnicas = new ArrayList<>();
        tecnicas.add(tecSalvo);
        docSalvo.setTecnicas(tecnicas);

        ArrayList<Orientacao> orientacoes = new ArrayList<>();
        orientacoes.add(orientSalvo);
        docSalvo.setOrientacoes(orientacoes);

        ArrayList<Programa> programas = new ArrayList<>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas);

       // if(Assertions.assertEquals(docSalvo.getTecnicas().size() != 0)){

       // }

        //teste



    }

}
