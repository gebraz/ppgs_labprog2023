//Modificar as info box  
export default function Indicadores({dados}) {
    return (
        <>
        <h5 className="mb-2">Indicadores Capes</h5>
        <div className="row">
            <Infobox nome="Total Produções" valor={dados.totalProd} bg="gray" icon="fa-copy" />
            <Infobox nome="I Geral" valor={dados.indiceGeral} bg="info" icon="fa-envelope" />
            <Infobox nome="I Restrito" valor={dados.indiceRest} bg="success" icon="fa-flag" />
            <Infobox nome="I Não Restrito" valor={dados.indiceNRest} bg="warning" icon="fa-copy" />
        </div>
        </>
    );
}

function Infobox({nome, valor, bg, icon}){
    return (
        <div className="col-md-3 col-sm-6 col-12">
            <div className="info-box">
                <span className={`info-box-icon ${bg}`}>
                    <i className={"far " + icon}></i>
                </span>
                <div className="info-box-content">
                    <span className="info-box-text">{nome}</span>
                    <span className="info-box-number">{valor}</span>
                </div>                
            </div>            
        </div> 
    );
}