import { createContext, ReactNode, useState } from "react";

interface IFormData {
  continente: string;
  algorithm: string;
}

interface IFormDataContext {
  dadosForm: IFormData;
  setDadosForm: React.Dispatch<React.SetStateAction<IFormData>>;
}

interface IFormDataContextProvider {
  children: ReactNode;
}

export const FormDataContext = createContext({} as IFormDataContext);

export function FormDataContextProvider({
  children,
}: IFormDataContextProvider) {
  const [dadosForm, setDadosForm] = useState<IFormData>({
    continente: "",
    algorithm: "",
  });
  return (
    <FormDataContext.Provider value={{ dadosForm, setDadosForm }}>
      {children}
    </FormDataContext.Provider>
  );
}
