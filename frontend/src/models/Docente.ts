import { Indice } from './Indice';
import { Orientacao } from './Orientacao';
import { Producao } from './Producao';

export interface Docente {
  id: number;
  lattes: string;
  nome: string;
  producoes: Producao[];
  orientacoes: Orientacao[];
  indice: Indice;
  qtdProducoes: number;
}
