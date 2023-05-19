import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";
import { apiPaises } from "../api/api";
import { FormDataContext } from "./FormDataContext";

interface IPaisesCoordenadas {
  lat: number;
  lng: number;
}
interface IPaises {
    nome: string;
  }

interface IPaisesContext {
  paises: Array<IPaises>;
  paisesCoordenadas: { [key: string]: IPaisesCoordenadas };
}

interface IPaisesContextProvider {
  children: ReactNode;
}

export const PaisesContext = createContext({} as IPaisesContext);

export function PaisesContextProvider({ children }: IPaisesContextProvider) {
  const [paises, setPaises] = useState<Array<IPaises>>([]);
  const [paisesCoordenadas, setPaisesCoordenadas] = useState<{
    [key: string]: IPaisesCoordenadas;
  }>({});

  const { dadosForm } = useContext(FormDataContext);
  const { continente } = dadosForm;

  async function recebePaises() {
    try {
      const respostaPaisesCoordenadas = await apiPaises.get(
        `coordenadas/${continente.toLowerCase()}`
      );

      const coordenadasObjeto = respostaPaisesCoordenadas.data;
      const coordenadasArray = Object.entries(coordenadasObjeto).map(
        ([pais, coordenadas]) => ({
          [pais]: coordenadas as IPaisesCoordenadas,
        })
      );
  
      console.log("Resposta paises Coordenadas:", respostaPaisesCoordenadas);
  
      const novoPaisesCoordenadas = Object.assign({}, ...coordenadasArray);
      setPaisesCoordenadas(novoPaisesCoordenadas);
  
      const respostaPaises = await apiPaises.get(
        `/pais/${continente.toLowerCase()}`
      );

      console.log("Resposta paises :", respostaPaises);
  
      setPaises(respostaPaises.data);
    } catch (error) {
      // Trate o erro da requisição
      console.error("Erro na requisição:", error);
    }
  }
  

  useEffect(() => {
    recebePaises();
  }, [continente]);

  return (
    <PaisesContext.Provider value={{ paises, paisesCoordenadas }}>
      {children}
    </PaisesContext.Provider>
  );
}
