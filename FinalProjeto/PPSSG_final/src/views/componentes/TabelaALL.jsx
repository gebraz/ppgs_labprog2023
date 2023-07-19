import React, { useState } from 'react';
import ReactPaginate from 'react-paginate';

export default function Tabela({ categorias, linhas, titulo }) {
  const [currentPage, setCurrentPage] = useState(0);
  const itemsPerPage = 10; // Número de linhas por página

  function generateRows() {
    const startIndex = currentPage * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return linhas.slice(startIndex, endIndex);
  }

  const pageCount = Math.ceil(linhas.length / itemsPerPage);

  const handlePageChange = (selectedPage) => {
    setCurrentPage(selectedPage.selected);
  };

  const header = <tr>{categorias}</tr>;
  const rows = generateRows();

  return (
    <div>
      <h3>{titulo}</h3>
      <Table header={header} data={rows} />
      <ReactPaginate
        previousLabel={'Anterior'}
        nextLabel={'Próxima'}
        breakLabel={'...'}
        pageCount={pageCount}
        marginPagesDisplayed={2}
        pageRangeDisplayed={5}
        onPageChange={handlePageChange}
        containerClassName={'pagination'}
        activeClassName={'active'}
      />
    </div>
  );
}

function Table({ header, data }) {
  return (
    <table>
      <thead>{header}</thead>
      <tbody>{data}</tbody>
    </table>
  );
}
