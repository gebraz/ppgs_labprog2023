export default function Navbar({titulo}) {
    return (
        <nav className="main-header navbar navbar-expand-md navbar-primary navbar-dark">
          <div className="container">
            <a href="/" className="navbar-brand">
              <span className="brand-text font-weight-light">{titulo}</span>
            </a>
            <button className="navbar-toggler order-1" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse order-3" id="navbarCollapse">              
              <ul className="navbar-nav">
                <li className="nav-item">
                  <a href="Programas" className="nav-link">Programas</a>
                </li>
                <li className="nav-item">
                  <a href="Docente" className="nav-link">Docentes</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>        
    );
}