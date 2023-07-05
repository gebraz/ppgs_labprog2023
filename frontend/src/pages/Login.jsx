import { useEffect } from "react";
import { useAuth } from "../auth";

export const Login = () => {
  const { onLogin } = useAuth();

  const tryLogin = () => {
    onLogin("teles");
  };

  useEffect(() => {
    document.body.classNameList = "";
  });

  return (
    <section className="vh-100">
      <div className="container-fluid">
        <div className="row">
          <div className="col-sm-6 text-black">
            <div className="px-5 ms-xl-4">
              <i className="fas fa-crow fa-2x me-3 pt-5 mt-xl-4"></i>
              <span className="h1 fw-bold mb-0">SPPG</span>
            </div>

            <div className="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
              <form>
                <h3 className="fw-normal mb-3 pb-3">Tela de login</h3>

                <div className="form-outline mb-4">
                  <input
                    type="email"
                    id="form2Example18"
                    className="form-control form-control-lg"
                    placeholder="Email"
                  />
                </div>

                <div className="form-outline mb-4">
                  <input
                    type="password"
                    id="form2Example28"
                    className="form-control form-control-lg"
                    placeholder="Senha"
                  />
                </div>

                <div className="pt-1 mb-4">
                  <button
                    className="btn btn-info btn-lg btn-block"
                    type="button"
                    onClick={tryLogin}
                  >
                    Entrar
                  </button>
                </div>

                <p className="small mb-5 pb-lg-2">
                  <a className="text-muted" href="#!">
                    Esqueceu a senha?
                  </a>
                </p>
                <p>
                  Não tem conta?{" "}
                  <a href="#!" className="link-info">
                    Faça seu cadastro
                  </a>
                </p>
              </form>
            </div>
          </div>
          <div className="col-sm-6 px-0 d-none d-sm-block">
            <img
              src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img3.webp"
              alt="Login"
              className="w-100 vh-100"
              style={imgStyle}
            />
          </div>
        </div>
      </div>
    </section>
  );
};

const imgStyle = {
  objectFit: "cover",
};
