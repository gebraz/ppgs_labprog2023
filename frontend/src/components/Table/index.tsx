'use client';
import React from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { useRouter } from 'next/navigation';
import { useSelector } from 'react-redux';

interface TableProps {
  data: any[],
  columns: any[];
  selectable?: boolean;
  page?: string;
}
export default function Table(props: TableProps) {
  const { data, columns, selectable = true, page } = props;
  const programa = useSelector((state: any) => state.programa.value);
  const router = useRouter();
  return (
    <div className="card w-full flex mb-10 ">
      <DataTable onRowClick={ (e) => {
        if (e.data.id && selectable && page)
          router.push(`${page}/${e.data.id}`);
      } } selection={ selectable } selectionMode="single" filterDisplay='menu' emptyMessage='Não possui registros' className='w-10/12 ' value={ data } paginator rows={ 5 } rowsPerPageOptions={ [5, 10, 25, 50] } tableStyle={ { minWidth: '50rem' } }>
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
