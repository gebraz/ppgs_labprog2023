package br.ufma.sppg.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.ufma.sppg.dto.Indices;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.services.DocenteService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DocenteServiceTest {

    @Autowired
    DocenteService service;

    @Autowired
    DocenteRepository docRepository;

    @Autowired
    ProducaoRepository prodRepository;

    @Autowired
    TecnicaRepository tecRepository;

    @Autowired
    OrientacaoRepository oriRepository;

    @Test
    public void deveObterProducoesPeloIdDocenteEIntervaloDeTempo() throws ParseException {
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1)
                                                .qtdGrad(1)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(3)
                                                .build();

        Producao producao2 = Producao.builder().tipo("tipo2")
                                                .issnOuSigla("sigla2")
                                                .ano(2007)
                                                .nomeLocal("Local2")
                                                .titulo("titulo2")
                                                .qualis("A2")
                                                .percentileOuH5(7)
                                                .qtdGrad(1)
                                                .qtdMestrado(3)
                                                .qtdDoutorado(3)
                                                .build();

        Producao producao3 = Producao.builder().tipo("tipo3")
                                                .issnOuSigla("sigla3")
                                                .ano(2023)
                                                .nomeLocal("Local3")
                                                .titulo("titulo3")
                                                .qualis("A3")
                                                .percentileOuH5(2)
                                                .qtdGrad(2)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(5)
                                                .build();

        Producao prodSalvo1 = prodRepository.save(producao1);
        Producao prodSalvo2 = prodRepository.save(producao2);
        Producao prodSalvo3 = prodRepository.save(producao3);

        List<Producao> lista1 = new ArrayList<>();
        List<Producao> lista2 = new ArrayList<>();
        
        lista1.add(prodSalvo1);
        lista1.add(prodSalvo2);
        lista1.add(prodSalvo3);
        lista2.add(prodSalvo1);
        lista2.add(prodSalvo3);


        docente1.setProducoes(lista1);
        docente2.setProducoes(lista2);

        Docente salvo1 = docRepository.save(docente1);
        docRepository.save(docente2);

        //Ação

        List<Producao> listaProd = service.obterProducaoDocente(salvo1.getId(), 2000, 2010);

        //Verificação
        Assertions.assertNotNull(listaProd);
        Assertions.assertEquals(2, listaProd.size());
        Assertions.assertEquals(prodSalvo1.getId(), listaProd.get(0).getId());
        Assertions.assertEquals(prodSalvo2.getId(), listaProd.get(1).getId());
    }

    @Test
    public void deveObterTecnicasPeloIdDocenteEIntervaloDeTempo() throws ParseException {
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();

        Tecnica tecnica1 = Tecnica.builder().tipo("tipo1")
                                                .financiadora("financiadora1")
                                                .ano(2001)
                                                .outrasInformacoes("info1")
                                                .titulo("titulo1")
                                                .qtdGrad(1)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(3)
                                                .build();

        Tecnica tecnica2 = Tecnica.builder().tipo("tipo2")
                                                .financiadora("financiadora2")
                                                .ano(2007)
                                                .outrasInformacoes("info2")
                                                .titulo("titulo2")
                                                .qtdGrad(1)
                                                .qtdMestrado(3)
                                                .qtdDoutorado(3)
                                                .build();

        Tecnica tecnica3 = Tecnica.builder().tipo("tipo3")
                                                .financiadora("financiadora3")
                                                .ano(2023)
                                                .outrasInformacoes("info3")
                                                .titulo("titulo3")
                                                .qtdGrad(2)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(5)
                                                .build();

        Tecnica tecSalvo1 = tecRepository.save(tecnica1);
        Tecnica tecSalvo2 = tecRepository.save(tecnica2);
        Tecnica tecSalvo3 = tecRepository.save(tecnica3);

        List<Tecnica> lista1 = new ArrayList<>();
        List<Tecnica> lista2 = new ArrayList<>();
        
        lista1.add(tecSalvo1);
        lista1.add(tecSalvo2);
        lista1.add(tecSalvo3);
        lista2.add(tecSalvo1);
        lista2.add(tecSalvo3);

        
        docente1.setTecnicas(lista1);
        docente2.setTecnicas(lista2);

        Docente salvo1 = docRepository.save(docente1);
        docRepository.save(docente2);

        //Ação

        List<Tecnica> listaTec = service.obterTecnicaDocente(salvo1.getId(), 2020, 2030);

        //Verificação
        Assertions.assertNotNull(listaTec);
        Assertions.assertEquals(1, listaTec.size());
        Assertions.assertEquals(tecSalvo3.getId(), listaTec.get(0).getId());
    }

    @Test
    public void deveObterOrientacoesPeloIdDocenteEIntervaloDeTempo()throws ParseException {
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();
        
        Orientacao Orientacao1 = Orientacao.builder().tipo("tipo1")
                                                .discente("discente1")
                                                .ano(2001)
                                                .modalidade("modalidade1")
                                                .titulo("titulo1")
                                                .instituicao("instituicao1")
                                                .curso("curso1")
                                                .status("status1")
                                                .build();
        
        Orientacao Orientacao2 = Orientacao.builder().tipo("tipo2")
                                                .discente("discente2")
                                                .ano(2007)
                                                .modalidade("modalidade2")
                                                .titulo("titulo2")
                                                .instituicao("instituicao2")
                                                .curso("curso2")
                                                .status("status2")
                                                .build();
        
        Orientacao Orientacao3 = Orientacao.builder().tipo("tipo3")
                                                .discente("discente3")
                                                .ano(2023)
                                                .modalidade("modalidade3")
                                                .titulo("titulo3")
                                                .instituicao("instituicao3")
                                                .curso("curso3")
                                                .status("status3")
                                                .build();
        
        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);
        
        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();
        
        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);
        
        Orientacao1.setOrientador(salvo1);
        Orientacao2.setOrientador(salvo2);
        Orientacao3.setOrientador(salvo1);
        
        Orientacao prodSalvo1 = oriRepository.save(Orientacao1);
        Orientacao prodSalvo2 = oriRepository.save(Orientacao2);
        Orientacao prodSalvo3 = oriRepository.save(Orientacao3);
        
        //Ação
        
        List<Orientacao> listaOri = service.obterOrientacaoDocente(salvo1.getId(), 2000, 2010);
        
        //Verificação
        Assertions.assertNotNull(listaOri);
        Assertions.assertEquals(1, listaOri.size());
        Assertions.assertEquals(prodSalvo1.getId(), listaOri.get(0).getId());
        
    }

    @Test
    public void deveObterIndicesPeloIdDocenteEIntervaloDeTempo()throws ParseException {
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5((float) 1)
                                                .qtdGrad(1)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(3)
                                                .build();

        Producao producao2 = Producao.builder().tipo("tipo2")
                                                .issnOuSigla("sigla2")
                                                .ano(2007)
                                                .nomeLocal("Local2")
                                                .titulo("titulo2")
                                                .qualis("A3")
                                                .percentileOuH5((float) 7)
                                                .qtdGrad(1)
                                                .qtdMestrado(3)
                                                .qtdDoutorado(3)
                                                .build();

        Producao producao3 = Producao.builder().tipo("tipo3")
                                                .issnOuSigla("sigla3")
                                                .ano(2023)
                                                .nomeLocal("Local3")
                                                .titulo("titulo3")
                                                .qualis("B4")
                                                .percentileOuH5((float) 2)
                                                .qtdGrad(2)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(5)
                                                .build();
        
        Producao producao4 = Producao.builder().tipo("tipo4")
                                                .issnOuSigla("sigla4")
                                                .ano(2017)
                                                .nomeLocal("Local4")
                                                .titulo("titulo4")
                                                .qualis("A4")
                                                .percentileOuH5((float) 2)
                                                .qtdGrad(2)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(5)
                                                .build();
        
        Producao producao5 = Producao.builder().tipo("tipo5")
                                                .issnOuSigla("sigla5")
                                                .ano(2005)
                                                .nomeLocal("Local5")
                                                .titulo("titulo5")
                                                .qualis("B2")
                                                .percentileOuH5((float) 2)
                                                .qtdGrad(2)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(5)
                                                .build();

        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);

        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();

        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);

        producao1.setDocentes(lista2);
        producao2.setDocentes(lista2);
        producao3.setDocentes(lista2);
        producao4.setDocentes(lista1);
        producao5.setDocentes(lista2);

        Indices indiceEsperado = new Indices(Double.valueOf(1.725), Double.valueOf(0.3), Double.valueOf(2.025));
        //Ação

        Indices indiceObtido = service.obterDocenteIndices(salvo2.getId(), 2000, 2023);

        //Verificação
        Assertions.assertNotNull(indiceObtido);
        Assertions.assertNotNull(indiceObtido.getIndiceGeral());
        Assertions.assertNotNull(indiceObtido.getIndiceRest());
        Assertions.assertNotNull(indiceObtido.getIndiceNRest());
        Assertions.assertEquals(indiceEsperado.getIndiceGeral(), indiceObtido.getIndiceGeral());
        Assertions.assertEquals(indiceEsperado.getIndiceRest(), indiceObtido.getIndiceRest());
        Assertions.assertEquals(indiceEsperado.getIndiceNRest(), indiceObtido.getIndiceNRest());
    }


    
}
