package br.ufma.sppg.model;

import java.util.Date;
import java.util.HashSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

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
  public void shouldSaveProgramaWithDocente() throws ParseException {
    Programa novoPrograma = Programa.builder()
        .nome("Programa de Pós-Graduação em Engenharia de Eletricidade - PPGEE")
        .build();
    Docente novoDocente = Docente.builder()
        .nome("Geraldo Braz Junior")
        .lattes("123").dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/05/2022"))
        .build();

    Programa programaSalvo = programaRepository.save(novoPrograma);
    Docente docenteSalvo = docenteRepository.save(novoDocente);
    List<Docente> docentes = new ArrayList<Docente>();
    docentes.add(docenteSalvo);
    programaSalvo.setDocentes(docentes);
    Programa programaSalvoV2 = programaRepository.save(programaSalvo);

    Assertions.assertNotNull(programaSalvoV2);
    Assertions.assertEquals(programaSalvoV2.getDocentes().size(), 1);

  }
  
  @Test
  public void shouldAvoidDeleteProgramaWithDocente() throws ParseException {
    Programa novoPrograma = Programa.builder()
        .nome("Programa de Pós-Graduação em Engenharia de Eletricidade - PPGEE")
        .build();
    Docente novoDocente = Docente.builder()
        .nome("Geraldo Braz Junior")
        .lattes("123").dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/05/2022"))
        .build();

    Programa programaSalvo = programaRepository.save(novoPrograma);
    Docente docenteSalvo = docenteRepository.save(novoDocente);
    List<Docente> docentes = new ArrayList<Docente>();
    docentes.add(docenteSalvo);
    programaSalvo.setDocentes(docentes);
    Programa programaSalvoV2 = programaRepository.save(programaSalvo);
    Assertions.assertNotNull(programaSalvoV2);

    try {
      programaRepository.delete(programaSalvoV2);
      Assertions.fail("Should not delete programa with docente");
    } catch (DataIntegrityViolationException e) {
      // TODO: handle exception
    }

    
    Optional<Programa> optionalPrograma = programaRepository.findById(programaSalvoV2.getId());
    Assertions.assertNotNull(optionalPrograma.isPresent());
    List<Docente> foundDocentes = docenteRepository.findAllById(Collections.singleton(docenteSalvo.getId()));
    Assertions.assertFalse(foundDocentes.isEmpty());
    


  }

  
}
