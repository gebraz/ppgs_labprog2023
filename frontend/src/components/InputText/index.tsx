import React from 'react';



interface InputTextProps {
  label: string;
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  type?: string;
  className?: string;
  disabled?: boolean;
  show?: boolean;
}
export default function InputText(props: InputTextProps): React.JSX.Element {
  const { label, className, value, placeholder, onChange, type = "text", disabled = false, show = true } = props;
  return (
    <div className="flex flex-col mr-4">
      { show ? <p className='text-start py-1 font-medium ' >{ label }</p> : <></> }
      <input disabled={ disabled } placeholder={ placeholder } value={ value } onChange={ (e) => onChange(e.currentTarget.value) } type={ type } className={ `p-2 border-2 border-gray-400 hover:border-gray-700 rounded-lg shadow-lg ${className} ${!show ? 'hidden' : 'block'}` } />
    </div>
  );
}