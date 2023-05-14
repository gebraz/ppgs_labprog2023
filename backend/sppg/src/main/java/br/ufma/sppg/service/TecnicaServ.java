package br.ufma.sppg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufma.sppg.excecao.ServicoRuntimeException;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.TecnicaRepo;

public class TecnicaServ {
    @Autowired
    TecnicaRepo repository;

    public Tecnica salvar(Tecnica tecnica){
        verificarTecnica(tecnica);
        if(tecnica.getId() != null){
            throw new ServicoRuntimeException(
                    "O ID das tecnicas é gerado automaticamente e não deve ser informado");
        }
         return repository.save(tecnica);
    }

    public List<Docente> obterTecnicasDocente(Tecnica tecnica){
        verificarTecnica(tecnica);
        verificarIdTecnica(tecnica);
        List<Docente> docente = repository.obterTecnicasDocente(tecnica.getId());
        return docente;
    }

    public Tecnica informarEstatisticasTecnica(Tecnica tecnica){
        verificarTecnica(tecnica);
        verificarIdTecnica(tecnica);
        Tecnica estatisticas = repository.informarEstatisticasTecnica(tecnica.getId());
        return estatisticas;
    }

    public Tecnica atualizar(Tecnica tecnica){
        verificarTecnica(tecnica);
        verificarIdTecnica(tecnica);
        return repository.save(tecnica);
    }

    public List<Tecnica> buscar(){
        return repository.findAll();
    }

    public void remover(Tecnica tecnica){
        verificarIdTecnica(tecnica);
        verificarTecnica(tecnica);
        repository.delete(tecnica);
    }

    public void removerPorId(Integer id){
        Optional<Tecnica> tecnica = repository.findById(id);
        remover(tecnica.get());
    }

    public void verificarTecnica(Tecnica tecnica){
        if(tecnica == null){
            throw new ServicoRuntimeException("A técnica não pode ser nulo!");
        }
    }

    public void verificarIdTecnica(Tecnica tecnica){
        if(tecnica == null || tecnica.getId() == null || !(repository.existsById(tecnica.getId()))){
            throw new ServicoRuntimeException("Id inválido!");
        }
    }
    



}
