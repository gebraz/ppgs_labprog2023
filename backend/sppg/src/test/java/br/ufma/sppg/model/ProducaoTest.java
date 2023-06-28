package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import br.ufma.sppg.repo.ProducaoRepository;
import java.text.ParseException;
import java.util.Optional;



@SpringBootTest
public class ProducaoTest {
    
    @Autowired
    ProducaoRepository prod;

    @Test
    public void deveSalvarProducao() throws ParseException {
        //cenário
        
        Producao novaProducao = Producao.builder().id(1).tipo("A").ano(2022).issnOuSigla("ABC")
                                .nomeLocal("Ufma").titulo("Classificação de Cancer de mama")
                                .qualis("qualisemxlm").percentileOuH5(2).qtdGrad(10)
                                .qtdMestrado(20).qtdDoutorado(10).build();                                  
        //acao
        Producao prodSalvo = prod.save(novaProducao);

        //teste
        Assertions.assertNotNull(prodSalvo);
        Assertions.assertEquals(novaProducao,prodSalvo);
        Assertions.assertEquals(novaProducao.getId(), prodSalvo.getId());
        Assertions.assertEquals(novaProducao.getAno(), prodSalvo.getAno());
        Assertions.assertEquals(novaProducao.getPercentileOuH5(), prodSalvo.getPercentileOuH5());
        Assertions.assertEquals(novaProducao.getNomeLocal(), prodSalvo.getNomeLocal());
        Assertions.assertEquals(novaProducao.getQualis(), prodSalvo.getQualis());
        Assertions.assertEquals(novaProducao.getQtdDoutorado(), prodSalvo.getQtdDoutorado());
        Assertions.assertEquals(novaProducao.getQtdGrad(), prodSalvo.getQtdGrad());
        Assertions.assertEquals(novaProducao.getQtdMestrado(), prodSalvo.getQtdMestrado());
        Assertions.assertEquals(novaProducao.getTipo(), prodSalvo.getTipo());
        Assertions.assertEquals(novaProducao.getTitulo(), prodSalvo.getTitulo());
        Assertions.assertEquals(novaProducao.getIssnOuSigla(), prodSalvo.getIssnOuSigla());     

    }


    
    public void deveAtualizarEstatisticasProducao() throws ParseException{
        //cenário 
        Producao novaProducao = Producao.builder().id(1).tipo("A").issnOuSigla("ABC")
                                .nomeLocal("Ufma").titulo("Classificação de Cancer de mama")
                                .qualis("qualisemxlm").percentileOuH5(2).qtdGrad(10)
                                .qtdMestrado(20).qtdDoutorado(10).build();    
                                   
        
        //acao
        //original
        Producao prodSalvo = prod.save(novaProducao);
        //verificando se salvou
        Assertions.assertNotNull(prodSalvo.getId());
        Assertions.assertNotNull(prodSalvo);

        //atualizando componentes da producao
        prodSalvo.setAno(2020);
        prodSalvo.setTipo("A");
        prodSalvo.setQtdDoutorado(10);
        prodSalvo.setQualis("F");

        // Salvar a producao atualizada
        prod.save(prodSalvo);
    
        //verificacao
        //procurando a producao pelo id no repositório
        Integer id = prodSalvo.getId(); 
        Optional <Producao> temp = prod.findById(id);       
        Assertions.assertTrue(temp.isPresent());

        // atribuindo a producao encontrada a uma nova variável 
        Producao prodAtualizado =  temp.get();

        Assertions.assertNotEquals(novaProducao.getAno(),prodAtualizado.getAno());
        Assertions.assertNotEquals(novaProducao.getTipo(),prodAtualizado.getTipo());
        Assertions.assertNotEquals(novaProducao.getTitulo(),prodAtualizado.getTitulo());
        Assertions.assertNotEquals(novaProducao.getQtdDoutorado(),prodAtualizado.getQtdDoutorado());
        Assertions.assertNotEquals(novaProducao.getQualis(), prodAtualizado.getQualis());

    }
    

}
