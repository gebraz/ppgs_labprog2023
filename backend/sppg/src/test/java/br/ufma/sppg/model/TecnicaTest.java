package br.ufma.sppg.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.TecnicaRepository;

@SpringBootTest
public class TecnicaTest {

    @Autowired
    TecnicaRepository tec;

    @Test
    public void deveSalvarTecnica() throws ParseException {
        //cenário
        Tecnica novTecnica = Tecnica.builder().id(1).tipo("teste_tipo").titulo("teste_titulo")
                                            .ano(2023)
                                            .financiadora("teste_financiadora")
                                            .outrasInformacoes("teste_outrasInformacoes")
                                            .qtdGrad(1)
                                            .qtdMestrado(2)
                                            .qtdDoutorado(3).build();

        //ação
        Tecnica tecSalva = tec.save(novTecnica);

        //verificação
        
        Assertions.assertNotNull(tecSalva);
        Assertions.assertEquals(novTecnica.getId(), tecSalva.getId());
        Assertions.assertEquals(novTecnica.getTipo(), tecSalva.getTipo());
        Assertions.assertEquals(novTecnica.getTitulo(), tecSalva.getTitulo());
        Assertions.assertEquals(novTecnica.getAno(), tecSalva.getAno());
        Assertions.assertEquals(novTecnica.getFinanciadora(), tecSalva.getFinanciadora());
        Assertions.assertEquals(novTecnica.getOutrasInformacoes(), tecSalva.getOutrasInformacoes());
        Assertions.assertEquals(novTecnica.getQtdGrad(), tecSalva.getQtdGrad());
        Assertions.assertEquals(novTecnica.getQtdMestrado(), tecSalva.getQtdMestrado());
        Assertions.assertEquals(novTecnica.getQtdDoutorado(), tecSalva.getQtdDoutorado());
    }

    @Test
    public void deveAtualizarEstatisticasTecnica() throws ParseException{
        //cenário
        Tecnica novTecnica = Tecnica.builder().id(1).tipo("teste_tipo").titulo("teste_titulo")
                                            .ano(2023)
                                            .financiadora("teste_financiadora")
                                            .outrasInformacoes("teste_outrasInformacoes")
                                            .qtdGrad(1)
                                            .qtdMestrado(2)
                                            .qtdDoutorado(3).build();

        //ação
        //graduados
        int novaQtdGrad = novTecnica.getQtdGrad() + 1;
        novTecnica.setQtdGrad(novaQtdGrad);

        //mestrados
        int novaQtdMest = novTecnica.getQtdMestrado() + 1;
        novTecnica.setQtdMestrado(novaQtdMest);

        //doutorados
        int novaQtdDout = novTecnica.getQtdDoutorado() + 1;
        novTecnica.setQtdDoutorado(novaQtdDout);

        Tecnica tecSalva = tec.save(novTecnica);
        
        //verificação
        Assertions.assertEquals(novaQtdGrad, tecSalva.getQtdGrad());
        Assertions.assertEquals(novaQtdMest, tecSalva.getQtdMestrado());
        Assertions.assertEquals(novaQtdDout, tecSalva.getQtdDoutorado());

    }
    

}
