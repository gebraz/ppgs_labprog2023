package br.ufma.sppg.model;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.TecnicaRepository;

@SpringBootTest
public class OrientacaoTest {

    @Autowired
    OrientacaoRepository oriRepo;

    @Autowired
    ProducaoRepository prodRepo;

    @Autowired
    TecnicaRepository tecRepo;

    @Test
    public void deveSalvarOrientacao(){

        Orientacao novaOri = Orientacao.builder()
                                        .tipo("Teste")
                                        .discente("Aluno")
                                        .titulo("Testando Orientacao")
                                        .ano(2023)
                                        .modalidade("Teste")
                                        .instituicao("UFMA")
                                        .curso("Computacao")
                                        .status("Em Progresso")
                                        .build();
        
        Orientacao oriSalva = oriRepo.save(novaOri);

        Assertions.assertNotNull(oriSalva);


    }

    @Test
    public void deveSalvarOrientacaoComTecnica(){
        
        Orientacao novaOri = Orientacao.builder()
                                        .tipo("Teste")
                                        .discente("Aluno")
                                        .titulo("Testando Orientacao")
                                        .ano(2023)
                                        .modalidade("Teste")
                                        .instituicao("UFMA")
                                        .curso("Computacao")
                                        .status("Em Progresso")
                                        .build();

        Tecnica novaTec = Tecnica.builder()
                            .tipo("Teste")
                            .titulo("TecTeste")
                            .ano(2023)
                            .financiadora("Ciclana")
                            .outrasInformacoes("Etc")
                            .qtdGrad(3)
                            .qtdMestrado(2)
                            .qtdDoutorado(1)
                            .build();

        Orientacao oriSalva = oriRepo.save(novaOri);

        Tecnica tecSalva = tecRepo.save(novaTec);

        ArrayList<Tecnica> tecnicas = new ArrayList<>();
        tecnicas.add(tecSalva);
        oriSalva.setTecnicas(tecnicas);

        Orientacao oriSalvaComTecnica = oriRepo.save(oriSalva);

        Assertions.assertNotNull(tecSalva);
        Assertions.assertNotNull(oriSalvaComTecnica);
        Assertions.assertEquals(oriSalvaComTecnica.getTecnicas().size(),1);


    }

    @Test
    public void deveSalvarOrientacaoComProducao(){

        Orientacao novaOri = Orientacao.builder()
                                        .tipo("Teste")
                                        .discente("Aluno")
                                        .titulo("Testando Orientacao")
                                        .ano(2023)
                                        .modalidade("Teste")
                                        .instituicao("UFMA")
                                        .curso("Computacao")
                                        .status("Em Progresso")
                                        .build();

        Producao novaProd = Producao.builder()
                                    .tipo("Teste")
                                    .issnOuSigla("Teste")
                                    .nomeLocal("Teste")
                                    .titulo("Testando")
                                    .ano(2023)
                                    .qualis("teste")
                                    .percentileOuH5(2)
                                    .qtdGrad(3)
                                    .qtdMestrado(2)
                                    .qtdDoutorado(1)
                                    .build();
        
        Orientacao oriSalva = oriRepo.save(novaOri);

        Producao prodSalva = prodRepo.save(novaProd);

        ArrayList<Producao> producoes = new ArrayList<>();
        producoes.add(prodSalva);
        oriSalva.setProducoes(producoes);

        Orientacao oriSalvaComProducao = oriRepo.save(oriSalva);

        Assertions.assertNotNull(prodSalva);
        Assertions.assertNotNull(oriSalvaComProducao);
        Assertions.assertEquals(oriSalvaComProducao.getProducoes().size(),1);

    }

    @Test
    public void deveImpedirRemoverOrientacaoComDependencia(){

        Orientacao novaOri = Orientacao.builder()
                                        .tipo("Teste")
                                        .build();

        Producao novaProd = Producao.builder()
                                    .tipo("Teste")
                                    .build();
        
        Orientacao oriSalva = oriRepo.save(novaOri);

        Producao prodSalva = prodRepo.save(novaProd);

        ArrayList<Producao> producoes = new ArrayList<>();
        producoes.add(prodSalva);
        oriSalva.setProducoes(producoes);
        
        oriRepo.save(oriSalva);
        Orientacao ori = oriRepo.getReferenceById(oriSalva.getId());

        oriRepo.delete(ori);
        Assertions.assertNotNull(oriRepo.getReferenceById(oriSalva.getId()));

        
    }

}
