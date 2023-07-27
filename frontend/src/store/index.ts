//Index of redux store
import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import { programaSlice } from './programa';
import { createWrapper } from 'next-redux-wrapper';

export const store = configureStore({
  reducer: {
    [programaSlice.name]: programaSlice.reducer,
  },
  devTools: true,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
