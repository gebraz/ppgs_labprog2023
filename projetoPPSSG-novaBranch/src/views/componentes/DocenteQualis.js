const dados = [
    {id:1, nome:"Alexandre César Muniz de Oliveira", A1:1, A2:0, A3:1, A4:0, B1:1, B2:0, B3:1, B4:0},
    {id:1, nome:"Geraldo Braz Junior", A1:1, A2:0, A3:1, A4:0, B1:1, B2:0, B3:1, B4:0},
]

export default function DocenteQualis () {
    const linhas = dados.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.nome}</td>
            <td>{i.A1}</td>
            <td>{i.A2}</td>
            <td>{i.A3}</td>
            <td>{i.A4}</td>
            <td>{i.B1}</td>
            <td>{i.B2}</td>
            <td>{i.B3}</td>
            <td>{i.B4}</td>
            <td> <a href="Docente">Mais</a> </td>
        </tr>
    ));  

    return (
        <div class="card">
            <div class="card-header">
                <h3 class="card-title">Docentes</h3>
            </div>
            
            <div class="card-body">
            <table id="example1" class="table table-bordered table-striped">
                <thead>
                <tr>
                <th>Docente</th>
                <th>A1</th>
                <th>A2</th>
                <th>A3</th>
                <th>A4</th>
                <th>B1</th>
                <th>B2</th>
                <th>B3</th>
                <th>B4</th>
                <th>Detalhar</th>
                </tr>
                </thead>
                <tbody>
                    {linhas}
                </tbody>
                <tfoot>
                <tr>
                <th>Docente</th>
                <th>A1</th>
                <th>A2</th>
                <th>A3</th>
                <th>A4</th>
                <th>B1</th>
                <th>B2</th>
                <th>B3</th>
                <th>B4</th>
                <th>Detalhar</th>
                </tr>
                </tfoot>
            </table>
            </div>
        </div>
    )



}