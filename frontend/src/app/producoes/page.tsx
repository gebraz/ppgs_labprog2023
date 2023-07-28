'use client';
import Table from '@/components/Table';
import { Dropdown } from 'primereact/dropdown';
import { useProducoesController } from './controller';
import { useEffect, useState } from 'react';
import { Docente } from '@/models/Docente';

export default function Producoes() {

  const { docentes, getAllDocentes, getDocente, loading, producoes, setProducoes, docente } = useProducoesController();
  const [selectedDocente, setSelectedDocente] = useState<Docente>({} as Docente);
  useEffect(() => {
    getAllDocentes();
  }, []);

  useEffect(() => {
    if (docente.id >= 0) {
      let producoes = docente.producoes.map(producao => {
        let qtdGrad = producao.qtdGrad ? producao.qtdGrad : 0;
        let qtdMestrado = producao.qtdMestrado ? producao.qtdGrad : 0;
        let qtdDoutorado = producao.qtdDoutorado ? producao.qtdGrad : 0;
        let orientacao = (qtdGrad + qtdDoutorado + qtdMestrado) > 0 ? 'Sim' : 'Não';
        return {
          ...producao,
          docente: docente.nome,
          estatisticas: `${qtdGrad}G | ${qtdGrad}M | ${qtdDoutorado}D`,
          orientacao: orientacao
        };


      });
      setSelectedDocente(prev => {
        return {
          ...prev,
          producoes
        };
      });
    }
  }, [docente]);
  return (
    <div className="flex flex-col h-full w-full text-center mx-8" >

      <p className='text-start my-10 font-bold text-lg' >Gerenciar dados de produção</p>

      <Dropdown name='docente' style={ { fontWeight: 'bolder', borderWidth: 2 } } className='font-bold w-72 m-0 h-11 mt-8 mb-4 mr-4 border-2' onChange={ async (e) => {
        await getDocente(e.value);
      } } optionLabel='nome' optionValue='id' options={ docentes } value={ docente.id } placeholder='Escolha um docente...' />
      <Table isLoading={ loading } selectable={ false } data={ selectedDocente?.producoes } columns={ ['ano', 'docente', 'titulo', 'nomeLocal', 'orientacao', 'estatisticas', 'acoes'] } />

    </div>
  );
}