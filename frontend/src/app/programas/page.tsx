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
            <p className='text-start my-10 font-bold text-lg' >Pesquisar por programas</p>
            <Filters />
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