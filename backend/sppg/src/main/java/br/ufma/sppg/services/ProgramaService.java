package br.ufma.sppg.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.Indices;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.services.exceptions.RegraNegocioRunTime;


@Service
public class ProgramaService {
    
    @Autowired
    ProgramaRepository repository;

    public List<Programa> obterPrograma(String nome){
        throwsNome(nome);
        return repository.findAllByNome(nome);
    }

    public List<Orientacao> obterOrientacoes (Integer idPrograma, Integer anoIni, Integer anoFin){
        throwId(idPrograma);
        throwAno(anoIni, anoFin);
        
        List<Orientacao> allOrientacoes = repository.obterallOrientacoes(idPrograma);
        List<Orientacao> orientacoes = new ArrayList<>();

        for(Orientacao orientacao: allOrientacoes){
            if(orientacao.getAno() >= anoIni && orientacao.getAno() <= anoFin){
                orientacoes.add(orientacao);
            }
        }

        throwListaOrientacoes(orientacoes);
        return orientacoes;

        // throwId(idPrograma);
        // List<Docente> docentes = repository.obterDocentes(idPrograma);
        // List<Orientacao> orientacoesD = new ArrayList<>();
        // List<Orientacao> orientacoes = new ArrayList<>();      

        // for(Docente docente : docentes){
        //     orientacoesD = docente.getOrientacoes();
        //     for(Orientacao orientacao : orientacoesD){
        //         if(orientacao.getAno() >= anoIni && orientacao.getAno() <= anoFin){
        //             orientacoes.add(orientacao);
        //         }
        //     }
        // }
        
        // return orientacoes;
    }

    public List<Producao> obterProducoes(Integer idPrograma, Integer anoIni, Integer anoFin){
        throwId(idPrograma);
        throwAno(anoIni, anoFin);

        List<Producao> allProducoes= repository.obterallProducoes(idPrograma);
        List<Producao> producoes = new ArrayList<>();

        for(Producao producao: allProducoes){
            if(producao.getAno() >= anoIni && producao.getAno() <= anoFin){
                producoes.add(producao);
            }
        }

        throwListaProducao(producoes);
        return producoes;

        // throwId(idPrograma);
        // List<Docente> docentes = repository.obterDocentes(idPrograma);
        // List<Producao> producoesD = new ArrayList<>();
        // List<Producao> producoes = new ArrayList<>();
        
        // for(Producao producao: producoes)
        //     if(producao.getAno() >= anoIni && producao.getAno() <= anoFin){
        //         producoes.add(producao);
        //     }
       
        //   for(Docente docente : docentes){
        //     producoesD = docente.getProducoes();
        //     for(Producao producao : producoesD){
        //         if(producao.getAno() >= anoIni && producao.getAno() <= anoFin){
        //             producoes.add(producao);
        //         }
        //     }
        // }

        // return producoes;
    }

    public List<Tecnica> obterTecnicas(Integer idPrograma, Integer anoIni, Integer anoFin){ 
        throwId(idPrograma);
        throwAno(anoIni, anoFin);

        List<Tecnica> alltecnicas= repository.obterallTecnicas(idPrograma);
        List<Tecnica> tecnicas = new ArrayList<>();

        for(Tecnica tecnica: alltecnicas){
            if(tecnica.getAno() >= anoIni && tecnica.getAno() <= anoFin){
                tecnicas.add(tecnica);
            }
        }

        throwListaTecnica(tecnicas);
        return tecnicas;
        
        // List<Docente> docentes = repository.obterDocentes(idPrograma);
        // List<Tecnica> tecnicas = new ArrayList<>();
        // List<Tecnica> tecnicasD = new ArrayList<>();
        
        // for(Docente docente : docentes){
        //     tecnicasD = docente.getTecnicas();
        //     for(Tecnica tecnica : tecnicasD){
        //         if(tecnica.getAno() >= anoIni && tecnica.getAno() <= anoFin){
        //             tecnicas.add(tecnica);
        //         }
        //     }
        // }
        
        // throwListaTecnica(tecnicas);
        // return tecnicas;
    }

    public Indices obterProducaoIndices(Integer idPrograma, Integer anoIni, Integer anoFin){ 
        throwId(idPrograma);
        throwAno(anoIni, anoFin);
        //List<Docente> docentes = repository.obterDocentes(idPrograma);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = obterProducoes(idPrograma, anoIni, anoFin);
        ArrayList<Integer> indicesProd = new ArrayList<>();

        // for(Docente docente : docentes){

        //     producoes = docente.getProducoes();

        for(Producao producao : producoes){

            if(producao.getAno() >= anoIni && producao.getAno() <= anoFin && !indicesProd.contains(producao.getId())){
                
                indicesProd.add(producao.getId());
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
                        throw new RegraNegocioRunTime("Uma das produções possui o Qualis inválido");
                }
            }
        }
        iGeral = iRestrito + iNRestrito;

        return new Indices(iRestrito, iNRestrito, iGeral);
    }

    public List<Docente> obterDocentePrograma(Integer idPrograma){
        List<Docente> docentes = repository.obterDocentes(idPrograma);

        throwListaDocente(docentes); 
        return docentes;
    }

    private void throwId(Integer id){
        if(id == null){
            throw new RegraNegocioRunTime("O id é nulo");
        }
        if(!repository.existsById(id)){
            throw new RegraNegocioRunTime("O id não existe");
        }
    }


    private void throwsNome(String nome){
        if(nome == null){
            throw new RegraNegocioRunTime("O nome do programa é nulo");
        }
        if(nome.trim().equals("")){
            throw new RegraNegocioRunTime("O nome do programa está vazio");
        }
    }

    private void throwListaOrientacoes(List<Orientacao> orientacoes){
        if(orientacoes.isEmpty()){
            throw new RegraNegocioRunTime("Não foi encontrado nenhuma orientação no período de tempo inserido");
        }
    }

    private void throwListaProducao(List<Producao> producoes){
        if(producoes.isEmpty()){
            throw new RegraNegocioRunTime("Não foi encontrado nenhuma produção no período de tempo inserido");
        }
    }

    private void throwListaTecnica(List<Tecnica> tecnicas){
        if(tecnicas.isEmpty()){
            throw new RegraNegocioRunTime("Não foi encontrado nenhuma técnica no período de tempo inserido");
        }
    }

    private void throwListaDocente(List<Docente> docentes){
        if(docentes.isEmpty()){
            throw new RegraNegocioRunTime("Não foi encontrado nenhum docente");
        }
    }

    private void throwAno(Integer anoIni, Integer anoFin){
        if (anoIni >= anoFin){ // caso o ano inicial seja maior que o ano final
            Integer data = anoFin;
            anoFin = anoIni;
            anoIni = data;
        }
    }
    
}