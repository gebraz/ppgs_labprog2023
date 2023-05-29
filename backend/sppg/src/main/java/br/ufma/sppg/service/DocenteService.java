package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.*;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;

@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository repository;

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
                        throw new RegrasRunTime("Uma das produções possui o Qualis inválido");
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

    private void verificarNome(String nome){
        if(nome == null){
            throw new RegrasRunTime("Nome do Docente inválido");
        }
        if(nome.trim().equals("")){
            throw new RegrasRunTime("Nome do Docente inválido");
        }
    }

    private void verificarId(Integer idDocente){
        verificarNumero(idDocente, "Id Inválido");
        if(!repository.existsById(idDocente)){
            throw new RegrasRunTime("Id do Docente não está registrado");
        }
    }

    private void verificarData(Integer data1, Integer data2){
        verificarNumero(data1, "Data Inválida");
        verificarNumero(data2, "Data Inválida");
        if(data1 > data2){
            throw new RegrasRunTime("Data inical maior que a data final");
        }
    }

    private void verificarNumero(Integer numero, String mensagem){
        if(numero == null){
            throw new RegrasRunTime(mensagem);
        }

    }
}