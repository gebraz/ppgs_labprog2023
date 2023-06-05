package br.ufma.sppg.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.Doc;

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
public class TecnicaServiceTeste {
 
    @Autowired
    TecnicaService service;

    @Autowired
    TecnicaRepository repositoryTec;

    @Autowired
    OrientacaoRepository repositoryOri;    

    @Autowired
    DocenteRepository repositoryDoc;  
    
    @Autowired
    ProgramaRepository repositoryProg;

    private Docente docenteFactory() throws ParseException {
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                                    .lattes("123").id(1)
                                    .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                    .build();

        return novoDocente;
    }

    private Programa progFactory (){
        Programa novoPrograma = Programa.builder()
                                        .nome("Programa de Pós-Graduação em Engenharia de Eletricidade - PPGEE")
                                        .id(1)
                                        .build();
        return novoPrograma;
    }

    private Orientacao oriFactory(){
        Orientacao orientacao = Orientacao.builder().tipo("TCC").ano(2023).discente("Gabriel").titulo("TCC")
                                .modalidade("Presencial").instituicao("UFMA")
                                .curso("Ciência da Computação").status("Ativo").build();
        return orientacao;
    }

    private Tecnica tecnicaFactory(){
        Tecnica novTecnica = Tecnica.builder().tipo("tipo1")
                                    .id(1)
                                    .titulo("titulo1")
                                    .ano(2023)
                                    .financiadora("financiadora1")
                                    .outrasInformacoes("informações1")
                                    .qtdGrad(1)
                                    .qtdDoutorado(11)
                                    .qtdMestrado(111)
                                    .build();
        return novTecnica;
    }

    @Test
    public void deveInformarIntervaloDeTempoComoDoisInteiros() throws ParseException{
        // Cenario - cria / builder
        Tecnica tecnicaD = tecnicaFactory();
        Docente docente = docenteFactory();
        Docente docSalvo = repositoryDoc.save(docente);
        Tecnica tecSalvo = repositoryTec.save(tecnicaD);

        //listas para salvar e setar em cada classe
        List<Docente> ldocente = new ArrayList<>();
        List<Tecnica> ltecnica = new ArrayList<>();

        ldocente.add(docSalvo);
        ltecnica.add(tecSalvo);

        docSalvo.setTecnicas(ltecnica);
        tecSalvo.setDocentes(ldocente);

        //acao
        Optional<List<Tecnica>> tec = service.obterTecnicasDocentePorPeriodo(docente.getId(), 2020, 2023) ;
        List<Tecnica> aux = tec.get();
        //Verificação
        Assertions.assertNotNull(aux); 
        Assertions.assertFalse(aux.isEmpty()); 
        Assertions.assertEquals(ltecnica,aux);    

        repositoryTec.delete(tecnicaD);
        repositoryDoc.delete(docente);
    }

    @Test
    public void deveGerarErroAoUsarIntervaloDeTempoComPeriodoInvalido() throws ParseException{
        Tecnica tecnica = tecnicaFactory();
        Docente docente = docenteFactory();
        Docente docSalvo = repositoryDoc.save(docente);
        Tecnica tecSalvo = repositoryTec.save(tecnica);


        List<Docente> ldocente = new ArrayList<>();
        List<Tecnica> ltecnica = new ArrayList<>();

        ldocente.add(docSalvo);
        ltecnica.add(tecSalvo);

        docente.setTecnicas(ltecnica);
        tecnica.setDocentes(ldocente);

        //acao
        Assertions.assertThrows(ServicoRuntimeException.class, 
                                    () -> service.obterTecnicasDocentePorPeriodo(docente.getId(), -1, 2023), 
                                    "Periodo inválido");
    }

    

    @Test
    public void deveConseguirRecuperarTecnicasPorDocenteNumIntervaloDeTempo() throws ParseException{
        // Cenario - cria / builder
        Tecnica tecnicaD = tecnicaFactory();
        Docente docente = docenteFactory();
        Docente docSalvo = repositoryDoc.save(docente);
        Tecnica tecSalvo = repositoryTec.save(tecnicaD);

        //listas para salvar e setar em cada classe
        List<Docente> ldocente = new ArrayList<>();
        List<Tecnica> ltecnica = new ArrayList<>();

        ldocente.add(docSalvo);
        ltecnica.add(tecSalvo);

        docSalvo.setTecnicas(ltecnica);
        tecSalvo.setDocentes(ldocente);

        //acao
        Optional<List<Tecnica>> tec = service.obterTecnicasDocentePorPeriodo(docente.getId(), 2020, 2023) ;
        List<Tecnica> aux = tec.get();
        
        //Verificação
        Assertions.assertNotNull(aux); 
        Assertions.assertFalse(aux.isEmpty());    
        Assertions.assertTrue(aux.contains(ltecnica)); 

        repositoryTec.delete(tecnicaD);
        repositoryDoc.delete(docente);
    }


