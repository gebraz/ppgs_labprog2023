'use client';
import Filters from '@/components/Filters';
import SummaryCard from '@/components/SummaryCard';
import { StackedBarChart } from '@/components/BarChart';
import Table from '@/components/Table';
import useProgramaController from './controller';
import InputText from '@/components/InputText';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { ProgramaState } from '@/store/programa';
import Loading from '../loading';
import ReactLoading from 'react-loading';
import AutoComplete from '@/components/AutoComplete';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { A1Template, A2Template, A3Template, A4Template, B1Template, B2Template, B3Template, B4Template, CTemplate, TotalTemplate } from '@/components/Table/DocenteQualisTemplate';
import { useRouter } from 'next/navigation';
const data = [
    { id: 1, docente: 'Carlos', a1: 25 }
];
type inputTypes = 'ano_inicial' | 'ano_final' | 'programa';

export default function Programas() {
    const { formik, getPrograma, getAllProgramas, programas, qualisProducao, qualisType, loading } = useProgramaController();
    const programa: any = useSelector((state: any) => state.programa.value);
    const router = useRouter();

    useEffect(() => {
        getAllProgramas();
    }, []);



    return (
        <div className="flex flex-col h-full w-full text-center mx-8" >
            <p className='text-start my-10 font-bold text-lg' >Pesquisar por programas</p>
            <div className='flex justify-start'>
                <Dropdown style={ { fontWeight: 'bolder', borderWidth: 2 } } className='font-bold w-72 m-0 h-11 mt-8 mr-4 border-2' onChange={ (e) => formik.setFieldValue('nome', e.value) } optionLabel='nome' optionValue='nome' options={ programas } value={ formik.values.nome } placeholder='Nome do programa' />
                <InputText label='Ano inicial' placeholder='2023' value={ formik.values.ano_inicial.toString() } onChange={ (e) => {
                    formik.setFieldValue('ano_inicial', e);
                } } />
                <InputText label='Ano final' placeholder='2023' value={ formik.values.ano_final.toString() } onChange={ (e) => {
                    formik.setFieldValue('ano_final', e);
                } } />


            </div>
            <button disabled={ loading } onClick={ async (e) => {
                e.preventDefault();
                await getPrograma(formik.values.nome);
            } } className='flex justify-around w-40 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-5' >{ loading ? <ReactLoading type='bars' className='text-sm' height={ '20%' } width={ '20%' } /> : <span>Pesquisar</span> }  </button>
            <p className='text-start my-10 font-bold text-lg' >Indicadores Capes</p>

            <div className='w-full flex'>
                <SummaryCard label='Total produções' value={ programa.qtdProducoes.toString() } />
                <SummaryCard label='I Geral' value={ programa.indice.indiceGeral.toFixed(2) } />
                <SummaryCard label='I Restrito' value={ programa.indice.indiceRest.toFixed(2) } />
                <SummaryCard label='I Não Restrito' value={ programa.indice.indiceNRest.toFixed(2) } />

            </div>
            <p className='text-start my-10 font-bold text-lg' >Produção vs Qualis</p>
            <StackedBarChart data={ qualisProducao } dataKeys={ qualisType } />
            <p className='text-start my-10 font-bold text-lg' >Relação de docentes</p>
            <div className="card w-full flex mb-10 ">
                <DataTable onRowClick={ (e) => {
                    if (e.data.id)
                        router.push(`docentes/${e.data.id}`);
                } } selection={ true } selectionMode="single" emptyMessage='Não possui registros' className='w-10/12 ' value={ programa.docentes } paginator rows={ 10 } rowsPerPageOptions={ [5, 10, 25, 50] } tableStyle={ { minWidth: '50rem', } }>
                    <Column sortable key={ 'docente' } field={ 'docente' } header={ 'Docente' } style={ { width: '30%', fontWeight: 'bolder' } }></Column>
                    <Column sortable key={ 'a1' } header={ 'A1' } style={ { width: '10%' } } body={ A1Template }></Column>
                    <Column sortable key={ 'a2' } header={ 'A2' } style={ { width: '10%  ', } } body={ A2Template }></Column>
                    <Column sortable key={ 'a3' } header={ 'A3' } style={ { width: '10%' } } body={ A3Template }></Column>
                    <Column sortable key={ 'a4' } header={ 'A4' } style={ { width: '10%' } } body={ A4Template }></Column>
                    <Column sortable key={ 'b1' } header={ 'B1' } style={ { width: '10%' } } body={ B1Template }></Column>
                    <Column sortable key={ 'b2' } header={ 'B2' } style={ { width: '10%' } } body={ B2Template }></Column>
                    <Column sortable key={ 'b3' } header={ 'B3' } style={ { width: '10%' } } body={ B3Template }></Column>
                    <Column sortable key={ 'b4' } header={ 'B4' } style={ { width: '10%' } } body={ B4Template }></Column>
                    <Column sortable key={ 'c' } header={ 'C' } style={ { width: '10%' } } body={ CTemplate }></Column>
                    <Column sortable key={ 'total' } header={ 'Total' } style={ { width: '10%' } } body={ TotalTemplate }></Column>
                </DataTable>
            </div>
        </div>
    );
}