import style from "./style.module.css";
import { useContext } from "react";
import { FormDataContext } from "../../../../contexts/FormDataContext";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { IPesquisa } from "../../../../contexts/PesquisaDadosContext";

import "react-toastify/dist/ReactToastify.css";
import * as z from "zod";
import { Select } from "@chakra-ui/react";
import "@mui/material/styles/";
import { PaisesContext } from "../../../../contexts/PaisesContext";

const Pesquisa = z
  .object({
    partida: z.string().nonempty({ message: "Campo obrigatório" }),

    destino: z.string().nonempty({ message: "Campo obrigatório" }),
    limiteMaximo: z.string().optional(),
  })
  .strict();

interface IFormularioPesquisaProps {
  setPesquisaDados: React.Dispatch<React.SetStateAction<IPesquisa>>;
  setFormEnviado: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function FormularioPesquisa({
  setPesquisaDados,
  setFormEnviado,
}: IFormularioPesquisaProps) {
  const { dadosForm } = useContext(FormDataContext);
  const {paises} = useContext(PaisesContext);
  
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<IPesquisa>({
    resolver: zodResolver(Pesquisa),
    mode: "onSubmit",
  });

  const onSubmit: SubmitHandler<IPesquisa> = async (data) => {
    setPesquisaDados({
      partida: data.partida
        .trim()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/[^a-zA-Z\s]/g, "")
        .replace(/Ç/g, "C"),
      destino: data.destino
        .trim()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/[^a-zA-Z\s]/g, "")
        .replace(/Ç/g, "C"),
      limiteMaximo: data.limiteMaximo ? parseInt(data.limiteMaximo) : undefined,
    });
    setFormEnviado(true);
    reset();
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} className={style.map_inputs}>
        <Select
          className={style.inputs}
          placeholder='Partida'
          {...register("partida")}
        >
          {paises.map(({nome}) => (
            <option key={nome} value={nome}>
              {nome}
            </option>
          ))}
        </Select>
        {errors.partida && (
          <span className={style.errorMessage}>{errors.partida?.message}</span>
        )}
        <Select
          className={style.inputs}
          placeholder='Destino'
          {...register("destino")}
        >
          {paises.map(({nome}) => (
            <option key={nome} value={nome}>
              {nome}
            </option>
          ))}
        </Select>
        {errors.destino && (
          <span className={style.errorMessage}>{errors.destino?.message}</span>
        )}

        {dadosForm.algorithm === "profundidadelimitada" ||
        dadosForm.algorithm === "aprofundamentoiterativo" ? (
          <input
            type='number'
            placeholder='Limite Máximo.....'
            {...register("limiteMaximo")}
            className={style.inputs}
          />
        ) : (
          ""
        )}
        {errors.limiteMaximo && (
          <span className={style.errorMessage}>
            {errors.limiteMaximo?.message}
          </span>
        )}
        <button type='submit'>Procurar</button>
      </form>
    </>
  );
}
