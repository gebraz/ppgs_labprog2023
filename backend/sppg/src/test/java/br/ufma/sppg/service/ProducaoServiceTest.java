package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ProducaoServiceTest {
    @Autowired
    ProducaoService service;

    @Autowired
    ProducaoRepository producaoRepo;

    @Autowired 
    DocenteRepository docenteRepo;

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
    public void deveRetornarProducaoPorDocente() {
        //criar uma producao e um docente, associar o docente a producao e depois recuperar a producao associada ao docente
        // Cenário
        Producao producaoSalvar = Producao.builder().tipo("teste").ano(2020).build();
        List<Producao> producoes = new ArrayList<Producao>();
        producoes.add(producaoSalvar);
        Docente docenteSalvar = Docente.builder().nome("João").producoes(producoes).build();

        // Ação
        producaoRepo.save(producaoSalvar);
        docenteRepo.save(docenteSalvar);
        Docente docenteRecuperado = docenteRepo.findById(docenteSalvar.getId()).get();
        Producao producaoRecuperada = docenteRecuperado.getProducoes().get(0);
        List<Producao> producoesObtidas = service.obterProducoesDocente(docenteRecuperado.getId(), 2019, 2021);

        // Teste
        Assertions.assertEquals(producaoRecuperada, producoesObtidas.get(0));
        
    }
}
