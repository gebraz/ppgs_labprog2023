import { useEffect } from "react";
import { Programa } from "./pages";

function App() {
  useEffect(() => {
    document.body.classList = "hold-transition layout-top-nav";
  });

  return (
    <>
      <Programa />
    </>
  );
}

export default App;
