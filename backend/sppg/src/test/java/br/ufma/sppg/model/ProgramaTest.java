package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.ProgramaRepository;

@SpringBootTest
public class ProgramaTest {

  @Autowired
  private ProgramaRepository programaRepository;

  @Test
  public void shouldSavePrograma() {
    Programa novoPrograma = Programa.builder()
      .nome("Programa de Pós-Graduação em Engenharia de Eletricidade - PPGEE")
        .build();
      
    Programa programaSalvo = programaRepository.save(novoPrograma);

    Assertions.assertNotNull(programaSalvo);

  }
  
  
}
