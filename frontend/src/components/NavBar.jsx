export const NavBar = () => {
  return (
    <nav class="main-header navbar navbar-expand-md navbar-light navbar-white">
      <div class="container">
        <a href="home.html" class="navbar-brand">
          <span class="brand-text font-weight-light">SPPG</span>
        </a>

        <button
          class="navbar-toggler order-1"
          type="button"
          data-toggle="collapse"
          data-target="#navbarCollapse"
          aria-controls="navbarCollapse"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse order-3" id="navbarCollapse">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a href="home.html" class="nav-link">
                Programas
              </a>
            </li>
            <li class="nav-item">
              <a href="docente.html" class="nav-link">
                Docentes
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};
