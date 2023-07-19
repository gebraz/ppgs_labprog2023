'use client';
import React from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';


interface TableProps {
  data: any[],
  columns: any[];
}
export default function Table(props: TableProps) {
  const { data, columns } = props;
  console.log("🚀 ~ file: index.tsx:13 ~ Table ~ data:", data);

  return (
    <div className="card w-full ">
      <DataTable onRowClick={ (e) => {
        console.log(e.data);
      } } selection={ true } selectionMode="single" filterDisplay='menu' emptyMessage='Não possui registros' className='w-8/12 ' value={ data } paginator rows={ 5 } rowsPerPageOptions={ [5, 10, 25, 50] } tableStyle={ { minWidth: '50rem' } }>
        {
          columns.length > 0 && columns.map((col: string) => (
            col === 'docente' ? (
              <Column sortable key={ col } field={ col } header={ col[0].toUpperCase() + col.substring(1, col.length) } style={ { width: '30%' } }></Column>
            ) : (
              <Column sortable key={ col } field={ col } header={ col[0].toUpperCase() + col.substring(1, col.length) } style={ { width: '20%' } }></Column>
            )

          ))
        }

      </DataTable>
    </div>
  );
}
