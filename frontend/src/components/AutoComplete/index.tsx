import { AutoComplete, AutoCompleteCompleteEvent } from 'primereact/autocomplete';

interface AutoCompleteProps {
  value: string;
  onChange: (field: string, value: string) => void;
  field: string;
  suggestions: any[];
  placeholder?: string;
}

export default function AutoCompleteComponent(props: AutoCompleteProps) {
  const { value, onChange, suggestions, placeholder, field } = props;

  console.log('suggestions> ', suggestions);
  return (
    <div className='mr-4 pt-7'>
      <AutoComplete field={ field } suggestions={ suggestions } className='w-72 m-0' placeholder={ placeholder } value={ value } onChange={ (e) => {
        onChange(field, e.value);
      } } dropdown />
    </div>

  );
}