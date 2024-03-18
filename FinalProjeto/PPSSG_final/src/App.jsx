import Docente from './views/pages/Docente';
import Home from './views/pages/Home';
import Login from './views/pages/Login';
import Producao from './views/pages/Producao'
import { BrowserRouter, Routes, Route } from "react-router-dom";




function App() {
  return (
    
    <BrowserRouter>
      <div>
        <Routes> 
          <Route path="/login" element={<Login/>} />
          <Route path="/" element={<Home/>} />
          <Route path="docente/:id" element={<Docente />} />
          <Route path="producao" element={<Producao />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;