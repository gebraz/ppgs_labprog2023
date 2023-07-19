import React from 'react'
//import { Chart } from "react-google-charts";
//import { render } from "react-dom";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';


ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const config = {
  responsive : true,
  scales: {
    x: {
      stacked: true,
    },
    y: {
      stacked: true,
    },
  }
}
const randomColor = () => {
  const letters = '0123456789ABCDEF';
  let color = '#';
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
};
export default function GraficoProducao({ titulo, dadosBrutos }) {
  const dados = {
    labels  : dadosBrutos.anos,
    datasets: [
      {
        label               : 'A1',
        data                : dadosBrutos.a1,
        backgroundColor     : randomColor()
      },
      {
        label               : 'A2',
        data                : dadosBrutos.a2,
        backgroundColor     : randomColor()
      },
      {
        label               : 'A3',
        data                : dadosBrutos.a3,
        backgroundColor     : randomColor()
      },
      {
        label               : 'A4',
        data                : dadosBrutos.a4,
        backgroundColor     : randomColor()
      },
    ]
  }
  
  return (
      <div className="card card-gray">
          <div className="card-header">
              <h3 className="card-title">{ titulo }</h3>

              <div className="card-tools">
                  <button type="button" className="btn btn-tool" data-card-widget="collapse">
                  <i className="fas fa-minus"></i>
                  </button>
                  <button type="button" className="btn btn-tool" data-card-widget="remove">
                  <i className="fas fa-times"></i>
                  </button>
              </div>
          </div>
          <div className="card-body">
              <div className="chart">
                  <Bar options={config} data={dados} />
              </div>
          </div>
      </div>
  );

}