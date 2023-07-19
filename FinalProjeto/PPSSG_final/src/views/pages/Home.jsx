
import Navbar from "../componentes/Navbar";
import Header from "../componentes/Header";
import Filtros from "../componentes/Filtros";
import Indicadores from "../componentes/Indicadores";
import Graficos from "../componentes/Graficos";
import Tabela from "../componentes/Tabela";

import { useState } from "react";
import { useEffect } from "react";
import axios from 'axios'

// import TabelaALL from "../componentes/TabelaALL";

export default function Home(){
    
    const [progSel, setProgSel] = useState(15);
    const [anoIni, setAnoIni] = useState(2019);
    const [anoFim, setAnoFim] = useState(2023);
    const [graph,Setgraph] = useState({})
    const [indicadores, setIndicadores] = useState({});
    const[docenteDados, setdocenteDados]=useState([]);
   
    
    const programas = [
        {id:1, nome:"PPGCC"},
    ]

    const client = axios.create({
        baseURL: "http://localhost:8080/api/docente/" 
        
    });
    const client2 = axios.create({
        baseURL: "http://localhost:8080/api/programa/" 
        
    });


    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        onSearch();
        
    },[] 
       
    )

    
    function onSearch() {
        client.get(`indicadores/${progSel}/${anoIni}/${anoFim}`)
            .then(                
                (response) => {
                    console.log(response.data)
                    setIndicadores(response.data)
                }
            ).catch(error => {
                console.log(error.response);
            });

        client.get(`teste/${anoIni}/${anoFim}`)
            .then(response => {
                console.log(Array.isArray(response.data));
                console.log(response.data);
                setdocenteDados(response.data);
            })
            .catch(error => {
                console.log(error.response);
            });
        
        client2.get(`obterGrafico/${progSel}/${anoIni}/${anoFim}`)
            .then(                
                (response) => {
                    console.log(Array.isArray(response.data))
                    console.log(response.data)
                    Setgraph(response.data)
                }
            ).catch(error => {
                console.log(error.response);
            });
                

            
    }

    return (
        
        <div className="wrapper">
            <Navbar titulo="SPPG"/>
            <div className="content-wrapper">
                <Header titulo="Docente"/>

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
                            <Graficos titulo={"Produção vs Qualis"}  dadosBrutos={ graph }/>
                            <Tabela dados={docenteDados} header = {'Docentes'} titulo ={'Docente'}/>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    );
}