package br.ufma.sppg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.services.exceptions.RegraAcessoRunTime;

@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository repository;

    public Indice obterIndice(Integer idDocente, Integer anoIni, Integer anoFin){ 
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        Optional<Docente> docente = repository.findById(idDocente);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = new ArrayList<>();

        producoes = docente.get().getProducoes();

            for(Producao producao : producoes){

                if(producao.getAno() >= anoIni && producao.getAno() <= anoFin){
                    
                    switch (producao.getQualis()) {
                        case "A1":
                            iRestrito += 1;
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
                            throw new RegraAcessoRunTime("Uma das produções possui o Qualis inválido");
                    }
                }
            }
        iGeral = iRestrito + iNRestrito;

        return new Indice(iRestrito, iNRestrito, iGeral);
    }

    private void verificarNome(String nome){
        if(nome == null){
            throw new RegraAcessoRunTime("Nome do Programa inválido");
        }
        if(nome.trim().equals("")){
            throw new RegraAcessoRunTime("Nome do Programa inválido");
        }
    }

    private void verificarId(Integer idDocente){
        verificarNumero(idDocente);
        if(!repository.existsById(idDocente)){
            throw new RegraAcessoRunTime("Id do Programa não está registrado");
        }
    }

    private void verificarData(Integer data1, Integer data2){
        verificarNumero(data1);
        verificarNumero(data2);
        if(data1 > data2){
            throw new RegraAcessoRunTime("Data inical maior que a data final");
        }
    }

    private void verificarNumero(Integer numero){
        if(numero == null){
            throw new RegraAcessoRunTime("Número Inválido");
        }

    }
}
