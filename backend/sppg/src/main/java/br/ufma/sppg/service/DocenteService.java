package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.*;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository repository;

    public List<Docente> obterDocentes() {
        return repository.findAll();
    }

    public Indice obterIndice(Integer idDocente, Integer anoIni, Integer anoFim){ 
        verificarId(idDocente);
        verificarData(anoIni, anoFim);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = new ArrayList<>();

        producoes = repository.obterProducoes(idDocente, anoIni, anoFim);

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

    public List<Producao> obterProducoes(Integer idDocente, Integer anoIni, Integer anoFim){
        verificarId(idDocente);
        verificarData(anoIni, anoFim);

        return repository.obterProducoes(idDocente, anoIni, anoFim);

    }

    public List<Orientacao> obterOrientacoes(Integer idDocente, Integer anoIni, Integer anoFim){
        verificarId(idDocente);
        verificarData(anoIni, anoFim);

        return repository.obterOrientacoes(idDocente, anoIni, anoFim);

    }

    public List<Tecnica> obterTecnicas(Integer idDocente, Integer anoIni, Integer anoFim){
        verificarId(idDocente);
        verificarData(anoIni, anoFim);

        return repository.obterTecnicas(idDocente, anoIni, anoFim);

    }

    @Transactional
    public Docente salvarDocente(Docente doc) {
        verificarDocente(doc);

        return repository.save(doc);
    }
    
    

    public Optional<Docente> obterDocente(Integer idDocente){
        verificarId(idDocente);

        return repository.findById(idDocente);
    }

    public List<Docente> obterDocentesNome(String nome){
        verificarPalavra(nome, "Nome inválido");

        return repository.findByNome(nome);
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

    private void verificarData(Integer anoIni, Integer anoFim){
        verificarNumero(anoIni, "Data Inválida");
        verificarNumero(anoFim, "Data Inválida");
        if(anoIni > anoFim){
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
    }

    public Optional<Docente> obterDocentePorId(Integer idDocente) {
        return repository.findById(idDocente);
    }
}