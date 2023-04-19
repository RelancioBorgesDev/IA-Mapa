import { BrowserRouter } from "react-router-dom";
import { FormDataContextProvider } from "./contexts/FormDataContext";
import { PesquisaDadosContextProvider } from "./contexts/PesquisaDadosContext";
import Router from "./Router";

function App() {
  return (
    <FormDataContextProvider>
      <PesquisaDadosContextProvider>
        <BrowserRouter>
          <Router />
        </BrowserRouter>
      </PesquisaDadosContextProvider>
    </FormDataContextProvider>
  );
}

export default App;
