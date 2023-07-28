import { ProgramaState } from '@/models/Programa';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';





type InitialState = {
  value: ProgramaState;
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