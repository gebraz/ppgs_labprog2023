package br.ufma.sppg.service.loader;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.model.Producao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//TODO: ao carregar os dados do XML, tem que verificar se eles já estão presentes na base.


public class Parsing extends DefaultHandler {

	private String tagAtual;
	public Producao lastArtigo;
    public Producao lastEvento;
    public Tecnica lastTecnica;
    public Orientacao lastOrientacao;
    public String arquivo;
    Docente ref;
        
	/**
	 * construtor default
	 */
	public Parsing(Docente d) {
            super();
            this.ref = d;
	}

	/**
	 * Método que executa o parsing: laço automático que varre o documento de
	 * início ao fim, disparando eventos relevantes
	 * 
	 * @param pathArq
	 */
	public void executar(String pathArq) {

		// Passo 1: cria instância da classe SAXParser, através da factory
		// SAXParserFactory
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;

		try {
			saxParser = factory.newSAXParser();

			// Passo 2: comanda o início do parsing
			saxParser.parse(pathArq, this); // o "this" indica que a própria
								// classe  atuará como
								// gerenciadora de eventos SAX.

			// Passo 3: tratamento de exceções.
		} catch (ParserConfigurationException | SAXException | IOException e) {			
			e.printStackTrace();
			
		}
	}

	// os métodos startDocument, endDocument, startElement, endElement e
	// characters, listados a seguir, representam os métodos de call-back da API
	// SAX

	/**
	 * evento startDocument do SAX. Disparado antes do processamento da primeira
	 * linha
	 */
	public void startDocument() {
        //System.out.println("\nIniciando o Parsing...\n");
	}

	/**
	 * evento endDocument do SAX. Disparado depois do processamento da última
	 * linha
	 */
	public void endDocument() {
        //System.out.println("\nFim do Parsing...");
	}

