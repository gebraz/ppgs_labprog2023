import { CardCapes, NavBar } from "../components";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const data = {
  labels: ["2023", "2022", "2021", "2020", "2019"],
  datasets: [
    {
      label: "A1",
      data: [9, 33, 30, 26, 17],
      backgroundColor: "#4dc9f6",
    },
    {
      label: "A2",
      data: [0, 8, 13, 17, 6],
      backgroundColor: "#f67019",
    },
    {
      label: "A3",
      data: [12, 26, 24, 46, 20],
      backgroundColor: "#537bc4",
    },
    {
      label: "A4",
      data: [0, 30, 49, 25, 55],
      backgroundColor: "#acc236",
    },
  ],
};

const config = {
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    xAxes: [
      {
        stacked: true,
      },
    ],
    yAxes: [
      {
        stacked: true,
      },
    ],
  },
};

export const Programa = () => {
  return (
    <div className="wrapper">
      <NavBar />
      <div className="content-wrapper">
        <div className="content-header">
          <div className="container">
            <div className="row mb-2">
              <div className="col-sm-6">
                <h1 className="m-0"> Programa </h1>
              </div>
            </div>
          </div>
        </div>

        <div className="content">
          <div className="container">
            <div className="container-fluid">
              <h5 className="mb-2">Filtros</h5>

              <form action="#">
                <div className="row">
                  <div className="col-md-10">
                    <div className="row">
                      <div className="col-3">
                        <div className="form-group">
                          <label>Programa:</label>
                          <select
                            className="form-control"
                            style={{ width: "100%" }}
                          >
                            <option selected>PPGCC</option>
                            <option>DCCMAPI</option>
                          </select>
                        </div>
                      </div>
                      <div className="col-3">
                        <div className="form-group">
                          <label>Ano inicial:</label>
                          <input className="form-control" value="2019" />
                        </div>
                      </div>
                      <div className="col-3">
                        <div className="form-group">
                          <label>Ano Final:</label>
                          <input className="form-control" value="2023" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </form>

              <h5 className="mb-2">Indicadores Capes</h5>

              <div className="row">
                <CardCapes
                  title="Total Produção"
                  value="220"
                  color="gray"
                  icon="copy"
                />
                <CardCapes
                  title="I Geral"
                  value="17,43"
                  color="info"
                  icon="envelope"
                />
                <CardCapes
                  title="I Restrito"
                  value="16,45"
                  color="success"
                  icon="flag"
                />
                <CardCapes
                  title="I Não Restrito"
                  value="0,99"
                  color="warning"
                  icon="copy"
                />
              </div>

              <div class="card card-gray">
                <div class="card-header">
                  <h3 class="card-title">Produção vs Qualis</h3>

                  <div class="card-tools">
                    <button
                      type="button"
                      class="btn btn-tool"
                      data-card-widget="collapse"
                    >
                      <i class="fas fa-minus"></i>
                    </button>
                    <button
                      type="button"
                      class="btn btn-tool"
                      data-card-widget="remove"
                    >
                      <i class="fas fa-times"></i>
                    </button>
                  </div>
                </div>
                <div class="card-body">
                  <div class="chart">
                    <Bar options={config} data={data} />
                  </div>
                </div>
              </div>

              <div class="card">
                <div class="card-header">
                  <h3 class="card-title">Docentes</h3>
                </div>
                <div class="card-body">
                  <table
                    id="example1"
                    class="table table-bordered table-striped"
                  >
                    <thead>
                      <tr>
                        <th>Docente</th>
                        <th>A1</th>
                        <th>A2</th>
                        <th>A3</th>
                        <th>A4</th>
                        <th>B1</th>
                        <th>B2</th>
                        <th>B3</th>
                        <th>B4</th>
                        <th>Detalhar</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Alexandre César Muniz de Oliveira</td>
                        <td>1</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>2</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Anselmo Cardoso de Paiva</td>
                        <td>22</td>
                        <td>4</td>
                        <td>3</td>
                        <td>1</td>
                        <td>1</td>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Areolino de Almeida Neto</td>
                        <td>1</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Ariel Soares Teles</td>
                        <td>9</td>
                        <td>3</td>
                        <td>5</td>
                        <td>6</td>
                        <td>1</td>
                        <td>2</td>
                        <td>1</td>
                        <td>3</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Aristófanes Corrêa Silva</td>
                        <td>24</td>
                        <td>3</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>1</td>
                        <td>1</td>
                        <td>1</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Bruno Feres de Souza</td>
                        <td>1</td>
                        <td>0</td>
                        <td>0</td>
                        <td>3</td>
                        <td>0</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Carlos de Salles Soares Neto</td>
                        <td>1</td>
                        <td>1</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Daniel Lima Gomes Júnior</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Darlan Bruno Pontes Quintanilha</td>
                        <td>2</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Davi Viana dos Santos</td>
                        <td>4</td>
                        <td>2</td>
                        <td>0</td>
                        <td>5</td>
                        <td>1</td>
                        <td>2</td>
                        <td>1</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Francisco Glaubos Nunes Clímaco</td>
                        <td>2</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>3</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Francisco José da Silva e Silva</td>
                        <td>7</td>
                        <td>1</td>
                        <td>1</td>
                        <td>3</td>
                        <td>0</td>
                        <td>1</td>
                        <td>0</td>
                        <td>2</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                      <tr>
                        <td>Geraldo Braz Júnior</td>
                        <td>10</td>
                        <td>3</td>
                        <td>2</td>
                        <td>0</td>
                        <td>0</td>
                        <td>1</td>
                        <td>2</td>
                        <td>2</td>
                        <td>
                          {" "}
                          <a href="docente.html">Mais</a>{" "}
                        </td>
                      </tr>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Docente</th>
                        <th>A1</th>
                        <th>A2</th>
                        <th>A3</th>
                        <th>A4</th>
                        <th>B1</th>
                        <th>B2</th>
                        <th>B3</th>
                        <th>B4</th>
                        <th>Detalhar</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
