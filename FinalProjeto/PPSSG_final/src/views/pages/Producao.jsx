
import Navbar from "../componentes/Navbar";
import Header from "../componentes/Header";
import Filtros from "../componentes/Filtros";
import Indicadores from "../componentes/Indicadores";
import ProductionTable from '../componentes/ProductionTable'
// import Graficos from "../componentes/Graficos";
// import Tabela from "../componentes/Tabela";

import { useState } from "react";
import { useEffect } from "react";
import axios from 'axios'

export default function Producao(){
 
    const [progSel, setProgSel] = useState(1);
    const [anoIni, setAnoIni] = useState(2019);
    const [anoFim, setAnoFim] = useState(2023);
    
    const [indicadores, setIndicadores] = useState({});
    const dados = [
        {id:1, nome:"Alexandre César Muniz de Oliveira", A1:1, A2:0, A3:1, A4:0, B1:1, B2:0, B3:1, B4:0},
        {id:1, nome:"Geraldo Braz Junior", A1:1, A2:0, A3:1, A4:0, B1:1, B2:0, B3:1, B4:0},
    ]
    const data = [
        {
          ano: '2022',
          docente: 'Geraldo Braz Junior',
          titulo: 'Artigo sobre cadeia evolutiva',
          local: 'Teste 1',
          orientacao: 'Sim',
          estatisticas: '1G|0M|0D',
        },
        {
          ano: '2021',
          docente: 'Geraldo Braz Junior',
          titulo: 'Aquele outro',
          local: 'Teste 2',
          orientacao: 'Sim',
          estatisticas: '1G|1M|0D',
        }
      ];
      
    const programas = [
        {id:1, nome:"PPGCC"},
        {id:2, nome:"DCCMAPI"},
    ]

    const client = axios.create({
        baseURL: "http://localhost:8080/api/home/" 
    });

    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        onSearch();
        },[] 
    )

    
    function onSearch() {
        client.get(`indicadores?programa=${progSel}&anoIni=${anoIni}&anoFim=${anoFim}`)
            .then(                
                (response) => {
                    console.log(response.data)
                    setIndicadores(response.data)
                }
            ).catch(error => {
                console.log(error.response);
            });
    }
    
    return (
        
        <div className="wrapper">
            <Navbar titulo="SPPG"/>
            <div className="content-wrapper">
                <Header titulo="Programas"/>

                <div className="content">      
                    <div className="container">
                        <div className="container-fluid">
                            <ProductionTable data ={data}/>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    );
}