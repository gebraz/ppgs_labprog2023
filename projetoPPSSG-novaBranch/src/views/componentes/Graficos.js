import {Bar} from 'react-chartjs-2'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);
const dados = {
    labels  : ['2023', '2022', '2021', '2020', '2019'],
    datasets: [
      {
        label               : 'A1',
        data                : [9, 33, 30, 26, 17],
        backgroundColor     : '#4dc9f6'
      },
      {
        label               : 'A2',
        data                : [0, 8, 13, 17, 6],
        backgroundColor     : '#f67019'
      },
      {
        label               : 'A3',
        data                : [12, 26, 24, 46, 20],
        backgroundColor     : '#537bc4'
      },
      {
        label               : 'A4',
        data                : [0, 30, 49, 25, 55],
        backgroundColor     : '#acc236'
      },
    ]
  }

const config = {
    responsive : true,
    scales: {
      xAxes: [{
        stacked: true,
      }],
      yAxes: [{
        stacked: true
      }]
    }
  }

export default function Graficos({titulo}) {
    return (
            <GraficoProducao titulo={titulo}/>
    );
}

function GraficoProducao({titulo}){
  return(
      <div class="card card-gray">
      <div class="card-header">
        <h3 class="card-title">{titulo}</h3>
        <div class="card-tools">
          <button type="button" class="btn btn-tool" data-card-widget="collapse">
            <i class="fas fa-minus"></i>
          </button>
          <button type="button" class="btn btn-tool" data-card-widget="remove">
            <i class="fas fa-times"></i>
          </button>
        </div>
      </div>
      <div class="card-body">
        <div class="chart">
          <Bar options={config} data = {dados} />
        </div>
      </div>        
    </div>
  );

}


