import { Bar } from "react-chartjs-2";

export const ChartPrograma = ({ config, data }) => {
  return <Bar options={config} data={data} />;
};
