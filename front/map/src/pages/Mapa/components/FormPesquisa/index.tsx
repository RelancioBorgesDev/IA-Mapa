import style from "./style.module.css";
import { useContext } from "react";
import { FormDataContext } from "../../../../contexts/FormDataContext";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { IPesquisa } from "../../../../contexts/PesquisaDadosContext";
import { paisesAceitos } from "../../../../utils/paisesValidos";
import "react-toastify/dist/ReactToastify.css";
import * as z from "zod";

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
          throw new Error("Destino inválido");
        }
        return true;
      }),
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
    if (!Object.keys(errors).length) {
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
        limiteMaximo: data.limiteMaximo
          ? parseInt(data.limiteMaximo)
          : undefined,
      });
      setFormEnviado(true);
      reset();
    }
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
          className={errors.destino ? style.inputError : ""}
        />
        {errors.destino && (
          <span className={style.errorMessage}>{errors.destino?.message}</span>
        )}
        {dadosForm.algorithm === "profundidadelimitada" ||
        dadosForm.algorithm === "aprofundamentoiterativo" ? (
          <input
            type='number'
            placeholder='Limite Máximo.....'
            {...register("limiteMaximo")}
            className={errors.limiteMaximo ? style.inputError : ""}
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
