package br.ufma.sppg.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProgramaRepository;

@SpringBootTest
public class ProgramaTest {

  @Autowired
  private ProgramaRepository programaRepository;
  @Autowired
  private DocenteRepository docenteRepository;

  @Test
  public void shouldSavePrograma() {
    Programa novoPrograma = Programa.builder()
        .nome("Programa de Pós-Graduação em Engenharia de Eletricidade - PPGEE")
        .build();

    Programa programaSalvo = programaRepository.save(novoPrograma);

    Assertions.assertNotNull(programaSalvo);

  }
  
  @Test
  public void shouldSaveProgramaWithDocente() {
    Programa novoPrograma  = Programa.builder()
        .nome("Programa de Pós-Graduação em Engenharia de Eletricidade - PPGEE")
        .build();
    Docente novoDocente = Docente.builder()
        .nome("Geraldo Braz Junior")
        .lattes("123").dataAtualizacao(new Date(0))
        .build();


    Programa programaSalvo = programaRepository.save(novoPrograma);
    Docente docenteSalvo = docenteRepository.save(novoDocente);
    List<Docente> docentes = new ArrayList<>();
    docentes.add(docenteSalvo);
    programaSalvo.setDocentes(docentes);
    Programa programaSalvoV2 = programaRepository.save(programaSalvo);
    
    Assertions.assertNotNull(programaSalvoV2);
    Assertions.assertEquals(programaSalvoV2.getDocentes().size(), 1);
      
  }
  
  
}
