//Index of redux store
import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import { programaSlice } from './programa';
import { createWrapper } from 'next-redux-wrapper';
import { docenteSlice } from './docente';
import { usuarioSlice } from './auth';

export const store = configureStore({
  reducer: {
    [programaSlice.name]: programaSlice.reducer,
    [docenteSlice.name]: docenteSlice.reducer,
    [usuarioSlice.name]: usuarioSlice.reducer,
  },
  devTools: true,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
