import { createSlice } from '@reduxjs/toolkit';
import { HYDRATE } from 'next-redux-wrapper';

interface ProgramaIndice {
  indiceGeral: number;
  indiceNRest: number;
  indiceRest: number;
}

interface Docente {
  
}

interface ProgramaProducao {
  ano?: number;
  autores?: String;
  nome: String;
  doi?: String;
  id: number;
  issnOuSigla?: String;
  natureza?: String;
  nomeLocal?: String;
  percentileOuH5?: number;
  qtdDoutorado?: number;
  qtdGrad?: number;
  qtdMestrado?: number;
  qualis?: String;
  tipo?: String;
  titulo?: String;

}

export interface ProgramaState {
  id: number;
  nome: string;
  indice: ProgramaIndice;
  qtdProducoes: number;
  producoes: ProgramaProducao[];
}