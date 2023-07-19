import React from 'react';

export default function ProductionTable({ data }) {
  return (
    <div className="container-xl">
      <div className="table-responsive">
        <div className="table-wrapper">
          <table className="table table-striped table-hover">
            <thead>
              <tr>
                <th>Ano</th>
                <th>Docente</th>
                <th>Título</th>
                <th>Local</th>
                <th>Orientação?</th>
                <th>Estatísticas</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {data.map((item, index) => (
                <tr key={index}>
                  <td>{item.ano}</td>
                  <td>{item.docente}</td>
                  <td>{item.titulo}</td>
                  <td>{item.local}</td>
                  <td>{item.orientacao}</td>
                  <td>{item.estatisticas}</td>
                  <td>
                    <a href="#addOrientacao" className="edit" data-toggle="modal">
                      <i className="material-icons" data-toggle="tooltip" title="Orientações">
                        &#xe7fe;
                      </i>
                    </a>
                    <a href="#addEstatisticas" className="data" data-toggle="modal">
                      <i className="material-icons" data-toggle="tooltip" title="Estatísticas">
                        &#xe8b8;
                      </i>
                    </a>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="clearfix">
            <div className="hint-text">
              Exibindo <b>5</b> de <b>25</b> entradas
            </div>
            <ul className="pagination">
              <li className="page-item disabled">
                <a href="#">Anterior</a>
              </li>
              <li className="page-item">
                <a href="#" className="page-link">
                  1
                </a>
              </li>
              <li className="page-item">
                <a href="#" className="page-link">
                  2
                </a>
              </li>
              <li className="page-item active">
                <a href="#" className="page-link">
                  3
                </a>
              </li>
              <li className="page-item">
                <a href="#" className="page-link">
                  4
                </a>
              </li>
              <li className="page-item">
                <a href="#" className="page-link">
                  5
                </a>
              </li>
              <li className="page-item">
                <a href="#" className="page-link">
                  Próxima
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
