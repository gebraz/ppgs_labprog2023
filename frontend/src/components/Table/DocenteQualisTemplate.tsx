export function A1Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'A1');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function A2Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'A2');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function A3Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'A3');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function A4Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'A4');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function B1Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'B1');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function B2Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'B2');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function B3Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'B3');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function B4Template(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'B4');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function CTemplate(rowData: any) {
  let qtdQualis = rowData?.qtdQualis?.filter((qualis: any) => qualis.qualis === 'C');
  if (qtdQualis?.length > 0)
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis[0] ? qtdQualis[0].quantidade : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}
export function TotalTemplate(rowData: { qtdQualis: any[]; }) {
  let qtdQualis = rowData?.qtdQualis?.reduce((curr, acc) => curr + acc.quantidade, 0);
  if (qtdQualis)
    return (
      <div className='text-white bg-green-600 h-full rounded-full flex justify-center font-semibold'>
        { qtdQualis ? qtdQualis : 0 }
      </div>
    );
  else
    return (
      <div className='text-white bg-gray-800 h-full rounded-full flex justify-center font-semibold'>
        0
      </div>
    );
}

