import { rest } from '@/api';
import useDocenteController from '@/app/docentes/controller';
import { Docente } from '@/models/Docente';
import { Producao } from '@/models/Producao';
import { useState } from 'react';
import { useSelector } from 'react-redux';

export function useProducoesController() {
  const [docentes, setDocentes] = useState<Docente[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [selectedDocente, setSelectedDocente] = useState<Docente>();
  const [producoes, setProducoes] = useState<Producao[]>([] as Producao[]);
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

  return {
    getAllDocentes,
    getDocente,
    docentes,
    docente,
    loading,
    producoes,
    setProducoes,
  };
}
