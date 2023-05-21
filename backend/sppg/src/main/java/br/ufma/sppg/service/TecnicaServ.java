package br.ufma.sppg.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

public class TecnicaServ {
    @Autowired
    TecnicaRepository repository;

    public Tecnica salvar(Tecnica tecnica){
        verificarTecnica(tecnica);
        if(tecnica.getId() != null){
            throw new ServicoRuntimeException(
                    "O ID das tecnicas é gerado automaticamente e não deve ser informado");
        }
         return repository.save(tecnica);
    }

    public List<Tecnica> obterTecnicasDocente(Integer docenteId, Integer anoInicio, Integer anoFim){
        if (anoFim == null){
            LocalDate dataAtual = LocalDate.now();
            anoFim = dataAtual.getYear();        
        }
        if (anoInicio == null){
            anoInicio = anoFim - 3;
        }
        List<Tecnica> tecnica = repository.obterTecnicasDocente(docenteId, anoInicio, anoFim);
        return tecnica;
    }

    public List<Tecnica> obterTecnicasPPG(Integer programaId, Integer anoInicio, Integer anoFim){
        if (anoFim == null){
            LocalDate dataAtual = LocalDate.now();
            anoFim = dataAtual.getYear();        
        }
        if (anoInicio == null){
            anoInicio = anoFim - 3;
        }
        List<Tecnica> tecnica = repository.obterTecnicasPPG(programaId, anoInicio, anoFim);
        return tecnica;
    }

    public List<Orientacao> obterOrientacoesTecnica(Integer tecnicaId, Integer anoInicio, Integer anoFim){
        if (anoFim == null){
            LocalDate dataAtual = LocalDate.now();
            anoFim = dataAtual.getYear();        
        }
        if (anoInicio == null){
            anoInicio = anoFim - 3;
        }
        List<Orientacao> orientacao = repository.obterOrientacoesTecnica(tecnicaId, anoInicio, anoFim);
        return orientacao;
    }

    public void informarEstatisticasTecnica(Integer tecnicaId, Integer qtdGrad, Integer qtdMestrado, Integer qtdDoutorado  ){
        if (tecnicaId == null){
            throw new ServicoRuntimeException("O ID da tecnica não pode ser nulo");
        }else{
            Optional<Tecnica> tecnica = repository.findById(tecnicaId);
            Tecnica tecnicaAtualizada = tecnica.get();
            tecnicaAtualizada.setQtdGrad(qtdGrad);
            tecnicaAtualizada.setQtdMestrado(qtdMestrado);
            tecnicaAtualizada.setQtdDoutorado(qtdDoutorado);
            repository.save(tecnicaAtualizada);
        }
    }

    public Tecnica atualizar(Tecnica tecnica){
        verificarTecnica(tecnica);
        verificarIdTecnica(tecnica);
        return repository.save(tecnica);
    }

    public List<Tecnica> buscar(){
        return repository.findAll();
    }

    public List<Tecnica> buscar(Tecnica filtro) {
        if (filtro == null)
            throw new ServicoRuntimeException("O filtro está nulo");
        Example<Tecnica> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING));

        return repository.findAll(example);
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
