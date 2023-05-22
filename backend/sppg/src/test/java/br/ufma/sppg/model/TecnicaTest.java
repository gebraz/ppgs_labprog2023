package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.TecnicaRepository;

@SpringBootTest
public class TecnicaTest {

    @Autowired
    TecnicaRepository repositoryTec;

    @Autowired
    OrientacaoRepository repositoryOri;

    @Test
    public void deveSalvarTecnica() {
        // Cenario
        Tecnica tecnica = Tecnica.builder().tipo("tipo1")
                .titulo("titulo1")
                .ano(1111)
                .financiadora("financiadora1")
                .outrasInformacoes("informações1")
                .qtdGrad(1)
                .qtdDoutorado(11)
                .qtdMestrado(111)
                .build();

        // Ação
        Tecnica salvo = repositoryTec.save(tecnica);

        // Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(tecnica.getId(), salvo.getId());
        Assertions.assertEquals(tecnica.getTipo(), salvo.getTipo());
        Assertions.assertEquals(tecnica.getTitulo(), salvo.getTitulo());
        Assertions.assertEquals(tecnica.getAno(), salvo.getAno());
        Assertions.assertEquals(tecnica.getFinanciadora(), salvo.getFinanciadora());
        Assertions.assertEquals(tecnica.getOutrasInformacoes(), salvo.getOutrasInformacoes());
        Assertions.assertEquals(tecnica.getQtdGrad(), salvo.getQtdGrad());
        Assertions.assertEquals(tecnica.getQtdDoutorado(), salvo.getQtdDoutorado());
        Assertions.assertEquals(tecnica.getQtdMestrado(), salvo.getQtdMestrado());
    }

    @Test
    public void deveAtualizarEstatisticasTecnica() {
        // Cenario
        Tecnica tecnicaG = Tecnica.builder().tipo("tipo1")
                .titulo("titulo1")
                .ano(1111)
                .financiadora("financiadora1")
                .outrasInformacoes("informações1")
                .qtdGrad(1)
                .qtdDoutorado(11)
                .qtdMestrado(111)
                .build();

        Tecnica tecnicaD = Tecnica.builder().tipo("tipo2")
                .titulo("titulo2")
                .ano(2222)
                .financiadora("financiadora2")
                .outrasInformacoes("informações2")
                .qtdGrad(2)
                .qtdDoutorado(22)
                .qtdMestrado(222)
                .build();

        Tecnica tecnicaM = Tecnica.builder().tipo("tipo3")
                .titulo("titulo3")
                .ano(3333)
                .financiadora("financiadora3")
                .outrasInformacoes("informações3")
                .qtdGrad(3)
                .qtdDoutorado(33)
                .qtdMestrado(333)
                .build();
        // Ação
        repositoryTec.save(tecnicaG);
        repositoryTec.save(tecnicaM);
        repositoryTec.save(tecnicaD);

        tecnicaG.setQtdGrad(123);
        tecnicaM.setQtdMestrado(456);
        tecnicaD.setQtdDoutorado(789);

        repositoryTec.save(tecnicaG);
        repositoryTec.save(tecnicaM);
        repositoryTec.save(tecnicaD);

        // Verificação
        Assertions.assertEquals(repositoryTec.findById(tecnicaG.getId()).isPresent(), true);
        Assertions.assertEquals(repositoryTec.findById(tecnicaM.getId()).isPresent(), true);
        Assertions.assertEquals(repositoryTec.findById(tecnicaD.getId()).isPresent(), true);
        Assertions.assertEquals(repositoryTec.findById(tecnicaG.getId()).get().getQtdGrad(), 123);
        Assertions.assertEquals(repositoryTec.findById(tecnicaM.getId()).get().getQtdMestrado(), 456);
        Assertions.assertEquals(repositoryTec.findById(tecnicaD.getId()).get().getQtdDoutorado(), 789);
    }
}
