import Table from '@/components/Table';
const data = [
  { id: 1, docente: 'Carlos', a1: 25 }
];
export default function Producoes() {
  return (
    <div className="flex flex-col h-full w-full text-center mx-8" >

      <p className='text-start my-10 font-bold text-lg' >Gerenciar dados de produção</p>
      <Table selectable={ false } data={ data } columns={ ['ano', 'docente', 'titulo', 'local', 'orientacao', 'estatisticas', 'acoes'] } />

    </div>
  );
}