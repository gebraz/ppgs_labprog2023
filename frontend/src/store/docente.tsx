import { Docente } from '@/models/Docente';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';


type InitialState = {
  value: Docente;
};


const docenteInitialState: InitialState = {
  value: {
    id: 0,
    nome: '',
    indice: {
      indiceGeral: 0,
      indiceNRest: 0,
      indiceRest: 0,
    },
    producoes: [],
    orientacoes: [],
    tecnicas: [],
    qtdProducoes: 0,

    lattes: '',
  }


};



export const docenteSlice = createSlice({
  name: 'docente',
  initialState: docenteInitialState,
  reducers: {
    setDocente(state, action: PayloadAction<Docente>) {
      return {
        value: {
          ...state.value,
          ...action.payload,
        }
      };
    }
  },

});

export const { setDocente } = docenteSlice.actions;
export default docenteSlice.reducer;