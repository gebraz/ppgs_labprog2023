import React, { useEffect } from 'react';
import $ from 'jquery';

function CabecalhoTabela({ header }) {
  return (
    <thead>
      <tr>
        <th>{header}</th>
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
  );
}

function CorpoTabela({ dados }) {
  const linhas = dados.map((item, index) => (
    <tr key={index}>
      <td>{item.nome}</td>
      <td>{item.a1}</td>
      <td>{item.a2}</td>
      <td>{item.a3}</td>
      <td>{item.a4}</td>
      <td>{item.b1}</td>
      <td>{item.b2}</td>
      <td>{item.b3}</td>
      <td>{item.b4}</td>
      <td> <a href={`/Docente/${item.id}`}>Mais</a> </td>
    </tr>
  ));
  
  return <tbody>{linhas}</tbody>;
}

export default function Tabela({ dados, header, titulo }) {
  useEffect(() => {
    $(function() {
      $('#example1').DataTable({
        responsive: true,
        lengthChange: false,
        autoWidth: false,
        buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis'],
      }).buttons().container().appendTo('#example1_wrapper .col-md-6:eq(0)');

      $('#example2').DataTable({
        paging: true,
        lengthChange: false,
        searching: false,
        ordering: true,
        info: true,
        autoWidth: false,
        responsive: true,
      });
    });
  }, []);

  return (
    <div className="card">
      <div className="card-header">
        <h3 className="card-title">{titulo}</h3>
      </div>

      <div className="card-body">
        <table id="example1" className="table table-bordered table-striped">
          <CabecalhoTabela header={header} />
          <CorpoTabela dados={dados} />
        </table>
      </div>
    </div>
  );
}
