
'use client';
import { BarChart, ResponsiveContainer, CartesianGrid, XAxis, YAxis, Tooltip, Legend, Bar } from 'recharts';


interface StackedbarChartProps {
  data: any;
  width?: number;
  height?: number;
  dataKeys?: string[];
}
export function StackedBarChart(props: StackedbarChartProps) {
  let { data, width, height, dataKeys } = props;
  return (

    <BarChart
      className='w-full h-full'
      width={ width ? width : 800 }
      height={ height ? height : 400 }
      data={ data }
      margin={ {
        top: 20,
        right: 30,
        bottom: 5,
      } }
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis />
      <Tooltip />
      <Legend />
      { dataKeys && dataKeys.map((qualis: string, key) => {
        console.log('qualis', qualis);
        return <Bar key={ key } dataKey={ qualis } stackId="a" fill="#003554" />;
      }) }

    </BarChart>


  );
}