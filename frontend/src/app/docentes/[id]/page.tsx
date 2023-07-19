import Filters from '@/components/Filters';
import SummaryCard from '@/components/SummaryCard';
import { StackedBarChart } from '@/components/BarChart';
import Table from '@/components/Table';
const data = [
    { id: 1, docente: 'Carlos', a1: 25 }
];
export default function Programas() {
    return (
        <div className="flex flex-col h-full w-full text-center mx-8" >
            <p className='text-start my-10 font-bold text-lg text-white bg-gray-800 p-4 w-6/12 rounded-md' >Docente: </p>
            <Filters showInputs={ ['ano_inicial', 'ano_final'] } />
            <p className='text-start my-10 font-bold text-lg' >Indicadores Capes</p>

            <div className='w-full flex'>
                <SummaryCard label='Total produções' value='200' />
                <SummaryCard label='I Geral' value='17,43' />
                <SummaryCard label='I Restrito' value='16,45' />
                <SummaryCard label='I Não Restrito' value='0,99' />

            </div>
            <div className='flex my-4'>
                <div className="border-2 shadow-md hover:shadow-xl p-2 mr-2 cursor-pointer">
                    <p className='text-start my-10 font-bold text-lg' >Produção em Periódicos vs Qualis</p>
                    <StackedBarChart width={ 700 } data={ data } /></div>
                <div className="border-2 shadow-md hover:shadow-xl p-2 cursor-pointer">
                    <p className='text-start my-10 font-bold text-lg' >Produção em Congressos vs Qualis</p>
                    <StackedBarChart width={ 700 } data={ data } /></div>

            </div>
            <div className="flex">
                <div className='border-2 shadow-md hover:shadow-xl p-2 mr-2 cursor-pointer'>
                    <p className='text-start my-10 font-bold text-lg' >Produção em periódicos vs Qualis</p>
                    <StackedBarChart width={ 700 } data={ data } />
                </div>
            </div>


            <p className='text-start my-10 font-bold text-lg' >Orientações</p>
            <Table data={ data } columns={ ['discente', 'titulo', 'tipo', 'ano'] } />
            <p className='text-start my-10 font-bold text-lg' >Artigos</p>
            <Table data={ data } columns={ ['titulo', 'local', 'tipo', 'qualis', 'ano'] } />
            <p className='text-start my-10 font-bold text-lg' >Técnicas</p>
            <Table data={ data } columns={ ['titulo', 'tipo', 'ano'] } />



        </div>
    );
}