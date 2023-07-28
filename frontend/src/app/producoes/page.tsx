'use client';
import Table from '@/components/Table';
import { Dropdown } from 'primereact/dropdown';
import { useProducoesController } from './controller';
import { useEffect, useState } from 'react';
import { Docente } from '@/models/Docente';
import { Dialog } from 'primereact/dialog';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Tooltip } from 'primereact/tooltip';
import { MultiSelect } from 'primereact/multiselect';
import { BsPersonAdd, BsCardChecklist } from 'react-icons/bs';
import { MdReportGmailerrorred } from 'react-icons/md';
import InputText from '@/components/InputText';
import { Producao } from '@/models/Producao';

export default function Producoes() {

  const { docentes, getAllDocentes, getDocente, visibleOrientacao, setOrientacaoVisible, visibleEstatistica, setEstatisticaVisible, students, setStudents, loading, formik, informarEstatisticasProducao, docente } = useProducoesController();

  const [selectedDocente, setSelectedDocente] = useState<Docente>({} as Docente);
  const [saved, setSaved] = useState<boolean>(false);

  const [selectedProducao, setSelectedproducao] = useState<Producao>({} as Producao);
  useEffect(() => {
    getAllDocentes();
  }, []);

  useEffect(() => {
    if (docente.id >= 0) {
      let producoes = docente.producoes.map(producao => {
        let qtdGrad = producao.qtdGrad ? producao.qtdGrad : 0;
        let qtdMestrado = producao.qtdMestrado ? producao.qtdMestrado : 0;
        let qtdDoutorado = producao.qtdDoutorado ? producao.qtdDoutorado : 0;
        let orientacao = (qtdGrad + qtdDoutorado + qtdMestrado) > 0 ? 'Sim' : 'Não';

        if (producao.qtdGrad && producao?.qtdGrad > 0) {
          console.log(producao.qtdGrad);
          console.log(producao.qtdMestrado);
          console.log(producao.qtdDoutorado);
        }


        return {
          ...producao,
          docente: docente.nome,
          estatisticas: `${qtdGrad}G | ${qtdMestrado}M | ${qtdDoutorado}D`,
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
  }, [docente, saved]);

  function ActionTemplate(rowData: any) {
    try {
      return <div className='w-full flex justify-around align-center'>
        <BsPersonAdd

          data-pr-tooltip="Adicionar orientandos"
          data-pr-position="right"
          data-pr-at="right+5 top"
          data-pr-my="left center-2"
          onClick={ () => { setSelectedproducao(rowData); setOrientacaoVisible(true); } } className='addOri w-5 h-5 hover:scale-150 hover:text-green-600' />
        <Tooltip target=".addOri" />
        <BsCardChecklist
          onClick={ () => {
            setSelectedproducao(rowData);
            setEstatisticaVisible(true);
            formik.setFieldValue('qtdGrad', rowData.qtdGrad);
            formik.setFieldValue('qtdMestrado', rowData.qtdMestrado);
            formik.setFieldValue('qtdDoutorado', rowData.qtdDoutorado);
          } }
          data-pr-tooltip="Alterar estatísticas"
          data-pr-position="right"
          data-pr-at="right+5 top"
          data-pr-my="left center-2"
          className='alterEstat w-5 h-5 hover:scale-150 hover:text-yellow-600' />
        <Tooltip target=".alterEstat" />
      </div>;
    } catch (error) {
      return (
        <MdReportGmailerrorred className='w-5 h-5' />
      );
    }
  }
  function FooterOrientacao(rowData: any) {
    try {
      return <div className='w-full flex justify-end align-end'>
        <div className='flex'>

          <button disabled={ loading } onClick={ (e) => {
            e.preventDefault();
            setStudents([]);
            setOrientacaoVisible(false);
          } } className='flex justify-around w-40 bg-transparent border-2 border-red-700 text-red-700 hover:bg-red-700 hover:text-white font-bold py-2 px-4 rounded mt-5' >Cancelar  </button>
          <button disabled={ loading } onClick={ (e) => {
            e.preventDefault();
            setOrientacaoVisible(false);

          } } className='flex justify-around w-40 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mt-5' >Salvar  </button>


        </div>
      </div>;
    } catch (error) {
      return (
        <MdReportGmailerrorred className='w-5 h-5' />
      );
    }
  }
  function FooterEstatistica(e) {
    try {
      return <div className='w-full flex justify-end align-end'>
        <div className='flex'>

          <button disabled={ loading } onClick={ (e) => {
            e.preventDefault();
            formik.setFieldValue('qtdGrad', 0);
            formik.setFieldValue('qtdMestrado', 0);
            formik.setFieldValue('qtdDoutorado', 0);
            setEstatisticaVisible(false);
          } } className='flex justify-around w-40 bg-transparent border-2 border-red-700 text-red-700 hover:bg-red-700 hover:text-white font-bold py-2 px-4 rounded mt-5' >Cancelar  </button>
          <button disabled={ loading } onClick={ (e) => {
            e.preventDefault();
            informarEstatisticasProducao(selectedProducao);
            formik.setFieldValue('qtdGrad', 0);
            formik.setFieldValue('qtdMestrado', 0);
            formik.setFieldValue('qtdDoutorado', 0);
            setEstatisticaVisible(false);
            setSaved(prev => prev!);
          } } className='flex justify-around w-40 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mt-5' >Salvar  </button>


        </div>
      </div>;
    } catch (error) {
      return (
        <MdReportGmailerrorred className='w-5 h-5' />
      );
    }
  }

  return (
    <div className="flex flex-col h-full w-full text-center mx-8" >

      <p className='text-start my-10 font-bold text-lg' >Gerenciar dados de produção</p>

      <Dropdown name='docente' style={ { fontWeight: 'bolder', borderWidth: 2 } } className='font-bold w-72 m-0 h-11 mt-8 mb-4 mr-4 border-2' onChange={ async (e) => {
        await getDocente(e.value);
      } } optionLabel='nome' optionValue='id' options={ docentes } value={ docente.id } placeholder='Escolha um docente...' />

      <div className="card w-full flex mb-10 ">
        <DataTable loading={ loading } selectionMode="single" emptyMessage='Não possui registros' className='w-10/12 ' value={ selectedDocente?.producoes } paginator rows={ 10 } rowsPerPageOptions={ [5, 10, 25, 50] } tableStyle={ { minWidth: '50rem', } }>
          <Column sortable key={ 'ano' } field={ 'ano' } header={ 'Ano' } style={ { width: '1%' } }></Column>
          <Column sortable key={ 'docente' } field={ 'docente' } header={ 'Docente' } style={ { width: '20%', fontWeight: 'bolder' } }></Column>
          <Column sortable key={ 'titulo' } field={ 'titulo' } header={ 'Título' } style={ { width: '30%' } }></Column>
          <Column sortable key={ 'nomeLocal' } field={ 'nomeLocal' } header={ 'Local' } style={ { width: '30%' } }></Column>
          <Column sortable key={ 'orientacao' } field={ 'orientacao' } header={ 'Orientação' } style={ { width: '1%' } }></Column>
          <Column sortable key={ 'estatisticas' } field={ 'estatisticas' } header={ 'Estatísticas' } style={ { width: '10%' } }></Column>
          <Column sortable key={ 'acoes' } header={ 'Ações' } body={ ActionTemplate } style={ { width: '1%' } }></Column>
        </DataTable>
      </div>
      <Dialog header="Informe as orientações" footer={ FooterOrientacao } visible={ visibleOrientacao } style={ { width: '50vw', height: '30vw' } } breakpoints={ { '960px': '75vw', '641px': '100vw' } } onHide={ () => {
        setSelectedproducao({} as Producao);
        setOrientacaoVisible(false);
        formik.setFieldValue('qtdGrad', 0);
        formik.setFieldValue('qtdMestrado', 0);
        formik.setFieldValue('qtdDoutorado', 0);
      } }>
        <p className="mb-4 font-semibold text-xl">
          Selecione para a produção { selectedProducao.titulo }
        </p>
        <MultiSelect value={ students } onChange={ (e) => setStudents(e.value) } options={ [] } optionLabel="name" display="chip"
          placeholder='"Carlos..."' maxSelectedLabels={ 3 } className="w-full md:w-20rem" />
      </Dialog>
      <Dialog header="Altere Estatísticas" footer={ FooterEstatistica } visible={ visibleEstatistica } style={ { width: '50vw', height: '30vw' } } breakpoints={ { '960px': '75vw', '641px': '100vw' } } onHide={ () => {
        setSelectedproducao({} as Producao);
        setEstatisticaVisible(false);
        formik.setFieldValue('qtdGrad', 0);
        formik.setFieldValue('qtdMestrado', 0);
        formik.setFieldValue('qtdDoutorado', 0);
      } }>
        <p className="mb-4 font-semibold text-xl">
          Selecione para a produção { selectedProducao.titulo }        </p>
        <InputText label='Quantidade de alunos de graduação' placeholder='0' value={ formik.values.qtdGrad?.toString() } onChange={ (e) => {
          formik.setFieldValue('qtdGrad', e);
        } } />
        <InputText label='Quantidade de alunos de mestrado' placeholder='0' value={ formik.values.qtdMestrado?.toString() } onChange={ (e) => {
          formik.setFieldValue('qtdMestrado', e);
        } } />
        <InputText label='Quantidade de alunos de doutorado' placeholder='0' value={ formik.values.qtdDoutorado?.toString() } onChange={ (e) => {
          formik.setFieldValue('qtdDoutorado', e);
        } } />
      </Dialog>
    </div>
  );
}