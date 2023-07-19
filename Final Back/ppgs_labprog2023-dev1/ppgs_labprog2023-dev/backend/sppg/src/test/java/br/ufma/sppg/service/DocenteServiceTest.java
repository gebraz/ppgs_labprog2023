package br.ufma.sppg.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.*;
import br.ufma.sppg.repo.*;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

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
    public void deveObterProducoesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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

        List<Producao> listaProd = service.obterProducoes(salvo1.getId(), 2000, 2010);

        //Verificação
        Assertions.assertNotNull(listaProd);
        Assertions.assertEquals(2, listaProd.size());
        Assertions.assertEquals(prodSalvo1.getId(), listaProd.get(0).getId());
        Assertions.assertEquals(prodSalvo2.getId(), listaProd.get(1).getId());
    }

    @Test
    public void deveGerarErroIdNullaoObterProducoesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
        producao2.setDocentes(lista1);
        producao3.setDocentes(lista2);

        Producao prodSalvo1 = prodRepository.save(producao1);
        Producao prodSalvo2 = prodRepository.save(producao2);
        Producao prodSalvo3 = prodRepository.save(producao3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterProducoes(null, 2000, 2010),
                "Id Inválido");
    }

    @Test
    public void deveGerarErroIdNaoExisteaoObterProducoesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
        producao2.setDocentes(lista1);
        producao3.setDocentes(lista2);

        Producao prodSalvo1 = prodRepository.save(producao1);
        Producao prodSalvo2 = prodRepository.save(producao2);
        Producao prodSalvo3 = prodRepository.save(producao3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterProducoes(9999, 2000, 2010),
                "Id do Docente não está registrado");
    }

    @Test
    public void deveGerarErroDataInicialNullaoObterProducoesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
        producao2.setDocentes(lista1);
        producao3.setDocentes(lista2);

        Producao prodSalvo1 = prodRepository.save(producao1);
        Producao prodSalvo2 = prodRepository.save(producao2);
        Producao prodSalvo3 = prodRepository.save(producao3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterProducoes(salvo1.getId(), null, 2010),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataFinalNullaoObterProducoesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
        producao2.setDocentes(lista1);
        producao3.setDocentes(lista2);

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterProducoes(salvo1.getId(), 2020, null),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataInicialMaiorQueaFinalaoObterProducoesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
        producao2.setDocentes(lista1);
        producao3.setDocentes(lista2);

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterProducoes(salvo1.getId(), 2030, 2020),
                "Data inical maior que a data final");
    }

    @Test
    public void deveObterTecnicasPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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

        List<Tecnica> listaTec = service.obterTecnicas(salvo1.getId(), 2020, 2030);

        //Verificação
        Assertions.assertNotNull(listaTec);
        Assertions.assertEquals(1, listaTec.size());
        Assertions.assertEquals(tecSalvo3.getId(), listaTec.get(0).getId());
    }

    @Test
    public void deveGerarErroIdNullaoObterTecnicasPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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

        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);

        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();

        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);

        tecnica1.setDocentes(lista2);
        tecnica2.setDocentes(lista1);
        tecnica3.setDocentes(lista2);

        tecRepository.save(tecnica1);
        tecRepository.save(tecnica2);
        tecRepository.save(tecnica3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterTecnicas(null, 2020, 2030),
                "Id Inválido");
    }

    @Test
    public void deveGerarErroIdNaoExisteaoObterTecnicasPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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

        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);

        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();

        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);

        tecnica1.setDocentes(lista2);
        tecnica2.setDocentes(lista1);
        tecnica3.setDocentes(lista2);

        tecRepository.save(tecnica1);
        tecRepository.save(tecnica2);
        tecRepository.save(tecnica3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterTecnicas(9999, 2020, 2030),
                "Id do Docente não está registrado");
    }

    @Test
    public void deveGerarErroDataInicialNullaoObterTecnicasPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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

        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);

        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();

        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);

        tecnica1.setDocentes(lista2);
        tecnica2.setDocentes(lista1);
        tecnica3.setDocentes(lista2);

        tecRepository.save(tecnica1);
        tecRepository.save(tecnica2);
        tecRepository.save(tecnica3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterTecnicas(salvo1.getId(), null, 2030),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroFinalNullaoObterTecnicasPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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

        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);

        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();

        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);

        tecnica1.setDocentes(lista2);
        tecnica2.setDocentes(lista1);
        tecnica3.setDocentes(lista2);

        tecRepository.save(tecnica1);
        tecRepository.save(tecnica2);
        tecRepository.save(tecnica3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterTecnicas(salvo1.getId(), 2020, null),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataInicialMaiorQueaFinalaoObterTecnicasPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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

        Docente salvo1 = docRepository.save(docente1);
        Docente salvo2 = docRepository.save(docente2);

        List<Docente> lista1 = new ArrayList<>();
        List<Docente> lista2 = new ArrayList<>();

        lista1.add(salvo1);
        lista2.add(salvo1);
        lista2.add(salvo2);

        tecnica1.setDocentes(lista2);
        tecnica2.setDocentes(lista1);
        tecnica3.setDocentes(lista2);

        tecRepository.save(tecnica1);
        tecRepository.save(tecnica2);
        tecRepository.save(tecnica3);

        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterTecnicas(salvo1.getId(), 2030, 2020),
                "Data inical maior que a data final");
    }

    @Test
    public void deveObterOrientacoesPeloIdDocenteEIntervaloDeTempo(){
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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
        
        List<Orientacao> listaOri = service.obterOrientacoes(salvo1.getId(), 2000, 2010);
        
        //Verificação
        Assertions.assertNotNull(listaOri);
        Assertions.assertEquals(1, listaOri.size());
        Assertions.assertEquals(prodSalvo1.getId(), listaOri.get(0).getId());
        
    }

    @Test
    public void deveGerarErroIdNullObterOrientacoesPeloIdDocenteEIntervaloDeTempo(){
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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
        
        oriRepository.save(Orientacao1);
        oriRepository.save(Orientacao2);
        oriRepository.save(Orientacao3);
        
        //Ação
        
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterOrientacoes(null, 2000, 2010),
                "Id Inválido");
    }

    @Test
    public void deveGerarErroIdNaoExisteObterOrientacoesPeloIdDocenteEIntervaloDeTempo(){
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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
        
        oriRepository.save(Orientacao1);
        oriRepository.save(Orientacao2);
        oriRepository.save(Orientacao3);
        
        //Ação
        
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterOrientacoes(9999, 2000, 2010),
                "Id do Docente não está registrado");
    }

    @Test
    public void deveGerarErroDataInicialNullObterOrientacoesPeloIdDocenteEIntervaloDeTempo(){
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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
        
        oriRepository.save(Orientacao1);
        oriRepository.save(Orientacao2);
        oriRepository.save(Orientacao3);
        
        //Ação
        
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterOrientacoes(salvo1.getId(), null, 2010),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataFinalNullObterOrientacoesPeloIdDocenteEIntervaloDeTempo(){
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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
        
        oriRepository.save(Orientacao1);
        oriRepository.save(Orientacao2);
        oriRepository.save(Orientacao3);
        
        //Ação
        
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterOrientacoes(salvo1.getId(), 2000, null),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataInicialMaiorQueaFinalObterOrientacoesPeloIdDocenteEIntervaloDeTempo(){
        oriRepository.deleteAll();
        docRepository.deleteAll();
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
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
        
        oriRepository.save(Orientacao1);
        oriRepository.save(Orientacao2);
        oriRepository.save(Orientacao3);
        
        //Ação
        
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterOrientacoes(salvo1.getId(), 2010, 2000),
                "Data inical maior que a data final");
    }

    @Test
    public void deveObterIndicesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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

        Producao prodSalvo1 = prodRepository.save(producao1);
        Producao prodSalvo2 = prodRepository.save(producao2);
        Producao prodSalvo3 = prodRepository.save(producao3);
        Producao prodSalvo4 = prodRepository.save(producao4);
        Producao prodSalvo5 = prodRepository.save(producao5);

        Indice indiceEsperado = new Indice(Double.valueOf(1.725), Double.valueOf(0.3), Double.valueOf(2.025));
        //Ação

        Indice indiceObtido = service.obterIndice(salvo2.getId(), 2000, 2023);

        //Verificação
        Assertions.assertNotNull(indiceObtido);
        Assertions.assertNotNull(indiceObtido.getIndiceGeral());
        Assertions.assertNotNull(indiceObtido.getIndiceRest());
        Assertions.assertNotNull(indiceObtido.getIndiceNRest());
        Assertions.assertEquals(indiceEsperado.getIndiceGeral(), indiceObtido.getIndiceGeral());
        Assertions.assertEquals(indiceEsperado.getIndiceRest(), indiceObtido.getIndiceRest());
        Assertions.assertEquals(indiceEsperado.getIndiceNRest(), indiceObtido.getIndiceNRest());
    }

    @Test
    public void deveGerarErroIdNullObterIndicesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);
        prodRepository.save(producao4);
        prodRepository.save(producao5);
        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterIndice(null, 2000, 2023),
                "Id Inválido");
    }

    @Test
    public void deveGerarErroIdNaoExisteObterIndicesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);
        prodRepository.save(producao4);
        prodRepository.save(producao5);
        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterIndice(9999, 2000, 2023),
                "Id do Docente não está registrado");
    }

    @Test
    public void deveGerarErroDataInicialNullObterIndicesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);
        prodRepository.save(producao4);
        prodRepository.save(producao5);
        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterIndice(salvo2.getId(), null, 2023),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataFinalNullObterIndicesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);
        prodRepository.save(producao4);
        prodRepository.save(producao5);
        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterIndice(salvo2.getId(), 2000, null),
                "Data Inválida");
    }

    @Test
    public void deveGerarErroDataInicialMaiorQueaFinalObterIndicesPeloIdDocenteEIntervaloDeTempo(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
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
                                                .percentileOuH5(7f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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
                                                .percentileOuH5(2f)
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

        prodRepository.save(producao1);
        prodRepository.save(producao2);
        prodRepository.save(producao3);
        prodRepository.save(producao4);
        prodRepository.save(producao5);
        //Ação

        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.obterIndice(salvo2.getId(), 2023, 2000),
                "Data inical maior que a data final");
    }

    @Test
    public void deveSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        //Ação
        Docente doc = service.salvarDocente(docente1);

        //Verificação
        Assertions.assertNotNull(doc);
        Assertions.assertEquals(docente1.getId(), doc.getId());
        Assertions.assertEquals(docente1.getNome(), doc.getNome());
        Assertions.assertEquals(docente1.getLattes(), doc.getLattes());
        Assertions.assertEquals(docente1.getDataAtualizacao(), doc.getDataAtualizacao());
    }

    @Test
    public void deveGerarErroNomeVazioSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().nome("")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.salvarDocente(docente1),
                "Nome inválido");
    }

    @Test
    public void deveGerarErroNomeNullSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().nome(null)
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.salvarDocente(docente1),
                "Nome inválido");
    }

    @Test
    public void deveGerarErroLattesVazioSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("")
                                            .build();
        
        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.salvarDocente(docente1),
                "Lattes inválido");
    }

    @Test
    public void deveGerarErroLattesNullSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes(null)
                                            .build();
        
        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.salvarDocente(docente1),
                "Lattes inválido");
    }

    @Test
    public void deveGerarErroIdNullSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().id(null)
                                            .nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.salvarDocente(docente1),
                "Id inválido");
    }

    @Test
    public void deveGerarErroIdExistenteSalvarDocente(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Docente docente2 = Docente.builder().nome("docente2")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes2")
                                            .build();
                            
        Docente doc = docRepository.save(docente2);

        docente1.setId(doc.getId());
        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
                () -> service.salvarDocente(docente1),
                "Id já registrado");
    }

    @Test
    public void deveObterDocentePorId(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                .dataAtualizacao(new Date())
                .lattes("lattes1")
                .build();

        Docente docente2 = Docente.builder().nome("docente2")
                .dataAtualizacao(new Date())
                .lattes("lattes2")
                .build();

        Docente doc = docRepository.save(docente2);
        docRepository.save(docente1);

        //Ação
        Optional<Docente> doc2 = service.obterDocente(doc.getId());

        //Verificação
        Assertions.assertNotNull(doc2.get());
        Assertions.assertEquals(doc.getId(),doc2.get().getId());
        Assertions.assertEquals(doc.getNome(),doc2.get().getNome());
        Assertions.assertEquals(doc.getLattes(),doc2.get().getLattes());
    }
    
    @Test
    public void deveGerarErroIdNullObterDocentePorId(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                .dataAtualizacao(new Date())
                .lattes("lattes1")
                .build();

        Docente docente2 = Docente.builder().nome("docente2")
                .dataAtualizacao(new Date())
                .lattes("lattes2")
                .build();

        Docente doc = docRepository.save(docente2);
        docRepository.save(docente1);

        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
        () -> service.obterDocente(null),
        "Id inválido");

    }

    @Test
    public void deveGerarErroIdInexistenteObterDocentePorId(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                .dataAtualizacao(new Date())
                .lattes("lattes1")
                .build();

        Docente docente2 = Docente.builder().nome("docente2")
                .dataAtualizacao(new Date())
                .lattes("lattes2")
                .build();

        Docente doc = docRepository.save(docente2);
        docRepository.save(docente1);

        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
        () -> service.obterDocente(0),
        "Id do Docente não está registrado");

    }

    @Test
    public void deveObterDocentesPorNome(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente")
                .dataAtualizacao(new Date())
                .lattes("lattes1")
                .build();

        Docente docente2 = Docente.builder().nome("docente")
                .dataAtualizacao(new Date())
                .lattes("lattes2")
                .build();

        Docente doc = docRepository.save(docente2);
        docRepository.save(docente1);

        //Ação
        List<Docente> listaDoc = service.obterDocentesNome(doc.getNome());

        //Verificação
        Assertions.assertNotNull(listaDoc);
        Assertions.assertEquals(2, listaDoc.size());
        Assertions.assertEquals(doc.getNome(), listaDoc.get(0).getNome());
        Assertions.assertEquals(doc.getNome(), listaDoc.get(1).getNome());
    }

    @Test
    public void deveGerarErroNomeNullObterDocentesPorNome(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente")
                .dataAtualizacao(new Date())
                .lattes("lattes1")
                .build();

        Docente docente2 = Docente.builder().nome("docente")
                .dataAtualizacao(new Date())
                .lattes("lattes2")
                .build();

        Docente doc = docRepository.save(docente2);
        docRepository.save(docente1);

        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
        () -> service.obterDocentesNome(null),
        "Nome inválido");
    }

    @Test
    public void deveGerarErroNomeVazioObterDocentesPorNome(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente")
                .dataAtualizacao(new Date())
                .lattes("lattes1")
                .build();

        Docente docente2 = Docente.builder().nome("docente")
                .dataAtualizacao(new Date())
                .lattes("lattes2")
                .build();

        Docente doc = docRepository.save(docente2);
        docRepository.save(docente1);

        //Ação
        Assertions.assertThrows(ServicoRuntimeException.class,
        () -> service.obterDocentesNome(""),
        "Nome inválido");
    }
}