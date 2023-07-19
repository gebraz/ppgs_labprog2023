'use client';
import React from 'react';
import InputText from "../InputText";
import DatePicker from '../DatePicker';
import useProgramaController from '@/app/programas/controller';


export default function Filters(props: FiltersProps) {
  const [program, setProgram] = React.useState<string>('');
  const [startDate, setStartDate] = React.useState<string>('');
  const [lastDate, setLastDate] = React.useState<string>('');
  const { showInputs } = props;
  const { formik, getPrograma } = useProgramaController();


  return (
    <div className='flex flex-col w-full'>


    </div>
  );
}