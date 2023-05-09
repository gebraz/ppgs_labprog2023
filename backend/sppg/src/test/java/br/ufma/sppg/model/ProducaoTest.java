package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.ProducaoRepository;

import java.text.ParseException;

@SpringBootTest
public class ProducaoTest {

  @Autowired
  ProducaoRepository repo;

  @Test
  public void deveSalvarProducao() throws ParseException {

    // cenário
    Producao novaProducao = Producao.builder().tipo("tipo_teste")
        .ano(2019)
        .issnOuSigla("sigla_teste")
        .nomeLocal("nome_local_teste")
        .titulo("titulo_teste")
        .qualis("B1")
        .qtdGrad(900)
        .qtdMestrado(300)
        .qtdDoutorado(10)
        .percentileOuH5(10)
        .build();

    // Ação
    Producao savedProd = repo.save(novaProducao);

    // Testes
    Assertions.assertNotNull(savedProd);
    Assertions.assertEquals(novaProducao.getTipo(), savedProd.getTipo());
    Assertions.assertEquals(novaProducao.getAno(), savedProd.getAno());
    Assertions.assertEquals(novaProducao.getIssnOuSigla(), savedProd.getIssnOuSigla());
    Assertions.assertEquals(novaProducao.getNomeLocal(), savedProd.getNomeLocal());
    Assertions.assertEquals(novaProducao.getTitulo(), savedProd.getTitulo());
    Assertions.assertEquals(novaProducao.getQualis(), savedProd.getQualis());
    Assertions.assertEquals(novaProducao.getQtdGrad(), savedProd.getQtdGrad());
    Assertions.assertEquals(novaProducao.getQtdMestrado(), savedProd.getQtdMestrado());
    Assertions.assertEquals(novaProducao.getQtdDoutorado(), savedProd.getQtdDoutorado());
    Assertions.assertEquals(novaProducao.getPercentileOuH5(), savedProd.getPercentileOuH5());

  }

  @Test
  public void deveAtualizarProducao() {

    // Cenário
    Producao novaProducao = Producao.builder().tipo("tipo_testeX")
        .ano(2019)
        .issnOuSigla("sigla_testePRE")
        .nomeLocal("nome_local_teste")
        .titulo("titulo_teste")
        .qualis("B1")
        .qtdGrad(900)
        .qtdMestrado(300)
        .qtdDoutorado(10)
        .percentileOuH5(10)
        .build();

    Producao initialSavedProd = repo.save(novaProducao);
    // Aqui considera-se que tudo deu certo na criação de uma nova produção
    // uma vez que o teste é sobre atualização

    Producao producaoAtt = new Producao();

    BeanUtils.copyProperties(initialSavedProd, producaoAtt);

    producaoAtt.setTipo("producao_atualizada");
    producaoAtt.setAno(2020);
    producaoAtt.setIssnOuSigla("issn_atualizado");
    producaoAtt.setNomeLocal("nome_local_teste_atualizado");
    producaoAtt.setTitulo("titulo_atualizado");

    // Ação

    producaoAtt.setId(initialSavedProd.getId());
    Producao attSavedProd = repo.save(producaoAtt);

    // Testes

    Assertions.assertNotNull(attSavedProd);

    Assertions.assertEquals(initialSavedProd.getId(), attSavedProd.getId());
    Assertions.assertNotEquals(initialSavedProd.getTipo(), attSavedProd.getTipo());
    Assertions.assertNotEquals(initialSavedProd.getIssnOuSigla(), attSavedProd.getIssnOuSigla());
    Assertions.assertNotEquals(initialSavedProd.getNomeLocal(), attSavedProd.getNomeLocal());
    Assertions.assertNotEquals(initialSavedProd.getTitulo(), attSavedProd.getTitulo());
    Assertions.assertNotEquals(initialSavedProd.getAno(), attSavedProd.getAno());
    Assertions.assertEquals(initialSavedProd.getQualis(), attSavedProd.getQualis());
    Assertions.assertEquals(initialSavedProd.getPercentileOuH5(), attSavedProd.getPercentileOuH5());
    Assertions.assertEquals(initialSavedProd.getQtdGrad(), attSavedProd.getQtdGrad());
    Assertions.assertEquals(initialSavedProd.getQtdMestrado(), attSavedProd.getQtdMestrado());
    Assertions.assertEquals(initialSavedProd.getQtdDoutorado(), attSavedProd.getQtdDoutorado());

  }
}
