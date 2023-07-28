import { Usuario } from '@/models/Usuario';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';


type InitialState = {
  value: Usuario;
};


const usuarioInitialState: InitialState = {
  value: {
    id: -1,
    nome: '',
    email: '',
    senha: '',
    idDocente: -1,
    token: '',
  }


};



export const usuarioSlice = createSlice({
  name: 'usuario',
  initialState: usuarioInitialState,
  reducers: {
    setUsuario(state, action: PayloadAction<Usuario>) {
      //apply local storage
      return {
        value: {
          ...state.value,
          ...action.payload,
        }
      };
    }
  },

});

export const { setUsuario } = usuarioSlice.actions;
export default usuarioSlice.reducer;