    @Test
    public void deveConseguirRecuperarTecnicasPorProgramNumIntervaloDeTempo() throws ParseException{
        // Cenario - cria / builder
        Tecnica tecnicaD = tecnicaFactory();
        Docente docente = docenteFactory();
        Programa prog = progFactory();
        Integer ini = 2020;
        Integer fim = 2023;
        Tecnica tecSalvo = repositoryTec.save(tecnicaD);
        Docente docSalvo= repositoryDoc.save(docente);
        Programa progSalvo = repositoryProg.save(prog);
        


        //listas para salvar e setar em cada classe
        List<Docente> ldocente = new ArrayList<>();
        List<Tecnica> ltecnica = new ArrayList<>();
        List<Programa> lprog = new ArrayList<>();

        ldocente.add(docSalvo);
        ltecnica.add(tecSalvo);
        lprog.add(progSalvo);

        docSalvo.setTecnicas(ltecnica);
        docSalvo.setProgramas(lprog);
        tecSalvo.setDocentes(ldocente);
        progSalvo.setDocentes(ldocente);
    

        //acao
        Optional<List<Tecnica>> tec = service.obterTecnicasPPGPorPeriodo(progSalvo.getId(), ini, fim);
        List<Tecnica> aux = tec.get();
        // Verificação
        Assertions.assertEquals(ltecnica,aux);
        Assertions.assertEquals(aux.get(aux.size()).getId(),tecSalvo.getId());
        Assertions.assertNotNull(aux); 
        Assertions.assertFalse(aux.isEmpty()); 

        repositoryTec.delete(tecnicaD);
        repositoryDoc.delete(docente);
        repositoryProg.delete(prog);
    }

    @Test
    public void naoDeveSalvarTecnicaComEstatisticaInvalida() throws ParseException{
        Tecnica tecnica = tecnicaFactory();

        tecnica.setDocentes(null);
        tecnica.setQtdMestrado(null);
        tecnica.setQtdDoutorado(null); 

        Assertions.assertThrows(ServicoRuntimeException.class, 
                                    () -> service.salvarTecnica(tecnica), 
                                    "Técnica com estatítiscas nulas");
    }

    @Test
    public void deveObterOrientacoesAssociadasAUmaTecnica() throws ParseException{
        // Cenario - cria / builder
        Tecnica tecnicaD = tecnicaFactory();
        Docente docente = docenteFactory();
        Orientacao orientacoes = oriFactory();

        Tecnica tecSalvo = repositoryTec.save(tecnicaD);
        Docente docSalvo = repositoryDoc.save(docente);
        Orientacao oriSalvo = repositoryOri.save(orientacoes);
        
        //listas para salvar e setar em cada classe
        List<Docente> ldocente = new ArrayList<>();
        List<Tecnica> ltecnica = new ArrayList<>();
        List<Orientacao> lori = new ArrayList<>();

        ldocente.add(docSalvo);
        ltecnica.add(tecSalvo);
        lori.add(oriSalvo);

        docSalvo.setTecnicas(ltecnica);
        tecSalvo.setDocentes(ldocente);
        oriSalvo.setTecnicas(ltecnica);
        
        //acao
        Optional<List<Tecnica>> tec = service.obterTecnicasOrientacaoPorPeriodo(oriSalvo.getId(), 2020,2023) ;
        List<Tecnica> aux = tec.get();
        
        //Verificação
        Assertions.assertNotNull(aux); 
        Assertions.assertFalse(aux.isEmpty()); 
        Assertions.assertEquals(lori,aux);
        Assertions.assertEquals(aux.get(aux.size()).getId(),oriSalvo.getId());
 

        repositoryTec.delete(tecnicaD);
        repositoryDoc.delete(docente);
        //repositoryOri.delete(orientacoes);
    }

}

