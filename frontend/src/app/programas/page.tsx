'use client';
import Filters from '@/components/Filters';
import SummaryCard from '@/components/SummaryCard';
import { StackedBarChart } from '@/components/BarChart';
import Table from '@/components/Table';
import useProgramaController from './controller';
import InputText from '@/components/InputText';
const data = [
    { id: 1, docente: 'Carlos', a1: 25 }
];
type inputTypes = 'ano_inicial' | 'ano_final' | 'programa';

export default function Programas() {
    const { formik, getPrograma } = useProgramaController();

    return (
        <div className="flex flex-col h-full w-full text-center mx-8" >
            <p className='text-start my-10 font-bold text-lg' >Pesquisar por programas</p>
            <div className='flex justify-start'>
                <InputText className='w-96' label='Programa' placeholder='Nome do programa' value={ formik.values.nome } onChange={ (e) => {
                    formik.setFieldValue('nome', e);
                } } />
                <InputText label='Ano inicial' placeholder='2023' value={ formik.values.ano_inicial.toString() } onChange={ (e) => {
                    formik.setFieldValue('ano_inicial', e);
                } } />
                <InputText label='Ano final' placeholder='2023' value={ formik.values.ano_final.toString() } onChange={ (e) => {
                    formik.setFieldValue('ano_final', e);
                } } />


            </div>
            <button onClick={ async (e) => {
                e.preventDefault();
                console.log('formik', formik.values);
                await getPrograma(formik.values.nome);
            } } className='w-40 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-5' >Pesquisar</button>
            <p className='text-start my-10 font-bold text-lg' >Indicadores Capes</p>

            <div className='w-full flex'>
                <SummaryCard label='Total produções' value='200' />
                <SummaryCard label='I Geral' value='17,43' />
                <SummaryCard label='I Restrito' value='16,45' />
                <SummaryCard label='I Não Restrito' value='0,99' />

            </div>
            <p className='text-start my-10 font-bold text-lg' >Produção vs Qualis</p>
            <StackedBarChart data={ data } />
            <p className='text-start my-10 font-bold text-lg' >Relação de docentes</p>
            <Table page='docentes' data={ data } columns={ ['docente', 'a1', 'a2', 'a3', 'a4', 'b1', 'b2', 'b3', 'b4'] } />

        </div>
    );
}