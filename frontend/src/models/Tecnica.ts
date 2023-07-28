import { Docente } from './Docente';
import { Orientacao } from './Orientacao';

export interface Tecnica {
  id: number;
  tipo: string;
  titulo: string;
  autores: string;

  ano: number;
  financiadora: string;
  outrasInformacoes: string;
  qtdGrad: string;
  qtdMestrado: string;
  qtdDoutorado: string;
  orientacoes: Orientacao[];
  docentes: Docente[];
}
