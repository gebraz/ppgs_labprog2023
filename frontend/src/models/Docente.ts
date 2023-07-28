import { Indice } from './Indice';
import { Orientacao } from './Orientacao';
import { Producao } from './Producao';
import { Tecnica } from './Tecnica';

export interface Docente {
  id: number;
  lattes: string;
  nome: string;
  producoes: Producao[];
  orientacoes: Orientacao[];
  tecnicas: Tecnica[];
  indice: Indice;
  qtdProducoes: number;
}
