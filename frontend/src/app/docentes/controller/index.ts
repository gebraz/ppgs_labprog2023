import * as Yup from 'yup';
import { useFormik } from 'formik';
import { rest } from '@/api';
import { useEffect, useState } from 'react';
import { setDocente } from '@/store/docente';
import { Docente } from '@/models/Docente';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch } from '@/store';
import moment from 'moment';
import { set } from 'zod';
const DocenteSchema = Yup.object().shape({
  id: Yup.string(),
  ano_inicial: Yup.number(),
  ano_final: Yup.number(),
});

type TIPOPRODUCAO = 'ARTIGO-PUBLICADO' | 'TRABALHO-EM-EVENTOS';

export default function useDocenteController() {
  const dispatch = useDispatch<AppDispatch>();
  const docente: Docente = useSelector((state) => state.docente.value);
  const [loading, setLoading] = useState<boolean>(false);
  const [qualisProducao, setQualisProducao] = useState<any>([]);
  const [qualisType, setQualisType] = useState<{
    periodicos: any[];
    congressos: any[];
  }>({
    periodicos: [],
    congressos: [],
  });
  const formik = useFormik({
    initialValues: {
      id: '',
      ano_inicial: 1950,
      ano_final: moment().year(),
    },
    validationSchema: DocenteSchema,
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
    },
  });

  const getDocente = async (id: string) => {
    try {
      setLoading(true);
      const docenteResponse = await rest.get(`/docente/obterDocente/${id}`);

      let docentePayload: Docente = {} as Docente;
      let docenteData = docenteResponse.data;
      if (docenteResponse.data.length === 0) {
        alert('Docente não encontrado');
        return;
      }

      const prodQualis = await getProdQualis(docenteData.id, formik.values.ano_inicial, formik.values.ano_final);
      if (!prodQualis) {
        return;
      }

      const orientacoes = await getDocenteOrientacoes(
        docenteData.id,
        formik.values.ano_inicial,
        formik.values.ano_final
      );

      const tecnicas = await getDocenteTecnicas(docenteData.id, formik.values.ano_inicial, formik.values.ano_final);

      docentePayload = {
        id: docenteData.id,
        nome: docenteData.nome,
        indice: {
          indiceGeral: prodQualis.indice.indiceGeral,
          indiceNRest: prodQualis.indice.indiceNRest,
          indiceRest: prodQualis.indice.indiceRest,
        },
        producoes: prodQualis.producoes,
        orientacoes: orientacoes,
        tecnicas: tecnicas,
        lattes: docenteData.lattes,
        qtdProducoes: prodQualis.producoes.length,
      };

      formik.setFieldValue('id', docenteData.id);
      setQualisProducao(populateQualisProducao(prodQualis.producoes));

      dispatch(setDocente(docentePayload));
      setLoading(false);
    } catch (error) {
      console.log('🚀 ~ file: index.ts:71 ~ getDocente ~ error:', error);
    }
  };

  const getDocenteOrientacoes = async (
    idDocente: string | number,
    anoInicial: number = 1950,
    anoFinal: number = moment().year()
  ) => {
    if (!anoInicial) {
      anoInicial = 1950;
      formik.setFieldValue('ano_inicial', 1950);
    }
    if (!anoFinal) {
      anoFinal = moment().year();
      formik.setFieldValue('ano_final', moment().year());
    }
    try {
      const response = await rest.get(`/docente/obterOrientacoes/${idDocente}/${anoInicial}/${anoFinal}`);
      if (response.data.length === 0) {
        alert('orientacoes não encontradas');
        return false;
      }
      return response.data;
    } catch (error) {}
  };
  const getDocenteTecnicas = async (
    idDocente: string | number,
    anoInicial: number = 1950,
    anoFinal: number = moment().year()
  ) => {
    if (!anoInicial) {
      anoInicial = 1950;
      formik.setFieldValue('ano_inicial', 1950);
    }
    if (!anoFinal) {
      anoFinal = moment().year();
      formik.setFieldValue('ano_final', moment().year());
    }
    try {
      const response = await rest.get(`/docente/obterTecnicas/${idDocente}/${anoInicial}/${anoFinal}`);
      if (response.data.length === 0) {
        alert('orientacoes não encontradas');
        return false;
      }
      return response.data;
    } catch (error) {}
  };

  function populateQualisProducao(producoes: any, tipo?: TIPOPRODUCAO) {
    let qualisProducao: {
      periodicos: any;
      congressos: any;
    } = {} as any;
    // Percorre o array de produções e preenche o objeto dataByYear
    let periodicosProducao = producoes.filter((producao: any) => producao.tipo === 'ARTIGO-PUBLICADO');
    let congressosProducao = producoes.filter((producao: any) => producao.tipo === 'TRABALHO-EM-EVENTOS');

    qualisProducao.periodicos = graphData(periodicosProducao);
    setQualisType((prev) => {
      return {
        ...prev,
        periodicos: Object.keys(qualisProducao.periodicos[0]).filter((qualis) => qualis !== 'name'),
      };
    });
    qualisProducao.congressos = graphData(congressosProducao);

    setQualisType((prev) => {
      return {
        ...prev,
        congressos: Object.keys(qualisProducao.congressos[0]).filter((qualis) => qualis !== 'name'),
      };
    });

    return qualisProducao;
  }

  function graphData(producoes: any) {
    const dataByYear = {}; // Objeto para armazenar as informações por ano e qualis

    producoes.forEach((producao: any) => {
      const { ano, qualis } = producao;

      // Verifica se o ano já existe no objeto, se não existir, cria um objeto vazio para ele
      if (!dataByYear[ano]) {
        dataByYear[ano] = {};
      }

      // Se a produção tiver o campo "qualis", preenche o valor correspondente no objeto
      if (qualis) {
        dataByYear[ano][qualis] = (dataByYear[ano][qualis] || 0) + 1;
      }
    });

    // Transforma o objeto em um array no formato desejado
    const tableData = Object.keys(dataByYear).map((ano) => {
      const rowData = { name: ano };

      // Preenche as informações de qualis para cada ano
      for (let qualis in dataByYear[ano]) {
        rowData[qualis] = dataByYear[ano][qualis];
      }

      // Preenche com 0 caso o qualis não exista para o ano
      for (let i = 1; i <= 4; i++) {
        if (!rowData[`A${i}`]) {
          rowData[`A${i}`] = 0;
        }
        if (!rowData[`B${i}`]) {
          rowData[`B${i}`] = 0;
        }
        if (!rowData[`C`]) {
          rowData[`C`] = 0;
        }
      }

      return rowData;
    });

    return tableData;
  }

  function countByQualis(producoes: any) {
    const qualisCount = {}; // Objeto para armazenar a contagem por qualis

    producoes.forEach((producao: any) => {
      const { qualis } = producao;

      // Se a produção tiver o campo "qualis", faz a contagem
      if (qualis) {
        qualisCount[qualis] = (qualisCount[qualis] || 0) + 1;
      }
    });

    // Transforma o objeto em um array de objetos no formato desejado
    const qualisArray = Object.keys(qualisCount).map((qualis) => {
      return {
        qualis,
        quantidade: qualisCount[qualis],
      };
    });

    return qualisArray;
  }

  const getProdQualis = async (
    idDocente: string | number,
    anoInicial: number = 1950,
    anoFinal: number = moment().year()
  ) => {
    if (!anoInicial) {
      anoInicial = 1950;
      formik.setFieldValue('ano_inicial', 1950);
    }
    if (!anoFinal) {
      anoFinal = moment().year();
      formik.setFieldValue('ano_final', moment().year());
    }

    try {
      const response = await rest.get(`/qualis/indice/docente/${idDocente}/${anoInicial}/${anoFinal}`);
      if (response.data.length === 0) {
        alert('Programa não encontrado');
        return false;
      }

      return response.data;
    } catch (error) {
      console.log('🚀 ~ file: index.ts:259 ~ useDocenteController ~ error:', error);
    }
  };

  return {
    formik,
    loading,
    getDocente,
    getProdQualis,
    qualisType,
    docente,
    qualisProducao,
  };
}
