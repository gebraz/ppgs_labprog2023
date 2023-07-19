import * as Yup from 'yup';
import { useFormik } from 'formik';
import { rest } from '@/api';
import { useEffect } from 'react';

const ProgramaSchema = Yup.object().shape({
  nome: Yup.string(),
  ano_inicial: Yup.number(),
  ano_final: Yup.number(),
});

export default function useProgramaController() {
  const formik = useFormik({
    initialValues: {
      nome: '',
      ano_inicial: 0,
      ano_final: 0,
    },
    validationSchema: ProgramaSchema,
    onSubmit: (values) => {
      alert(JSON.stringify(values, null, 2));
    },
  });

  const getPrograma = async (programa: string) => {
    const programResponse = await rest.get('/programa/obterPrograma', {
      params: {
        programa: programa,
      },
    });
    console.log('🚀 ~ file: index.ts:31 ~ getPrograma ~ programResponse:', programResponse);
    await getProdQualis(programResponse.data[0].id);
  };

  const getProdQualis = async (idPrograma: string | number) => {
    const response = await rest.get(`/v1/qualis/indice/${idPrograma}`);
    console.log(response.data);
  };
  useEffect(() => {
    console.log(formik.values);
  }, [formik.values]);

  return {
    formik,
    getPrograma,
  };
}
