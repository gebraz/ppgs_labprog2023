import { Navigate } from "react-router-dom";
import { useAuth } from "../auth";

export const Navigation = () => {
  const { token } = useAuth();

  return (
    <>
      {!token && <Navigate to="/login" />}
      {token && <Navigate to="/programa" />}
    </>
  );
};
