'use client';
import React from 'react';
import { Divider } from 'primereact/divider';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Checkbox } from 'primereact/checkbox';
import { useRouter } from 'next/navigation';

export default function LoginDemo() {
  const router = useRouter();
  return (
    <div className="flex w-full h-screen items-center justify-center">
      <div className="flex flex-col items-center justify-center surface-card p-4 shadow-2 border-round w-1/3 h-screen">
        <div className="text-center mb-5">
          <div className="text-900 text-3xl font-medium mb-3">Bem vindo de volta</div>

        </div>

        <div>
          <label htmlFor="email" className="block text-900 font-medium mb-2">Email</label>
          <InputText id="email" type="text" placeholder="email@email.com" className="w-full mb-6" />

          <label style={ { marginTop: 10 } } htmlFor="password" className="block text-900 font-medium mb-2">Password</label>
          <InputText id="password" type="password" placeholder="*********" className="w-full mb-3" />

          <Button style={ { marginTop: 20 } } onClick={ () => router.push('/') } label="Entrar" icon="pi pi-user" className="w-full mt-5" />
        </div>
      </div>
    </div>
  );
}