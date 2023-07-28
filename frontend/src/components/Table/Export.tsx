import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { useRef } from 'react';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { saveAs } from 'file-saver';
export function useExportDatatable() {
  const dt = useRef<DataTable>(null);
  const exportColumns = (cols: any) => cols.map((col: any) => ({ title: col.header, dataKey: col.field }));


  const exportCSV = (selectionOnly: any) => {
    dt.current.exportCSV({ selectionOnly });
  };

  const exportPdf = (data, exportColumns) => {

    const doc = new jsPDF.default(0, 0);

    doc.autoTable(exportColumns, data);
    doc.save('products.pdf');

  };

  const exportExcel = (data) => {
    import('xlsx').then((xlsx) => {
      const worksheet = xlsx.utils.json_to_sheet(data);
      const workbook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
      const excelBuffer = xlsx.write(workbook, {
        bookType: 'xlsx',
        type: 'array'
      });

      saveAsExcelFile(excelBuffer, 'arquivos');
    });
  };

  const saveAsExcelFile = (buffer, fileName) => {

    let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    let EXCEL_EXTENSION = '.xlsx';
    const data = new Blob([buffer], {
      type: EXCEL_TYPE
    });

    saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
  };

  return {
    dt,
    exportCSV,
    exportPdf,
    exportExcel,
    exportColumns

  };
};








