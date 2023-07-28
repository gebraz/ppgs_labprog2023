import { Docente } from './Docente';
import { Indice } from './Indice';
import { Producao } from './Producao';

export type ProgramaState = {
  id: number;
  nome: string;
  indice: Indice;
  qtdProducoes: number;
  producoes: Producao[];
  docentes: Docente[];
};
