package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.AllQualisProd;
import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.dto.QualisDto;
import br.ufma.sppg.model.*;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;


@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository repository;
    @Autowired
    ProducaoRepository prodRepo;
    
    public List<?> allqualis(Integer anoIni,Integer anoFim){
        var response = prodRepo.obterContagemQualisPorAno(anoIni, anoFim);
        return response;
    }

    public String obterDocentePeloIdNome(Integer Id){
        var docente= repository.findById(Id).get();
        return docente.getNome();
    }
     
    public Indice obterIndice(Integer idDocente, Integer anoIni, Integer anoFin){ 
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = new ArrayList<>();

        producoes = repository.obterProducoes(idDocente, anoIni, anoFin);

            for(Producao producao : producoes){
                    
                switch (producao.getQualis()) {
                    case "A1":
                        iRestrito += 1.0f;
                        break;
                            
                    case "A2":
                        iRestrito += 0.85;
                        break;

                    case "A3":
                        iRestrito += 0.725;
                        break;

                    case "A4":
                        iRestrito += 0.625;
                        break;

                    case "B1":
                        iNRestrito += 0.5;
                        break;

                    case "B2":
                        iNRestrito += 0.25;
                        break;

                    case "B3":
                        iNRestrito += 0.1;
                        break;
                        
                    case "B4":
                        iNRestrito += 0.05;
                        break;
                    
                    default:
                        throw new ServicoRuntimeException("Uma das produções possui o Qualis inválido");
                    }
            }

        iGeral = iRestrito + iNRestrito;

        return new Indice(iRestrito, iNRestrito, iGeral);
    }

    public List<Producao> obterProducoes(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterProducoes(idDocente, anoIni, anoFin);

    }

    public List<Orientacao> obterOrientacoes(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterOrientacoes(idDocente, anoIni, anoFin);

    }

    public List<Tecnica> obterTecnicas(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterTecnicas(idDocente, anoIni, anoFin);

    }
    public Indice obterIndicadores(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        Integer a1 = repository.obterQualis(idDocente, anoIni, anoFin, "A1").get();
        Integer a2 = repository.obterQualis(idDocente, anoIni, anoFin, "A2").get();
        Integer a3 = repository.obterQualis(idDocente, anoIni, anoFin, "A3").get();
        Integer a4 = repository.obterQualis(idDocente, anoIni, anoFin, "A4").get();
        Integer b1 = repository.obterQualis(idDocente, anoIni, anoFin, "B1").get();
        Integer b2 = repository.obterQualis(idDocente, anoIni, anoFin, "B2").get();
        Integer b3 = repository.obterQualis(idDocente, anoIni, anoFin, "B3").get();
        Integer b4 = repository.obterQualis(idDocente, anoIni, anoFin, "B4").get();

        Double iRestrito = Double.valueOf(a1) + Double.valueOf(a2)*Double.valueOf(0.85) + Double.valueOf(a3)*Double.valueOf(0.725) + Double.valueOf(a4)*Double.valueOf(0.625);
        Double iNRestrito = Double.valueOf(b1)*Double.valueOf(0.5) + Double.valueOf(b2)*Double.valueOf(0.25) + Double.valueOf(b3)*Double.valueOf(0.1) + Double.valueOf(b4)*Double.valueOf(0.05);
        Double iGeral = Double.valueOf(iRestrito) + Double.valueOf(iNRestrito);
        Integer totalProd = a1 + a2 + a3 + a4 + b1 + b2 + b3 + b4;
        
        return new Indice(iGeral, iRestrito  , iNRestrito  , totalProd );
    }

    public QualisDto obterQualis(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        var docente = repository.findById(idDocente);
        Integer a1 = repository.obterQualis(idDocente, anoIni, anoFin, "A1").orElse(0);
        Integer a2 = repository.obterQualis(idDocente, anoIni, anoFin, "A2").orElse(0);
        Integer a3 = repository.obterQualis(idDocente, anoIni, anoFin, "A3").orElse(0);
        Integer a4 = repository.obterQualis(idDocente, anoIni, anoFin, "A4").orElse(0);
        Integer b1 = repository.obterQualis(idDocente, anoIni, anoFin, "B1").orElse(0);
        Integer b2 = repository.obterQualis(idDocente, anoIni, anoFin, "B2").orElse(0);
        Integer b3 = repository.obterQualis(idDocente, anoIni, anoFin, "B3").orElse(0);
        Integer b4 = repository.obterQualis(idDocente, anoIni, anoFin, "B4").orElse(0);
    
        var response = new QualisDto(idDocente,docente.get().getNome(), a1, a2, a3, a4, b1, b2, b3, b4);
        return response;
    }

    public List<QualisDto> obtertTodosQualis(Integer anoIni, Integer anoFim){
        var docentes = repository.findAll();
        List<QualisDto> lista = new ArrayList<>();
       
        for(Docente d : docentes){
            var response = obterQualis(d.getId(),anoIni, anoFim);
            lista.add(response);
        }
        return lista;
    }

    public AllQualisProd obterQualisPorTipo(Integer idDocente, Integer anoIni, Integer anoFin, String tipoQualis) {
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        
        List<Integer> anos = new ArrayList<>();
        List<Integer> a1 = new ArrayList<>();
        List<Integer> a2 = new ArrayList<>();
        List<Integer> a3 = new ArrayList<>();
        List<Integer> a4 = new ArrayList<>();
        
        var prodDocente = repository.obterProducoes(idDocente, anoIni, anoFin);
        
       
        int numAnos = anoFin - anoIni + 1;
        
     
        for (int i = 0; i < numAnos; i++) {
            anos.add(anoFin - i);
            a1.add(0);
            a2.add(0);
            a3.add(0);
            a4.add(0);
        }
    
        for (Producao p : prodDocente) {
            if (p.getAno() >= anoIni && p.getAno() <= anoFin) {
                if (p.getTipo() != null && p.getTipo().equals(tipoQualis)) {
                    if (p.getQualis() != null) {
                        int index = anoFin - p.getAno();
                        if (p.getQualis().equals("A1")) {
                            a1.set(index, a1.get(index) + 1);
                        } else if (p.getQualis().equals("A2")) {
                            a2.set(index, a2.get(index) + 1);
                        } else if (p.getQualis().equals("A3")) {
                            a3.set(index, a3.get(index) + 1);
                        } else if (p.getQualis().equals("A4")) {
                            a4.set(index, a4.get(index) + 1);
                        }
                    }
                }
            }
        }
    
        return new AllQualisProd(anos, a1, a2, a3, a4);
    }



    @Transactional
    public Docente salvarDocente(Docente doc){
        verificarDocente(doc);

        return repository.save(doc);
    }

    public Optional<Docente> obterDocente(Integer idDocente){
        verificarId(idDocente);

        return repository.findById(idDocente);
    }

    public List<Docente> obterDocentesNome(String nome){
        verificarPalavra(nome, "Nome inválido");

        return repository.obterbyNome(nome);
    }

    private void verificarPalavra(String nome, String mensagem){
        if(nome == null){
            throw new ServicoRuntimeException(mensagem);
        }
        if(nome.trim().equals("")){
            throw new ServicoRuntimeException(mensagem);
        }
    }

    private void verificarId(Integer idDocente){
        verificarNumero(idDocente, "Id Inválido");
        if(!repository.existsById(idDocente)){
            throw new ServicoRuntimeException("Id do Docente não está registrado");
        }
    }

    private void verificarData(Integer data1, Integer data2){
        verificarNumero(data1, "Data Inválida");
        verificarNumero(data2, "Data Inválida");
        if(data1 > data2){
            throw new ServicoRuntimeException("Data inical maior que a data final");
        }
    }

    private void verificarNumero(Integer numero, String mensagem){
        if(numero == null){
            throw new ServicoRuntimeException(mensagem);
        }

    }

    private void verificarDocente(Docente docente){
        verificarPalavra(docente.getNome(), "Nome inválido");
        verificarPalavra(docente.getLattes(), "Lattes inválido");
        verificarNumero(docente.getId(), "Id inválido");
        if(repository.existsById(docente.getId())){
            throw new ServicoRuntimeException("Id já registrado");
        }
        /*
        if(docente.getOrientacoes() == null){
            throw new ServicoRuntimeException("Lista de orientações inexistente");
        }
        if(docente.getTecnicas() == null){
            throw new ServicoRuntimeException("Lista de tecnicas inexistente");
        }
        if(docente.getProducoes() == null){
            throw new ServicoRuntimeException("Lista de produções inexistente");
        }
        if(docente.getProgramas() == null){
            throw new ServicoRuntimeException("Lista de programas inexistente");
        }
        */
    }
}