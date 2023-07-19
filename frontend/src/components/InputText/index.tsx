import React from 'react';



interface InputTextProps {
  label: string;
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  type?: string;
  className?: string;
}
export default function InputText(props: InputTextProps): React.JSX.Element {
  const { label, className, value, placeholder, onChange, type = "text" } = props;
  return (
    <div className="flex flex-col mr-4">
      <p className='text-start py-1 font-medium ' >{ label }</p>
      <input placeholder={ placeholder } value={ value } onChange={ (e) => onChange(e.currentTarget.value) } type={ type } className={ `p-2 border-2 border-gray-400 hover:border-gray-700 rounded-lg shadow-lg ${className}` } />
    </div>
  );
}