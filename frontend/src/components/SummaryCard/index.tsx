import React from 'react';
interface SummaryCardProps {
  label: string;
  value: string;
  valueColor?: string;
  labelColor?: string;
  footerColor?: string;
  bgColor?: string;
}
export default function SummaryCard(props: SummaryCardProps): React.JSX.Element {
  const { label, value, valueColor = 'text-gray-800', labelColor, footerColor, bgColor = 'bg-gray-400' } = props;
  return (
    <div className={ `w-80 h-28 mr-4 shadow-lg ${bgColor}` }>
      <div>
        <p className='text-start py-1 px-2 font-semibold text-white' >{ label }</p>
      </div>
      <div className={ `flex justify-end align-center mr-10 text-3xl  ${valueColor}` }>
        { value }
      </div>
      <div className='text-white mt-4'>
        Saiba mais
      </div>
    </div>
  );
}