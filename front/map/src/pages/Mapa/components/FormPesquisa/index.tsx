import style from "./style.module.css";
import { useContext } from "react";
import { FormDataContext } from "../../../../contexts/FormDataContext";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { IPesquisa } from "../../../../contexts/PesquisaDadosContext";
import { paisesAceitos, paisesAfricanos, paisesAmericanos, paisesEuropeus } from "../../../../utils/paisesValidos";
import "react-toastify/dist/ReactToastify.css";
import * as z from "zod";
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import "@mui/material/styles/"; 

const Pesquisa = z
  .object({
    partida: z
      .string()
      .nonempty({ message: "Campo obrigatório" }),
    
    destino: z
      .string()
      .nonempty({ message: "Campo obrigatório" }),
   
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
    console.log("Partida: " + data.partida)
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
  };

  function verificaContinenteEscolhido(){
    console.log(dadosForm.continente)
    if(dadosForm.continente == "Americano"){
      return paisesAmericanos;
    }else if(dadosForm.continente == "Europeu"){
      return paisesEuropeus;
    }else if(dadosForm.continente == "Africano"){
      return paisesAfricanos;
    }else{
      return [];
    }
  }

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} className={style.map_inputs}>
      <Autocomplete
        options={verificaContinenteEscolhido()}
        className={`${style.inputs} ${style.autocomplete}`}
        sx={{ width: 300 }}
        renderInput={(params) => <TextField {...params} label="Partida" {...register("partida")}/>}
      />
        {errors.partida && (
          <span className={style.errorMessage}>{errors.partida?.message}</span>
        )}
        <Autocomplete
          disablePortal
          options={verificaContinenteEscolhido()}
          sx={{ width: 300 }}
          renderInput={(params) => <TextField {...params} label="Destino" {...register("destino")} />}
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
