import { createSlice, PayloadAction } from '@reduxjs/toolkit';


interface ProgramaIndice {
  indiceGeral: number;
  indiceNRest: number;
  indiceRest: number;
}

interface Docente {
  id: number,
  lattes: number,
  nome: string,
  producoes: ProgramaProducao[],
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
type InitialState = {
  value: ProgramaState;
};
export type ProgramaState = {
  id: number;
  nome: string;
  indice: ProgramaIndice;
  qtdProducoes: number;
  producoes: ProgramaProducao[];
  docentes: Docente[];
};

const programaInitialState: InitialState = {
  value: {
    id: 0,
    nome: '',
    indice: {
      indiceGeral: 0,
      indiceNRest: 0,
      indiceRest: 0,
    },
    qtdProducoes: 0,
    producoes: [],
    docentes: []
  }


};



export const programaSlice = createSlice({
  name: 'programa',
  initialState: programaInitialState,
  reducers: {
    setPrograma(state, action: PayloadAction<ProgramaState>) {
      return {
        value: {
          ...state.value,
          ...action.payload,
        }
      };
    }
  },

});

export const { setPrograma } = programaSlice.actions;
export default programaSlice.reducer;