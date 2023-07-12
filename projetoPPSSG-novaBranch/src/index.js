import React from 'react';
import ReactDOM from 'react-dom/client';
import Home from './views/Home';
import Docente from './views/Docente';

import { BrowserRouter, Routes, Route } from "react-router-dom";
import 'admin-lte/dist/css/adminlte.min.css'
import 'admin-lte/plugins/fontawesome-free/css/all.min.css'
import 'admin-lte/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css'
import 'admin-lte/plugins/datatables-responsive/css/responsive.bootstrap4.min.css'
import 'admin-lte/plugins/datatables-buttons/css/buttons.bootstrap4.min.css'


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="Programas" element={<Home />} />
          <Route path="Docente" element={<Docente />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);



