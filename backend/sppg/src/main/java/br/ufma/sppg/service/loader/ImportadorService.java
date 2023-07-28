package br.ufma.sppg.service.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Qualis;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.QualisRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import java.util.Optional;


@Service
public class ImportadorService {

    @Autowired
    DocenteRepository repoDoc;
    @Autowired
    ProducaoRepository prodRepo;
    @Autowired
    OrientacaoRepository oriRepo;
    @Autowired
    TecnicaRepository tecRepo;
    @Autowired
    QualisRepository qualisRepo;
    
    List<Qualis> eventos;  //caso especial

    //TODO: importa novos caras, mas não está sincronizando diferenças nos dados
/*     public List<String> importadorEmMassa(String defFolder){
        File dir = new File(defFolder);
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".xml"));
        String identificador[];
        List<String> importados = new ArrayList<>();
        
        eventos = qualisRepo.findByTipo("eventos");

        for (File f : files) {
            identificador = f.getName().split(".x");
            try {
                Docente ref = importarDocente(defFolder + f.getName());
                //apenas para ver onde está, comentar
                System.out.println("Executando: " + ref.getNome());
                //procura pela mesmo docente na base
                Docente base = repoDoc.findByNome(ref.getNome());                
                if (base == null)                     
                    base = Docente.builder()
                                .lattes(ref.getLattes())
                                .nome(ref.getNome())
                                .dataAtualizacao(ref.getDataAtualizacao())
                                .build();
                
                syncProducao(ref.getProducoes(), base);
                syncTecnica(ref.getTecnicas(), base);
                syncOrientacao(ref.getOrientacoes(), base);

                importados.add(base.getNome());
            }
            catch (Exception e) {
               e.printStackTrace();
            }            
        }
        return importados;
    }*/


