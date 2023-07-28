import * as Yup from 'yup';
import { useFormik } from 'formik';
import { rest } from '@/api';
import { useEffect, useState } from 'react';
import { ProgramaState, setPrograma } from '@/store/programa';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch } from '@/store';
import moment from 'moment';
import { set } from 'zod';
const ProgramaSchema = Yup.object().shape({
  nome: Yup.string(),
  ano_inicial: Yup.number(),
  ano_final: Yup.number(),
});

export default function useProgramaController() {
  const dispatch = useDispatch<AppDispatch>();
  const programa = useSelector((state) => state.programa.value);
  const [programas, setProgramas] = useState<Programa>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [qualisProducao, setQualisProducao] = useState<any>([]);
  const [qualisType, setQualisType] = useState<any>([]);
  const formik = useFormik({
    initialValues: {
      nome: '',
      ano_inicial: 1950,
      ano_final: moment().year(),
    },
    validationSchema: ProgramaSchema,
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
    },
  });

  const getAllProgramas = async () => {
    try {
      setLoading(true);
      const allProgramasResponse = await rest.get('/programa/obterProgramas');
      if (allProgramasResponse.data.length === 0) {
        alert('Programas não encontrados');
        return;
      }

      setProgramas(allProgramasResponse.data);

      formik.setFieldValue('nome', allProgramasResponse.data[0].nome);
      await getPrograma(allProgramasResponse.data[0].nome);
      setLoading(false);
    } catch (error) {
      setLoading(false);
      console.log(error);
    }
  };

  const getPrograma = async (programa: string) => {
    setLoading(true);

    const programResponse = await rest.get(`/programa/obterPrograma/${programa}`);
    let programaPayload: ProgramaState = {} as ProgramaState;
    let programaData = programResponse.data[0];
    if (programResponse.data.length === 0) {
      alert('Programa não encontrado');
      return;
    }
    console.log('🚀 ~ file: index.ts:47 ~ getPrograma ~ programaData.id', programaData.id);
    const prodQualis = await getProdQualis(programaData.id, formik.values.ano_inicial, formik.values.ano_final);
    const docentes = await getProgramaDocente(programaData.id);
    if (!prodQualis) {
      return;
    }
    programaPayload = {
      id: programaData.id,
      nome: programaData.nome,
      indice: {
        indiceGeral: prodQualis.indice.indiceGeral,
        indiceNRest: prodQualis.indice.indiceNRest,
        indiceRest: prodQualis.indice.indiceRest,
      },
      producoes: prodQualis.producoes,
      docentes: docentes,
      qtdProducoes: prodQualis.producoes.length,
    };

    console.log('🚀 ~ file: index.ts:47 ~ getPrograma ~ programaPayload:', programaPayload);
    setQualisProducao(populateQualisProducao(prodQualis.producoes));

    dispatch(setPrograma(programaPayload));
    setLoading(false);
  };

  const getProgramaDocente = async (id: number) => {
    try {
      const programaDocente = await rest.get(`/programa/obterDocentesPrograma/${id}`);
      if (programaDocente.data.length === 0) {
        alert('Docentes não encontrados');
        return false;
      }

      let data = programaDocente.data.map(async (docente: any) => {
        let producoes = await rest.get(
          `/docente/obterProducoes/${docente.id}/${formik.values.ano_inicial}/${formik.values.ano_final}`
        );
        if (producoes.data.length === 0) {
          return {};
        }
        return {
          id: docente.id,
          docente: docente.nome,
          lattes: docente.lattes,
          producoes: producoes.data,
          qtdQualis: countByQualis(producoes.data),
        };
      });

      data = await Promise.all(data);
      data = data.filter((d: any) => Object.prototype.hasOwnProperty.call(d, 'id'));
      return data;
    } catch (error) {
      console.log('🚀 ~ file: index.ts:47 ~ getPrograma ~ error', error);
      return false;
    }
  };

  function populateQualisProducao(producoes: any) {
    const dataByYear = {}; // Objeto para armazenar as informações por ano e qualis

    // Percorre o array de produções e preenche o objeto dataByYear
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
      }

      return rowData;
    });
    setQualisType(Object.keys(tableData[0]).filter((qualis) => qualis !== 'name'));

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
    idPrograma: string | number,
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
      const response = await rest.get(`/qualis/indice/${idPrograma}/${anoInicial}/${anoFinal}`);
      if (response.data.length === 0) {
        alert('Programa não encontrado');
        return false;
      }
      return response.data;
    } catch (error) {}
  };

  return {
    formik,
    loading,
    getPrograma,
    qualisType,
    qualisProducao,
    getAllProgramas,
    programas,
  };
}
