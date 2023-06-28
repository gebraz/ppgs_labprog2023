package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import jakarta.transaction.Transactional;


/*
 * A QUANTIDADE DE A1,A2,... POR ANO
 * SORT()
 */
@Service
public class ProducaoService {
    
    @Autowired
    ProducaoRepository prodRepo;

    @Autowired
    DocenteRepository docRepo;

    @Autowired
    ProgramaRepository progRepo;

    @Autowired
    OrientacaoRepository oriRepo;

    public List<Producao>obterProducoesPPG(Integer idPrograma, Integer data1, Integer data2){


        //É Presumido que o usuário coloque em data1 o valor mais baixo e em data2 o valor mais alto como por exemplo
        //data1=2016, data2=2023. Esta função verifica se a ordem esperada foi trocada e ajusta para que não ocorra erros
        if (data1 >= data2){
            Integer data = data2;
            data2 = data1;
            data1 = data;
        }

        Optional<Programa> programa = progRepo.findById(idPrograma);
        if(programa.isPresent()){

            //Verificando se o Programa possui Docentes cadastrados
            if(progRepo.getReferenceById(idPrograma).getDocentes() == null 
            || progRepo.getReferenceById(idPrograma).getDocentes().isEmpty())
                throw new RegrasRunTime("O programa não possui Docentes cadastrados");

            ArrayList<Producao> producoes = new ArrayList<>();

            for(int i = 0; i < progRepo.getReferenceById(idPrograma).getDocentes().size(); i++){
                //Verificando se o Docente do laço possui Array de Produções
                if(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes() != null
                && !progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().isEmpty())
                {
                    for(int j = 0; j< progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().size(); j++){
                        if(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j).getAno() >= data1
                        && progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j).getAno() <= data2){
                            producoes.add(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j));
                        }
                    }
                }
            }
            if(producoes.isEmpty())
                throw new RegrasRunTime("O Programa não possui Docentes com Produções no periodo especificado");

            return producoes;
        }
        throw new RegrasRunTime("Programa Inexistente");

    };

    public List<Producao>obterProducoesDocente(Integer idDocente, Integer data1, Integer data2){

        //É Presumido que o usuário coloque em data1 o valor mais baixo e em data2 o valor mais alto como por exemplo
        //data1=2016, data2=2023. Esta função verifica se a ordem esperada foi trocada e ajusta para que não ocorra erros
        if (data1 >= data2){
            Integer data = data2;
            data2 = data1;
            data1 = data;
        }

        Optional<Docente> docente = docRepo.findById(idDocente);
        if(docente.isPresent()){

            if(docRepo.getReferenceById(idDocente).getProducoes() == null 
            || docRepo.getReferenceById(idDocente).getProducoes().isEmpty())
                throw new RegrasRunTime("O Docente não possui nenhuma Produção Registrada");

            ArrayList<Producao> producoes = new ArrayList<>();

            for(int i = 0; i < docRepo.getReferenceById(idDocente).getProducoes().size(); i++){
                if(docRepo.getReferenceById(idDocente).getProducoes().get(i).getAno() >= data1
                && docRepo.getReferenceById(idDocente).getProducoes().get(i).getAno() <= data2){
                    producoes.add(docRepo.getReferenceById(idDocente).getProducoes().get(i));
                }
            }
            if(producoes.isEmpty())
                throw new RegrasRunTime("O Docente não possui nenhuma Produção no periodo especificado");

            return producoes;
        }
        throw new RegrasRunTime("Docente Inexistente");
    }


    
    private void verificarProducao(Producao producao){
        if(producao==null)
            throw new RegrasRunTime("Produção deve ser Informada");
        if(producao.getTipo() == null || producao.getTipo() == "")
            throw new RegrasRunTime("O tipo da Produção deve ser informado");
        if(producao.getIssnOuSigla() == null || producao.getIssnOuSigla() == "")
            throw new RegrasRunTime("A Issn/Sigla da Produção deve ser informada");
        if(producao.getNomeLocal() == null || producao.getNomeLocal() == "")
            throw new RegrasRunTime("O nome local da Produção deve ser informado");
        if(producao.getTitulo() == null || producao.getTitulo() == "")
            throw new RegrasRunTime("O titulo da Produção deve ser informado");
        if(producao.getAno() == null)
            throw new RegrasRunTime("O ano da Produção deve ser Informado");
        if(producao.getAno() < 0)
            throw new RegrasRunTime("Informe um ano válido para a Produção");
        if(producao.getQualis() == null || producao.getQualis() == "")
            throw new RegrasRunTime("A qualis da Produção deve ser informada");
        Float percentileOuH5 = producao.getPercentileOuH5();
        if(percentileOuH5 == null || percentileOuH5 < 0)
            throw new RegrasRunTime("O percentile/h5 da Produção deve ser informado");
        if(producao.getQtdGrad() == null)
            throw new RegrasRunTime("A quantidade de Graduandos da Produção deve ser informada");
        if(producao.getQtdGrad() < 0)
            throw new RegrasRunTime("Deve ser informado uma quantia real de Graduandos da Produção");
        if(producao.getQtdMestrado() == null)
            throw new RegrasRunTime("A quantidade de Mestrandos da Produção deve ser informada");
        if(producao.getQtdMestrado() < 0)
            throw new RegrasRunTime("Deve ser informado uma quantia real de Mestrandos da Produção");
        if(producao.getQtdDoutorado() == null)
            throw new RegrasRunTime("A quantidade de Doutorandos da Produção deve ser informada");
        if(producao.getQtdDoutorado() < 0)
            throw new RegrasRunTime("Deve ser informado uma quantia real de Doutorandos da Produção");
    }

    @Transactional
    public Producao informarEstatisticasProducao(Producao producao){
        verificarProducao(producao);
        return prodRepo.save(producao);
    }


    public List<Orientacao> obterOrientacaoProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            if(prodRepo.getReferenceById(idProducao).getOrientacoes() != null)
                return prodRepo.getReferenceById(idProducao).getOrientacoes();
        }
        throw new RegrasRunTime("A Producao não existe");
    }
