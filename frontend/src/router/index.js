import { createBrowserRouter } from "react-router-dom";
import { Programa, Login } from "../pages";

export const router = createBrowserRouter([
  {
    path: "/programa",
    element: <Programa />,
  },
  {
    path: "/login",
    element: <Login />,
  },
]);