	/**
	 * evento startElement do SAX. disparado quando o processador SAX identifica
	 * a abertura de uma tag. Ele possibilita a captura do nome da tag e dos
	 * nomes e valores de todos os atributos associados a esta tag, caso eles
	 * existam.
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts) {

        
		if (qName.compareTo("CURRICULO-VITAE") == 0) {
            //pode dar problema
            //try {
            //    ref.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse(atts.getValue("DATA-ATUALIZACAO")));
            //} catch (ParseException e) {
            //    ref.setDataAtualizacao(null);
            //    System.out.println("Erro com data atualização");
                //e.printStackTrace();
            //}
            ref.setLattes(atts.getValue("NUMERO-IDENTIFICADOR"));            
		}
		
         // se a tag for "", recupera o valor do atributo "sigla"
		if (qName.compareTo("DADOS-GERAIS") == 0) {
		    ref.setNome(atts.getValue("NOME-COMPLETO"));            
		}
                
        if ((qName.compareTo("ARTIGO-PUBLICADO")==0) ||
                (qName.compareTo("ARTIGO-ACEITO-PARA-PUBLICACAO")==0)){
            tagAtual = qName;
            if (lastArtigo == null)
                lastArtigo = new Producao();
            lastArtigo.setAutores("");
            lastArtigo.setTipo(tagAtual);
        }
                
        if (qName.compareTo("DADOS-BASICOS-DO-ARTIGO")==0) {
            if (lastArtigo == null)
                lastArtigo = new Producao();

            lastArtigo.setTitulo(atts.getValue("TITULO-DO-ARTIGO"));
            lastArtigo.setAno(Integer.parseInt(atts.getValue("ANO-DO-ARTIGO")));
            lastArtigo.setDoi(atts.getValue("DOI"));
            lastArtigo.setNatureza(atts.getValue("NATUREZA"));
        }
                
        if (qName.compareTo("DETALHAMENTO-DO-ARTIGO")==0) {
            if (lastArtigo == null)
                lastArtigo = new Producao();

            lastArtigo.setNomeLocal(atts.getValue("TITULO-DO-PERIODICO-OU-REVISTA"));
            lastArtigo.setIssnOuSigla(atts.getValue("ISSN"));
        }
                
        if (qName.compareTo("TRABALHO-EM-EVENTOS")==0) {
            tagAtual = qName;
            if (lastEvento == null)
                lastEvento = new Producao();
                
            lastEvento.setAutores("");            
            lastEvento.setTipo(tagAtual);            
        }
                
        if (qName.compareTo("DADOS-BASICOS-DO-TRABALHO")==0) {
            if (lastEvento == null)
                lastEvento = new Producao();

            lastEvento.setTitulo(atts.getValue("TITULO-DO-TRABALHO"));
            lastEvento.setAno(Integer.parseInt(atts.getValue("ANO-DO-TRABALHO")));
            lastEvento.setDoi(atts.getValue("DOI"));
            lastEvento.setNatureza(atts.getValue("NATUREZA"));
        }
                
        if (qName.compareTo("DETALHAMENTO-DO-TRABALHO")==0) {
            if (lastEvento == null)
                lastEvento = new Producao();
            lastEvento.setNatureza(atts.getValue("CLASSIFICACAO-DO-EVENTO"));
            lastEvento.setNomeLocal(atts.getValue("NOME-DO-EVENTO") + " - " + atts.getValue("TITULO-DOS-ANAIS-OU-PROCEEDINGS"));
        }
                
                
        if (qName.compareTo("AUTORES") == 0) {
            if (lastEvento!=null) {
                if (lastEvento == null) lastEvento = new Producao();
                lastEvento.setAutores(lastEvento.getAutores() + atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";");
            }
            if (lastArtigo!=null) {
                if (lastArtigo == null) lastArtigo = new Producao();
                lastArtigo.setAutores(lastArtigo.getAutores() + atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";");
            }
            if (lastTecnica != null) {
                if (lastTecnica == null) lastTecnica = new Tecnica();
                lastTecnica.setAutores(lastTecnica.getAutores() + atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";");
            }
        }
              
        if (qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-MESTRADO") == 0 ||
                qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO") ==0 ||
                qName.compareTo("OUTRAS-ORIENTACOES-CONCLUIDAS")==0 
                //||
                //qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO") ==0  ||
                //qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0 ||
                //qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-GRADUACAO")==0 ||
                //qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-INICIACAO-CIENTIFICA")==0 ||
                //qName.compareTo("OUTRAS-ORIENTACOES-EM-ANDAMENTO")==0
                )
        {
            if (lastOrientacao == null)
                lastOrientacao = new Orientacao();
            if (qName.contains("CONCLUIDAS"))
                lastOrientacao.setStatus("Concluída");
        }
        if (qName.compareTo("DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")==0 ||
                qName.compareTo("DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")==0 ||
                qName.compareTo("DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-CONCLUIDAS")==0 
                //||
                //qName.compareTo("DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO")==0 ||
                //qName.compareTo("DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0 ||
                //qName.compareTo("DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-GRADUACAO")==0 ||
                //qName.compareTo("DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-INICIACAO-CIENTIFICA")==0 ||
                //qName.compareTo("DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-EM-ANDAMENTO")==0
                ) {
            if (lastOrientacao == null)
                lastOrientacao = new Orientacao();
            lastOrientacao.setNatureza(atts.getValue("TIPO"));
            lastOrientacao.setTipo(atts.getValue("NATUREZA"));

            lastOrientacao.setTitulo(atts.getValue("TITULO"));
            lastOrientacao.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO") ==0 ||
                qName.compareTo("DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")==0 ||
                qName.compareTo("DETALHAMENTO-DE-OUTRAS-ORIENTACOES-CONCLUIDAS")==0 
                //||
                //qName.compareTo("DETALHAMENTO-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO")==0 ||
                //qName.compareTo("DETALHAMENTO-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0 ||
                //qName.compareTo("DETALHAMENTO-DA-ORIENTACAO-EM-ANDAMENTO-DE-GRADUACAO")==0 ||
                //qName.compareTo("DETALHAMENTO-DA-ORIENTACAO-EM-ANDAMENTO-DE-INICIACAO-CIENTIFICA")==0 ||
                //qName.compareTo("DETALHAMENTO-DE-OUTRAS-ORIENTACOES-EM-ANDAMENTO")==0
                ) {
            if (lastOrientacao == null)
                lastOrientacao = new Orientacao();
            
            lastOrientacao.setDiscente(atts.getValue("NOME-DO-ORIENTADO"));
            lastOrientacao.setInstituicao(atts.getValue("NOME-DA-INSTITUICAO"));            
            lastOrientacao.setCurso(atts.getValue("NOME-DO-CURSO"));
        }


                
        if (qName.compareTo("SOFTWARE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setAutores("");
            lastTecnica.setTipo(qName);            
        }
        if (qName.compareTo("DADOS-BASICOS-DO-SOFTWARE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTitulo(atts.getValue("TITULO-DO-SOFTWARE"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DO-SOFTWARE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Finalidade: " + atts.getValue("FINALIDADE") + "; " +
                            "Plataforma: " + atts.getValue("PLATAFORMA") + "; " +
                            "Ambiente: " + atts.getValue("AMBIENTE") + "; "            
            );
            //lastTecnica.setOutras_informacoes("Plataforma: " + atts.getValue("PLATAFORMA") + "; ");
            //lastTecnica.setOutras_informacoes("Ambiente: " + atts.getValue("AMBIENTE") + "; ");
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-FINANCIADORA"));
        }

        if (qName.compareTo("TRABALHO-TECNICO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            //lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DO-TRABALHO-TECNICO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(atts.getValue("NATUREZA"));
            lastTecnica.setTitulo(atts.getValue("TITULO-DO-TRABALHO-TECNICO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DO-TRABALHO-TECNICO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Finalidade: " + atts.getValue("FINALIDADE") + "; ");
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-FINANCIADORA"));
        }

        if (qName.compareTo("DEMAIS-TIPOS-DE-PRODUCAO-TECNICA")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            //lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DA-APRESENTACAO-DE-TRABALHO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(atts.getValue("NATUREZA"));
            lastTecnica.setTitulo(atts.getValue("TITULO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DA-APRESENTACAO-DE-TRABALHO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Nome do Evento: " + atts.getValue("NOME-DO-EVENTO") + "; " +                    
                    "Local da Apresentação: " + atts.getValue("LOCAL-DA-APRESENTACAO") + "; " + 
                    "Cidade da Apresentação: " + atts.getValue("CIDADE-DA-APRESENTACAO") + "; "
            );   
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-PROMOTORA"));         
        }

        if (qName.compareTo("CURSO-DE-CURTA-DURACAO-MINISTRADO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DE-CURSOS-CURTA-DURACAO-MINISTRADO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Nível do Curso: " + atts.getValue("NIVEL-DO-CURSO") + "; ");
            lastTecnica.setTitulo(atts.getValue("TITULO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DE-CURSOS-CURTA-DURACAO-MINISTRADO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Participação dos autores: " + atts.getValue("PARTICIPACAO-DOS-AUTORES") + "; " +
                "Local do Curso: " + atts.getValue("LOCAL-DO-CURSO") + "; " +
                "Cidade: " + atts.getValue("CIDADE") + "; " +
                "Duração: " + atts.getValue("DURACAO") + "; "
            );
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-PROMOTORA-DO-CURSO"));
            
        }

        if (qName.compareTo("ORGANIZACAO-DE-EVENTO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            //lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DA-ORGANIZACAO-DE-EVENTO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(atts.getValue("TIPO"));
            lastTecnica.setOutrasInformacoes("Natureza: " + atts.getValue("NATUREZA") + "; ");
            lastTecnica.setTitulo(atts.getValue("TITULO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DA-ORGANIZACAO-DE-EVENTO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-PROMOTORA"));
            lastTecnica.setOutrasInformacoes("Local: " + atts.getValue("LOCAL") + "; " +
                "Cidade: " + atts.getValue("CIDADE") + "; " +
                "Duração: " + atts.getValue("DURACAO-EM-SEMANAS") + "; "
            );            
        }

        if (qName.compareTo("OUTRA-PRODUCAO-TECNICA")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            //lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DE-OUTRA-PRODUCAO-TECNICA")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(atts.getValue("NATUREZA"));
            lastTecnica.setTitulo(atts.getValue("TITULO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DE-OUTRA-PRODUCAO-TECNICA")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-PROMOTORA"));            
            lastTecnica.setOutrasInformacoes("Finalidade: " + atts.getValue("FINALIDADE") + "; " +
                "Cidade: " + atts.getValue("CIDADE") + "; " +
                "Local: " + atts.getValue("LOCAL") + "; "
            );
        }

        if (qName.compareTo("CURSO-DE-CURTA-DURACAO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DO-CURSO-DE-CURTA-DURACAO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Nível do Curso: " + atts.getValue("NATUREZA") + "; ");
            lastTecnica.setTitulo(atts.getValue("TITULO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO")));
        }
        if (qName.compareTo("DETALHAMENTO-DO-CURSO-DE-CURTA-DURACAO")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-PROMOTORA-DO-EVENTO"));
            lastTecnica.setOutrasInformacoes("Local do Evento: " + atts.getValue("LOCAL-DO-EVENTO") + "; " +
                "Cidade: " + atts.getValue("CIDADE") + "; " +
                "Duração: " + atts.getValue("DURACAO") + "; "
            );            
        }

        if (qName.compareTo("PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTipo(qName);
            //lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DA-PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setTitulo(atts.getValue("TITULO"));
            lastTecnica.setAno(Integer.parseInt(atts.getValue("ANO-DESENVOLVIMENTO")));
        }
        if (qName.compareTo("DETALHAMENTO-DA-PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.setOutrasInformacoes("Finalidade: " + atts.getValue("FINALIDADE") + "; ");
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-FINANCIADORA"));
        }
        if (qName.compareTo("REGISTRO-OU-PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            
            lastTecnica.setFinanciadora(atts.getValue("INSTITUICAO-DEPOSITO-REGISTRO"));
            
            lastTecnica.setOutrasInformacoes("Tipo Registro/Patente: " + atts.getValue("TIPO-PATENTE") + "; " + 
                "Código: " + atts.getValue("CODIGO-DO-REGISTRO-OU-PATENTE") + "; " +
                "Data Depósito: " + atts.getValue("DATA-PEDIDO-DE-DEPOSITO") + "; " +            
                "Depositante: " + atts.getValue("NOME-DO-DEPOSITANTE") + "; " +
                "Titular: " + atts.getValue("NOME-DO-TITULAR") + "; "
            );
            
        }
	}

	/**
	 * evento endElement do SAX. Disparado quando o processador SAX identifica o
	 * fechamento de uma tag (ex: )
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		tagAtual = "";
                
        if ((qName.compareTo("ARTIGO-PUBLICADO") == 0) ||
                (qName.compareTo("ARTIGO-ACEITO-PARA-PUBLICACAO") == 0)){
            ref.adicionarProducao(lastArtigo);
            lastArtigo = null;
        }

        if (qName.compareTo("TRABALHO-EM-EVENTOS") == 0) {
            ref.adicionarProducao(lastEvento);
            lastEvento = null;
        }

        /*if (qName.compareTo("CAPITULO-DE-LIVRO-PUBLICADO") == 0||
                qName.compareTo("LIVRO-PUBLICADO-OU-ORGANIZADO")==0) {
            capitulo.add(lastCapitulo);
            lastCapitulo = null;
        }

        if (qName.compareTo("PROJETO-DE-PESQUISA") == 0) {
            ref.projetos.add(lastProjeto);
            lastProjeto = null;
        }
        */

