import "../estilizacao/Login.css";
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          email: email,
          senha: senha,
        },
        {
          withCredentials: true, // Adicione essa opção dentro do objeto de configuração
        }
      );

      const data = response.data;
      console.log(data.token);

      // Verifica se a autenticação foi bem-sucedida (por exemplo, o servidor retornou um token de autenticação)
      if (data.token) {
        // Armazena o token de autenticação no localStorage
        localStorage.setItem("token", data.token);

        // Redireciona o usuário para a página desejada após a autenticação bem-sucedida.
        navigate("/");
      } else {
        // Exibe uma mensagem de erro se a autenticação falhar.
        setErro("Credenciais inválidas. Verifique o e-mail e a senha.");
      }
    } catch (error) {
      // Exibe uma mensagem de erro se ocorrer um erro na solicitação.
      setErro("Ocorreu um erro ao fazer login. Por favor, tente novamente.");
    }
  };

  return (
    <div>
      <div className="fixed-body">
        {/* Conteúdo fixo do body */}
      </div>
      <div className="card card-outline card-primary">
        <div className="card-header">
          <h3 className="card-title"></h3>
        </div>
        <div className="card-body">
          <meta charSet="utf-8" />
          <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
          />
          <title>Login</title>
          <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          />
          <link
            rel="stylesheet"
            href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
          />
          <link rel="stylesheet" href="Login.css" />
          <div className="login-form">
            <form onSubmit={handleSubmit}>
              <h2 className="text-center">SPPg - Login</h2>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  className="form-control"
                  placeholder="Senha"
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <button type="submit" className="btn btn-primary btn-block">
                  Login
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}