/*
    public boolean excluirProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            removerDocentesProducao(idProducao);
            removerOrientacoesProducao(idProducao);
            prodRepo.delete(prodRepo.getReferenceById(idProducao));
            return true;
        }
        if(prodRepo.existsById(idProducao))
            throw new RegrasRunTime("Erro ao Excluir Produção");
        throw new RegrasRunTime("Produção Inexistente");
    }

    public boolean retirarProducaoDocente(Integer idProducao, Integer idDocente){
        Optional<Producao> opProducao = prodRepo.findById(idProducao);
        if(opProducao.isPresent()){
            Producao producao = prodRepo.getReferenceById(idProducao);
            if(docRepo.existsById(idDocente)){
                Docente docente = docRepo.getReferenceById(idDocente);

                if(docente.getProducoes().remove(producao))
                    producao.getDocentes().remove(docente);
                else
                    throw new RegrasRunTime("Producao e Docente não possuem Relação");
                
                docRepo.save(docente);
                prodRepo.save(producao);
                return true;
            }else{
                throw new RegrasRunTime("Docente Inexistente");
            }
        }
        throw new RegrasRunTime("Producao Inexistente");
    }


    public boolean retirarProducaoOrientacao(Integer idProducao, Integer idOrientacao){
        Optional<Producao> opProducao = prodRepo.findById(idProducao);
        if(opProducao.isPresent()){
            Producao producao = prodRepo.getReferenceById(idProducao);
            if(oriRepo.existsById(idOrientacao)){
                Orientacao orientacao = oriRepo.getReferenceById(idOrientacao);

                if(orientacao.getProducoes().remove(producao))
                    producao.getOrientacoes().remove(orientacao);
                else
                    throw new RegrasRunTime("Producao e Orientacao não possuem Relação");
                
                oriRepo.save(orientacao);
                prodRepo.save(producao);
                return true;
            }else{
                throw new RegrasRunTime("Orientacao Inexistente");
            }
        }
        throw new RegrasRunTime("Producao Inexistente");
    }

    public boolean removerOrientacoesProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            if(prodRepo.getReferenceById(idProducao).getOrientacoes() != null
            && !prodRepo.getReferenceById(idProducao).getOrientacoes().isEmpty()){
                for(int i = 0; i < prodRepo.getReferenceById(idProducao).getOrientacoes().size(); i++){
                    retirarProducaoOrientacao(idProducao, prodRepo.getReferenceById(idProducao).getOrientacoes().get(i).getId());
                }
                return true;
            }
            throw new RegrasRunTime("Não Existem Orientações na Produção");

        }
        throw new RegrasRunTime("Producao Inexistente");
    }

    public boolean removerDocentesProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            if(prodRepo.getReferenceById(idProducao).getDocentes() != null
            && !prodRepo.getReferenceById(idProducao).getDocentes().isEmpty()){
                for(int i = 0; i < prodRepo.getReferenceById(idProducao).getDocentes().size(); i++){
                    retirarProducaoDocente(idProducao, prodRepo.getReferenceById(idProducao).getDocentes().get(i).getId());
                }
                return true;
            }
            throw new RegrasRunTime("Não Existem Docentes na Produção");

        }
        throw new RegrasRunTime("Producao Inexistente");
    }
    */
}
