package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProgramaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class DocenteTest {
    
    @Autowired
    DocenteRepository repo;

    @Autowired
    ProgramaRepository prog;


    @Test
    public void deveSalvarDocentComPrograma() throws ParseException {
        //cenário
        Programa novoPPg = Programa.builder().nome("PPGCC").build();
        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                        .build();
        
        Programa progSalvo = prog.save(novoPPg);
        Docente docSalvo = repo.save(novDocente);

        //ação
        ArrayList<Programa> programas = new ArrayList<>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas);
        
        Docente docSalvo2 = repo.save(docSalvo);

        //teste
        Assertions.assertNotNull(docSalvo2);
        Assertions.assertEquals(docSalvo2.getProgramas().size(), 1);

    }
}