        if (qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-MESTRADO") == 0 ||
                qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO") ==0 ||
                qName.compareTo("OUTRAS-ORIENTACOES-CONCLUIDAS")==0 ||
                qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO") ==0  ||
                qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0)  {
            ref.adicionarOrientacao(lastOrientacao);
            lastOrientacao = null;
        }

        if (qName.compareTo("SOFTWARE")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }

        if (qName.compareTo("PATENTE")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }
        if (qName.compareTo("REGISTRO-OU-PATENTE")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }

        if (qName.compareTo("TRABALHO-TECNICO")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }

        if (qName.compareTo("DEMAIS-TIPOS-DE-PRODUCAO-TECNICA")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }

        if (qName.compareTo("CURSO-DE-CURTA-DURACAO-MINISTRADO")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }
        if (qName.compareTo("ORGANIZACAO-DE-EVENTO")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }

        if (qName.compareTo("OUTRA-PRODUCAO-TECNICA")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }
        if (qName.compareTo("CURSO-DE-CURTA-DURACAO")==0) {
            ref.adicionarTecnica(lastTecnica);
            lastTecnica = null;
        }
	}

	/**
	 * evento characters do SAX. É onde podemos recuperar as informações texto
	 * contidas no documento XML (textos contidos entre tags). Neste exemplo,
	 * recuperamos os nomes dos países, a população e a moeda
	 * 
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String texto = new String(ch, start, length);

		// ------------------------------------------------------------
		// --- TRATAMENTO DAS INFORMAÇÕES DE ACORDO COM A TAG ATUAL ---
		// ------------------------------------------------------------

		//if ((tagAtual != null) && (tagAtual.compareTo("DADOS-GERAIS") == 0)) {
		//	System.out.print(texto + " - nome: " + ref.curriculo.getNomeCompleto());
		//}
	}

	
        
}