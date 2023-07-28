import { rest } from '@/api';
import useDocenteController from '@/app/docentes/controller';
import { Docente } from '@/models/Docente';
import { Producao } from '@/models/Producao';
import { useFormik } from 'formik';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { set } from 'zod';

export function useProducoesController() {
  const [docentes, setDocentes] = useState<Docente[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [selectedDocente, setSelectedDocente] = useState<Docente>();
  const [producoes, setProducoes] = useState<Producao[]>([] as Producao[]);
  const [students, setStudents] = useState<any[]>([] as any[]);
  const [visibleOrientacao, setOrientacaoVisible] = useState<boolean>(false);
  const [visibleEstatistica, setEstatisticaVisible] = useState<boolean>(false);
  const { getDocente } = useDocenteController();
  const docente: Docente = useSelector((state) => state.docente.value);

  async function getAllDocentes() {
    try {
      setLoading(true);
      const docentesResponse = await rest.get('/docente/obterDocentes');

      if (docentesResponse.data.length === 0) {
        alert('Docentes não encontrados');
        return;
      }

      setDocentes(docentesResponse.data);
      await getDocente(docentesResponse.data[0].id);
      setLoading(false);
    } catch (error) {
      console.log('🚀 ~ file: index.ts:20 ~ getAllDocentes ~ error:', error);
      alert('Erro ao trazer docentes');
      setLoading(false);

      return;
    }
  }

  async function informarEstatisticasProducao(producao: Producao) {
    setLoading(true);
    let producaoAux = { ...producao };
    delete producaoAux.docente;
    delete producaoAux.estatisticas;
    delete producaoAux.orientacao;

    producaoAux.qtdGrad = Number(formik.values.qtdGrad) || 0;
    producaoAux.qtdMestrado = Number(formik.values.qtdMestrado) || 0;
    producaoAux.qtdDoutorado = Number(formik.values.qtdDoutorado) || 0;
    try {
      const producaoResponse = await rest.post('/producao/informarEstatisticasProducao', producaoAux);
      getDocente(docente.id.toString());
      setLoading(false);
      alert('Estatísticas atualizadas com sucesso');
    } catch (error) {
      alert('Erro ao informar estatísticas da produção');
      setLoading(false);
      setLoading(false);

      return;
    }
  }

  let formik = useFormik({
    initialValues: {
      qtdGrad: 0,
      qtdMestrado: 0,
      qtdDoutorado: 0,
    },
    onSubmit: async (values) => {},
  });

  return {
    getAllDocentes,
    getDocente,
    docentes,
    docente,
    loading,
    producoes,
    setProducoes,
    students,
    formik,
    setStudents,
    informarEstatisticasProducao,
    visibleOrientacao,
    setOrientacaoVisible,
    visibleEstatistica,
    setEstatisticaVisible,
  };
}
