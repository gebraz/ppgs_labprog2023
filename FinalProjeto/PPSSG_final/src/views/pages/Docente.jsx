
import Navbar from "../componentes/Navbar";
import Header from "../componentes/Header";
import Filtros from "../componentes/Filtros";
import Indicadores from "../componentes/Indicadores";
import Graficos from "../componentes/Graficos";
import Tabela from "../componentes/Tabela";
import { useParams } from 'react-router-dom';
import { useState } from "react";
import { useEffect } from "react";
import axios from 'axios'
import TabelaALL from "../componentes/TabelaALL";
import '../estilizacao/TabelaALL.css'


const programas = [
    {id:1, nome:"PPGCC"},
]

const client = axios.create({
    baseURL: "http://localhost:8080/api/docente/"
});





export default function Docente() {    
    const { id } = useParams();
    const [docenteId, seDocenteId] = useState(Number.parseInt(id, 10));
    console.log(id);
    const [progSel, setProgSel] = useState(1);
    const [anoIni, setAnoIni] = useState(2019);
    const [anoFim, setAnoFim] = useState(2023);
    const [nome,setNome] = useState();
    

    const [indicadores, setIndicadores] = useState({});
    const [graficoP, setGraficoP] = useState({});
    const [graficoC,setGraficoC] = useState({});
    const[producao,setProducao] = useState([])
    const [orientacao, setOrientacao] = useState([]);
    const [tecnica, setTecnica] = useState([]);
    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        onSearch();
        },[anoIni,anoFim]
    )
    
    function onSearch() {
        client.get(`indicadores/${docenteId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
            setIndicadores(response.data)}).catch(error => {
                console.log(error.repsonse)
            });
        client.get(`nome/${docenteId}`).then((response) => { console.log(response.data)
                setNome(response.data)}).catch(error => {
                    console.log(error.repsonse)
                });    
        client.get(`graficoDocente/${docenteId}/${anoIni}/${anoFim}/ARTIGO-PUBLICADO`).then((response) => { console.log(response.data)
                setGraficoP(response.data)}).catch(error => {
                console.log(error.repsonse)
                });
        client.get(`graficoDocente/${docenteId}/${anoIni}/${anoFim}/TRABALHO-EM-EVENTOS`).then((response) => { console.log(response.data)
                setGraficoC(response.data)}).catch(error => {
                console.log(error.repsonse)
                });
        client.get(`obter_orientacoes/${docenteId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setOrientacao(response.data)}).catch(error => {
                        console.log(error.repsonse)
        });       
        client.get(`obter_producoes/${docenteId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setProducao(response.data)}).catch(error => {
                        console.log(error.repsonse)
        });
        client.get(`obter_tecnicas/${docenteId}`).then((response) => { console.log(response.data)
            setTecnica(response.data)}).catch(error => {
                console.log(error.repsonse)
});
    }

     

    const linhasOrientacoes = orientacao.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.discente}</td>
            <td>{i.titulo}</td>
            <td>{i.tipo}</td>
            <td>{i.ano}</td>
        </tr>
    ));

    // const rotuloOrientacoes = (
    // <tr>
    //     <th>Discente</th>
    //     <th>Título</th>
    //     <th>Tipo</th>
    //     <th>Ano</th>
    // </tr>
    // );

    const rotuloOrientacoes = [
        <th key="discente">Discente</th>,
        <th key="titulo">Título</th>,
        <th key="tipo">Tipo</th>,
        <th key="ano">Ano</th>,
      ];
      

    const linhasProducao = producao.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.titulo}</td>
            <td>{i.local}</td>
            <td>{i.tipo}</td>
            <td>{i.qualis}</td>
            <tc>{`${i.ano}`}</tc>
        </tr>
    ));

    const rotuloProducao = (
    <tr>
        <th>Título</th>
        <th>Local</th>
        <th>Tipo</th>
        <th>Qualis</th>
        <th>Ano</th>
    </tr>
    );

    const linhasTecnicas = tecnica.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.titulo}</td>
            <td>{i.tipo}</td>
            <td>{i.ano}</td>
        </tr>
    ));

    const rotuloTecnicas = (
    <tr>
        <th>Título</th>
        <th>Tipo</th>
        <th>Ano</th>
    </tr>
    );
        

    return (
        
        <div className="wrapper">
            <Navbar titulo="SPPG"/>
            <div className="content-wrapper">
                <Header titulo={nome}/>

                <div className="content">      
                    <div className="container">
                        <div className="container-fluid">
                            <Filtros programas={programas}
                                    filtroProg={progSel} onProgChange={setProgSel} 
                                    filtroAnoIni={anoIni} onAnoIniChange={setAnoIni}
                                    filtroAnoFim={anoFim} onAnoFimChange={setAnoFim}
                                    onSearch={onSearch}
                                    />
                            <Indicadores dados={indicadores}/>
                            <Graficos titulo={"Produção em Congresso vs Qualis"} dadosBrutos={graficoC}/>
                            <Graficos titulo={"Produção em Periodicos vs Qualis"} dadosBrutos={graficoP}/>
                            <TabelaALL categorias={rotuloOrientacoes} linhas={linhasOrientacoes} titulo={"Orientações"}></TabelaALL>
                            <TabelaALL categorias={rotuloProducao} linhas={linhasProducao} titulo={"Artigos"}></TabelaALL>
                            <TabelaALL categorias={rotuloTecnicas} linhas={linhasTecnicas} titulo={"Técnicas"}></TabelaALL>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    );

}