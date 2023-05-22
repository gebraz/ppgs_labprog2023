package br.ufma.sppg.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;

@SpringBootTest
public class ProgramaServiceTest {

    @Autowired
    ProgramaService service;

    @Test
    public void deveInformarIntervaloDeTempo() {
        // Cenário
        Integer ini;
        Integer fim;

        // Ação
        ini = 2020;
        fim = 2023;

        // Teste
        Assertions.assertNotNull(ini);
        Assertions.assertNotNull(fim);
        Assertions.assertInstanceOf(Integer.class, ini);
        Assertions.assertInstanceOf(Integer.class, fim);
    }

    @Test
    public void deveRecuperaraProgramaPeloIdEIntervaloDeTempo() {
        // Cenário
        Integer id, ini, fim;

        // Ação
        id = 1;
        ini = 2020;
        fim = 2023;
        // service.obterPrograma(id, ini, fim);

        // Teste
        Assertions.fail("Método não implementado na camada de serviço.");
    }

    @Test
    public void deveRecuperarDocentesDoProgramaPeloIdEIntervaloDeTempo() {
        // Cenário
        Integer id = 1, ini = 2020, fim = 2023;

        // Ação
        List<Docente> docentes = service.obterDocentesPrograma(id);

        // Teste
        Assertions.assertNotNull(docentes);
        Assertions.assertTrue(docentes.size() > 0);

    }

    /*
     * dado um docente com produções e este docente num programa,
     * deve recuperar o *programa* com a lista correta de produções
     * num intervalo de tempo
     */
    @Test
    public void deveRecuperarOProgramaComProducoesNumIntervaloDeTempo() {

        // Cenário
        Integer idPPG = 1, ini = 2020, fim = 2023;

        // Ação
        List<Producao> ppg = service.obterProducoes(idPPG, ini, fim);

        // Teste
        Assertions.assertInstanceOf(Programa.class, ppg);
        Assertions.assertNotNull(ppg);
        Assertions.assertTrue(ppg.size() > 0); // todo: trocar para ppg.getProd.size()
        for (Producao prod : ppg) {
            Assertions.assertTrue(prod.getAno() >= ini && prod.getAno() <= fim);
        }
    }

    @Test
    public void deveRecuperarOProgramaComOrientacoesNumIntervaloDeTempo() {

        // Cenário
        Integer idPPG = 1, ini = 2020, fim = 2023;

        // Ação
        List<Orientacao> ppg = service.obterOrientacoes(idPPG, ini, fim);

        // Teste
        Assertions.assertInstanceOf(Programa.class, ppg);
        Assertions.assertNotNull(ppg);
        Assertions.assertTrue(ppg.size() > 0); // todo: trocar para ppg.getProd.size()
        for (Orientacao prod : ppg) {
            Assertions.assertTrue(prod.getAno() >= ini && prod.getAno() <= fim);
        }
    }

    @Test
    public void deveRecuperarOProgramaComTecnicasNumIntervaloDeTempo() {

        // Cenário
        Integer idPPG = 1, ini = 2020, fim = 2023;

        // Ação
        List<Tecnica> ppg = service.obterTecnicas(idPPG, ini, fim);

        // Teste
        Assertions.assertInstanceOf(Programa.class, ppg);
        Assertions.assertNotNull(ppg);
        Assertions.assertTrue(ppg.size() > 0); // todo: trocar para ppg.getProd.size()
        for (Tecnica prod : ppg) {
            Assertions.assertTrue(prod.getAno() >= ini && prod.getAno() <= fim);
        }
    }

    @Test
    public void deveCalcularIndiceDeAcordoComOEsperadoParaProgramaNumIntervaloDeTempo() {
        // Cenário
        // neste caso estou utilizando
        // um banco de dados de teste
        // com um docente que tem todos os
        // tipos de qualis
        Integer id = 3, ini = 2020, fim = 2023;

        // Ação
        Indice indice = service.obterProducaoIndices(id, ini, fim);

        // Teste
        Assertions.assertNotNull(indice);

        // dica:
        // caso a aplicação não passe no teste
        // o problema pode ser o fato de no serviço
        // estar sendo utilizado double como ponto flutuante
        // e não com objeto, por isso a falta de precisão
        // nos calculos
        Assertions.assertEquals(indice.getIndiceGeral(), Double.valueOf(4.1));
    }
}
