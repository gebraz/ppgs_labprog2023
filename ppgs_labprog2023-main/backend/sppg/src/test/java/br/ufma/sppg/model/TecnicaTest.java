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

    /*@Test
    public void deveImpedirRemoverTecnicaComDependencia() throws ParseException{
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

    }*/

    // @Test
    // public void deveVerificarRemoverPrograma() throws ParseException{
    //     //cenário
    //     Tecnica novTecnica = Tecnica.builder().id(1).tipo("teste_tipo").titulo("teste_titulo")
    //                                         .ano(2023)
    //                                         .financiadora("teste_financiadora")
    //                                         .outrasInformacoes("teste_outrasInformacoes")
    //                                         .qtdGrad(1)
    //                                         .qtdMestrado(2)
    //                                         .qtdDoutorado(3).build();
        
    //     Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
    //                                         .lattes("123")
    //                                         .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
    //                                         .build();


    //     Orientacao novOrientacao = Orientacao.builder().id(1).tipo("teste").discente("teste_disc")
    //                                         .titulo("teste_titulo")
    //                                         .ano(2023)
    //                                         .modalidade("teste_modalidade")
    //                                         .instituicao("teste_orientacao")
    //                                         .curso("teste_curso")
    //                                         .status("teste_status")
    //                                         .orientador(novDocente).build();
                                            

    //     //ação
    //     Tecnica tecSalva = tec.save(novTecnica);
    //     Integer id = tecSalva.getId();
    //     tec.deleteById(tecSalva.getId());

    //     //verificação
        
    //     Optional<Tecnica> temp = tec.findById(id);        
    //     Assertions.assertFalse(temp.isPresent());
    // }
    

}
