package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;

@Service
public class ProducaoService {
    
    @Autowired
    ProducaoRepository prodRepo;

    @Autowired
    DocenteRepository docRepo;

    @Autowired
    ProgramaRepository progRepo;

    public List<Producao>obterProducoesPPG(Integer idPrograma, Integer data1, Integer data2){

        ArrayList<Producao> producoes = new ArrayList<>();
        Optional<Programa> programa = progRepo.findById(idPrograma);
        if(programa.isPresent()){
            for(int i = 0; i < progRepo.getReferenceById(idPrograma).getDocentes().size(); i++){
                for(int j = 0; j< progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().size(); j++){
                    if(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j).getAno() >= data1
                    && progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j).getAno() <= data2){
                        producoes.add(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j));
                    }
                }
            }
            return producoes;
        }
        throw new RegrasRunTime("Programa Inexistente");

    };

    public List<Producao>obterProducoesDocente(Integer idDocente, Integer data1, Integer data2){

        ArrayList<Producao> producoes = new ArrayList<>();
        Optional<Docente> docente = docRepo.findById(idDocente);
        if(docente.isPresent()){
            for(int i = 0; i < docRepo.getReferenceById(idDocente).getProducoes().size(); i++){
                if(docRepo.getReferenceById(idDocente).getProducoes().get(i).getAno() >= data1
                && docRepo.getReferenceById(idDocente).getProducoes().get(i).getAno() <= data2){
                    producoes.add(docRepo.getReferenceById(idDocente).getProducoes().get(i));
                }
            }
            return producoes;
        }
        throw new RegrasRunTime("Docente Inexistente");
    }
}
