package br.ufma.sppg.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufma.sppg.excecao.ServicoRuntimeException;
import br.ufma.sppg.model.Orientacao;
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

    public void informarEstatisticasTecnica(Integer tecnicaId, Integer qtdGrand, Integer qtdMestrado, Integer qtdDoutorado  ){
        repository.associarEstatisticasTecnica(tecnicaId, qtdGrand, qtdMestrado, qtdDoutorado);;
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