    public List<String> importadorEmMassa(String defFolder) {
    File dir = new File(defFolder);
    File[] files = dir.listFiles((dir1, name) -> name.endsWith(".xml"));
    String identificador[];
    List<String> importados = new ArrayList<>();

    eventos = qualisRepo.findByTipo("eventos");

    for (File f : files) {
        identificador = f.getName().split("\\."); // Corrigido para escapar o ponto
        try {
            Docente ref = importarDocente(defFolder + f.getName());
            //apenas para ver onde está, comentar
            System.out.println("Executando: " + ref.getNome());
            //procura pelo mesmo docente na base
            List<Docente> baseOptional = repoDoc.findByNome(ref.getNome()); // Verifique se é Optional<Docente>
            
           /*  if (baseOptional.isPresent()) {
                Docente base = baseOptional.get(); // Extrair o Docente do Optional
                syncProducao(ref.getProducoes(), base);
                syncTecnica(ref.getTecnicas(), base);
                syncOrientacao(ref.getOrientacoes(), base);

                importados.add(base.getNome());
            } else {*/
            Docente base = Docente.builder()
                    .lattes(ref.getLattes())
                    .nome(ref.getNome())
                    .dataAtualizacao(ref.getDataAtualizacao())
                    .build();

            syncProducao(ref.getProducoes(), base);
            syncTecnica(ref.getTecnicas(), base);
            syncOrientacao(ref.getOrientacoes(), base);

            importados.add(base.getNome());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return importados;
}


    private Docente importarDocente(String arquivo) {
        Docente ref = new Docente();
        Parsing imp = new Parsing(ref);
        imp.executar(arquivo);
        return ref;
    }

    private void syncProducao(List<Producao> importado, Docente base) {        
        if (importado == null) return;
        Producao prodNaBase = null;

        //1) está na base?
        for (Producao prod : importado) {            

            if (prod == null) continue;
            //TODO: existem publicações repetidas. Tem o mesmo titulo, mas local cadastrado de maneiras diferentes.
            prodNaBase = prodRepo.findByTituloAndNomeLocal(prod.getTitulo(), prod.getNomeLocal());

            if (prodNaBase != null) {             
                //2) se não estiver com qualis, tenta associar   
                if (prodNaBase.getQualisRef() == null)
                    associarQualis(prodNaBase);

                //3) se estiver na base, está associado ao docente?                
                if ((base.getProducoes()!=null) && (!base.getProducoes().contains(prodNaBase)))
                    base.adicionarProducao(prodNaBase);                
            }
            else {             
                //2) se não estiver na base, coloque lattes, adicione e associe ao docente
                associarQualis(prod);   
                prodNaBase = prodRepo.save(prod);
                base.adicionarProducao(prodNaBase);
            }    
            
        }        
        repoDoc.save(base);        
    }

    private void associarQualis(Producao prod) {
        Qualis temp;
        //se periódico
        if (prod.getTipo().equals("ARTIGO-PUBLICADO")) {             
            String issn = prod.getIssnOuSigla().substring(0, 4) + "-" + prod.getIssnOuSigla().substring(4);
            //System.out.println(issn);
            temp = qualisRepo.findByIssnSigla(issn);
            if (temp != null){
                prod.setQualisRef(temp);
                if (temp.getEstratoAtualizado()!= null)
                    prod.setQualis(temp.getEstratoAtualizado());
                else
                    prod.setQualis(temp.getEstratoSucupira());
                if ((temp.getPercentil() != null) 
                    && (!temp.getPercentil().equals(""))
                    && (!temp.getPercentil().equals("nulo"))
                    )
                    prod.setPercentileOuH5(Float.parseFloat(temp.getPercentil()));
            }
        }
        //se evento :(
        //quebra o titulo -> busca cada palavra como sigla em qualis
        else {
            String[] palavras = prod.getNomeLocal().split("[\\s+12(),-;']");
            for (String palavra : palavras) {
                //tem 31 conferências com sigla < 3
                if (palavra.length()>=3) {
                    temp = qualisRepo.findByIssnSigla(palavra.toUpperCase());
                    if (temp!=null) {                        
                        prod.setQualisRef(temp);
                        prod.setIssnOuSigla(temp.getIssnSigla());
                        prod.setQualis(temp.getEstratoSucupira());                        
                        break;
                    }
                }
            }
            //segunda tentativa: encontrar nome completo no local
            String local = prod.getNomeLocal().toLowerCase();
            //TODO: melhorar, muito lento
            for (Qualis q: eventos) {
                if (local.contains(q.getTitulo().toLowerCase())){
                    prod.setQualisRef(q);
                    prod.setIssnOuSigla(q.getIssnSigla());
                    prod.setQualis(q.getEstratoSucupira());                        
                    break;
                }
            }
        }
        

    }


    private void syncTecnica(List<Tecnica> importado, Docente base) {
        if (importado == null) return;
        Tecnica tecNaBase = null;

        //1) está na base?
        for (Tecnica tec : importado) {
            if (tec == null) continue;
            tecNaBase = tecRepo.findByTitulo(tec.getTitulo());

            if (tecNaBase != null) {
                //2) se estiver na base, está associado ao docente?
                if ((base.getTecnicas()!=null) && (!base.getTecnicas().contains(tecNaBase)))
                    base.adicionarTecnica(tecNaBase);
            }
            else {             
                //3) se não estiver na base, adicione e associe ao docente
                tecNaBase = tecRepo.save(tec);
                base.adicionarTecnica(tecNaBase);
            }    
        }        
        repoDoc.save(base);       

    }

    private void syncOrientacao(List<Orientacao> importado, Docente base) {
        if (importado == null) return;
        Orientacao orientacaoNaBase = null;

        //1) está na base?
        for (Orientacao ori : importado) {
            if (ori == null) continue;
            orientacaoNaBase = oriRepo.findByTipoAndDiscenteAndTitulo(ori.getTipo(), ori.getDiscente(), ori.getTitulo());

            if (orientacaoNaBase != null) {
                //2) se estiver na base, está associado ao docente?
                if ((base.getOrientacoes()!=null) && (!base.getOrientacoes().contains(orientacaoNaBase))) {
                    base.adicionarOrientacao(orientacaoNaBase);
                    orientacaoNaBase.setOrientador(base);
                    oriRepo.save(orientacaoNaBase);
                }
            }
            else {             
                //3) se não estiver na base, adicione e associe ao docente
                ori.setOrientador(base);                
                orientacaoNaBase = oriRepo.save(ori);
                base.adicionarOrientacao(orientacaoNaBase);
                
            }    
        }        
        repoDoc.save(base);  
    }
}
