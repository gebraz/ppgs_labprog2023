import { Indice } from './Indice';
import { Producao } from './Producao';

export interface Docente {
  id: number;
  lattes: string;
  nome: string;
  producoes: Producao[];
  indice: Indice;
  qtdProducoes: number;
}
