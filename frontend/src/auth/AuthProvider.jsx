import { createContext, useContext, useEffect, useState } from "react";

const authContext = createContext();

export const useAuth = () => {
  return useContext(authContext);
};

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);

  const handleLogin = (e) => {
    setToken(e);
  };

  const handleLogout = () => {
    console.log("fazer logout");

    setToken(null);
  };

  useEffect(() => {
    console.log(token);
  });

  const value = {
    token,
    onLogin: handleLogin,
    onLogout: handleLogout,
  };

  return <authContext.Provider value={value}>{children}</authContext.Provider>;
};
