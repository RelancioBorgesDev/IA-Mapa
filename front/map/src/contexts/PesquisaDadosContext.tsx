import { createContext, ReactNode, useState } from "react";

export interface IPesquisa {
  partida: string;
  destino: string;
  limiteMaximo?: number;
}

interface IPesquisaDadosContext {
  pesquisaDados: IPesquisa;
  setPesquisaDados: React.Dispatch<React.SetStateAction<IPesquisa>>;
}

interface IPesquisaDadosContextProviderProps {
  children: ReactNode;
}

export const PesquisaDadosContext = createContext({} as IPesquisaDadosContext);

export function PesquisaDadosContextProvider({
  children,
}: IPesquisaDadosContextProviderProps) {
  const [pesquisaDados, setPesquisaDados] = useState<IPesquisa>(
    {} as IPesquisa
  );
  return (
    <PesquisaDadosContext.Provider value={{ pesquisaDados, setPesquisaDados }}>
      {children}
    </PesquisaDadosContext.Provider>
  );
}
