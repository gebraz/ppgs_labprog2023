'use client';
import React from 'react';
import InputText from "../InputText";
import DatePicker from '../DatePicker';

type inputTypes = 'ano_inicial' | 'ano_final' | 'programa';
interface FiltersProps {
  showInputs?: inputTypes[];
}
export default function Filters(props: FiltersProps) {
  const [program, setProgram] = React.useState<string>('');
  const [startDate, setStartDate] = React.useState<string>('');
  const [lastDate, setLastDate] = React.useState<string>('');
  const { showInputs } = props;
  return (
    <div className='flex flex-col w-full'>

      <div className='flex justify-start'>
        <InputText show={ showInputs?.includes("programa") } className='w-96' label='Programa' placeholder='Nome do programa' value={ program } onChange={ setProgram } />
        <InputText label='Ano inicial' placeholder='2023' value={ startDate } onChange={ setStartDate } />
        <InputText label='Ano final' placeholder='2023' value={ lastDate } onChange={ setLastDate } />


      </div>
    </div>
  );
}