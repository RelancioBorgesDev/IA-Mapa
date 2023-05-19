import { BrowserRouter } from "react-router-dom";
import { FormDataContextProvider } from "./contexts/FormDataContext";
import { PesquisaDadosContextProvider } from "./contexts/PesquisaDadosContext";
import Router from "./Router";
import { PaisesContextProvider } from "./contexts/PaisesContext";

function App() {
  return (
    <FormDataContextProvider>
      <PaisesContextProvider>
        <PesquisaDadosContextProvider>
          <BrowserRouter>
            <Router />
          </BrowserRouter>
        </PesquisaDadosContextProvider>
      </PaisesContextProvider>
    </FormDataContextProvider>
  );
}

export default App;
