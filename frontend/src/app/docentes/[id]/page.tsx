'use client';
import SummaryCard from '@/components/SummaryCard';
import { StackedBarChart } from '@/components/BarChart';
import Table from '@/components/Table';
import { useParams } from 'next/navigation';
import { useEffect } from 'react';
import useDocenteController from '../controller';
import InputText from '@/components/InputText';
import ReactLoading from 'react-loading';
const data = [
    { id: 1, docente: 'Carlos', a1: 25 }
];
export default function Programas() {
    const { id } = useParams();
    const { getDocente, qualisProducao, qualisType, formik, docente, loading } = useDocenteController();

    useEffect(() => {
        getDocente(id);
    }, []);

    return (
        <div className="flex flex-col h-full w-full text-center mx-8" >
            <p className='text-start my-10 font-bold text-lg text-white bg-gray-800 p-4 w-6/12 rounded-md' >Docente: { docente?.nome }</p>
            <div className='flex justify-start'>

                <InputText label='Ano inicial' placeholder='2023' value={ formik.values.ano_inicial.toString() } onChange={ (e) => {
                    formik.setFieldValue('ano_inicial', e);
                } } />
                <InputText label='Ano final' placeholder='2023' value={ formik.values.ano_final.toString() } onChange={ (e) => {
                    formik.setFieldValue('ano_final', e);
                } } />


            </div>
            <button disabled={ loading } onClick={ async (e) => {
                e.preventDefault();
                await getDocente(formik.values.id);
            } } className='flex justify-around w-40 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-5' >{ loading ? <ReactLoading type='bars' className='text-sm' height={ '20%' } width={ '20%' } /> : <span>Pesquisar</span> }  </button>

            <p className='text-start my-10 font-bold text-lg' >Indicadores Capes</p>

            <div className='w-full flex'>
                <SummaryCard label='Total produções' value={ docente?.qtdProducoes.toString() } />
                <SummaryCard label='I Geral' value={ docente?.indice.indiceGeral.toFixed(2) } />
                <SummaryCard label='I Restrito' value={ docente?.indice.indiceRest.toFixed(2) } />
                <SummaryCard label='I Não Restrito' value={ docente?.indice.indiceNRest.toFixed(2) } />

            </div>
            <div className='flex my-4'>
                <div className="border-2 shadow-md hover:shadow-xl p-2 mr-2 cursor-pointer">
                    <p className='text-start my-10 font-bold text-lg' >Produção em Periódicos vs Qualis</p>
                    <StackedBarChart width={ 700 } data={ qualisProducao } dataKeys={ qualisType?.periodicos } name='periodicos' /></div>
                <div className="border-2 shadow-md hover:shadow-xl p-2 cursor-pointer">
                    <p className='text-start my-10 font-bold text-lg' >Produção em Congressos vs Qualis</p>
                    <StackedBarChart width={ 700 } data={ qualisProducao } dataKeys={ qualisType?.congressos } name='congressos' /></div>

            </div>



            <p className='text-start my-10 font-bold text-lg' >Orientações</p>
            <Table data={ docente.orientacoes } columns={ ['discente', 'titulo', 'tipo', 'ano'] } />
            <p className='text-start my-10 font-bold text-lg' >Artigos</p>
            <Table data={ docente.producoes } columns={ ['titulo', 'local', 'tipo', 'qualis', 'ano'] } />
            <p className='text-start my-10 font-bold text-lg' >Técnicas</p>
            <Table data={ data } columns={ ['titulo', 'tipo', 'ano'] } />



        </div>
    );
}