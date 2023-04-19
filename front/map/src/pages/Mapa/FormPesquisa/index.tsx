import style from "./style.module.css";
import { useContext, useEffect, useState } from "react";
import { FormDataContext } from "../../../contexts/FormDataContext";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { IPesquisa } from "../../../contexts/PesquisaDadosContext";
import { paisesAceitos } from "../../../utils/paisesValidos";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Pesquisa = z
  .object({
    partida: z
      .string()
      .nonempty({ message: "Campo obrigatório" })
      .refine((value) => {
        let valorNormalizado = value
          .normalize("NFD")
          .replace(/[\u0300-\u036f]/g, "")
          .replace(/[^a-zA-Z\s]/g, "");
        if (!paisesAceitos.includes(valorNormalizado)) {
          throw new Error("Destino inválido");
        }
        return true;
      }),
    destino: z
      .string()
      .nonempty({ message: "Campo obrigatório" })
      .refine((value) => {
        let valorNormalizado = value
          .normalize("NFD")
          .replace(/[\u0300-\u036f]/g, "")
          .replace(/[^a-zA-Z\s]/g, "");
        if (!paisesAceitos.includes(valorNormalizado)) {
          console.log(valorNormalizado);
        }
        return true;
      }),
    limiteMaximo: z.string().optional(),
  })
  .strict();

interface IFormularioPesquisaProps {
  setPesquisaDados: React.Dispatch<React.SetStateAction<IPesquisa>>;
}

export default function FormularioPesqusia({
  setPesquisaDados,
}: IFormularioPesquisaProps) {
  const { dadosForm } = useContext(FormDataContext);
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<IPesquisa>({
    resolver: zodResolver(Pesquisa),
    mode: "onBlur",
  });

  const onSubmit: SubmitHandler<IPesquisa> = async (data) => {
    await Pesquisa.parseAsync(data); // Verifica se os dados são válidos de acordo com a schema
    setPesquisaDados({
      partida: data.partida
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/[^a-zA-Z\s]/g, "")
        .trim(),
      destino: data.destino
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/[^a-zA-Z\s]/g, "")
        .trim(),
      limiteMaximo: data.limiteMaximo ? parseInt(data.limiteMaximo) : undefined,
    });
    reset();
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} className={style.map_inputs}>
        <input
          type='text'
          placeholder='Ponto de partida....'
          {...register("partida")}
          className={errors.partida ? style.inputError : ""}
        />
        {errors.partida && (
          <span className={style.errorMessage}>{errors.partida?.message}</span>
        )}
        <input
          type='text'
          placeholder='Destino.....'
          {...register("destino")}
          className={errors.partida ? style.inputError : ""}
        />
        {errors.partida && (
          <span className={style.errorMessage}>{errors.destino?.message}</span>
        )}
        {dadosForm.algorithm === "profundidadelimitada" ||
        dadosForm.algorithm === "aprofundamentoiterativo" ? (
          <input
            type='number'
            placeholder='Limite Máximo.....'
            {...register("limiteMaximo")}
            className={errors.partida ? style.inputError : ""}
          />
        ) : (
          ""
        )}
        {errors.partida && (
          <span className={style.errorMessage}>
            {errors.limiteMaximo?.message}
          </span>
        )}
        <button type='submit'>Procurar</button>
      </form>
    </>
  );
}
